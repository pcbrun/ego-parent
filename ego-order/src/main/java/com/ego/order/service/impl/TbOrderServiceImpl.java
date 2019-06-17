package com.ego.order.service.impl;

import com.ego.commons.pojo.EgoResult;
import com.ego.commons.pojo.TbItemChild;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.HttpClientUtil;
import com.ego.commons.utils.IDUtils;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.dubbo.service.TbOrderDubboService;
import com.ego.order.pojo.MyOrderParams;
import com.ego.order.service.TbOrderService;
import com.ego.pojo.TbOrder;
import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;
import com.ego.redis.dao.JedisDao;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Auther:pcb
 * @Date:19/6/12
 * @Description:com.ego.order.service.impl
 * @version:1.0
 */
@Service
public class TbOrderServiceImpl implements TbOrderService {
    @Reference
    private TbItemDubboService tbItemDubboServiceImpl;
    @Reference
    private TbOrderDubboService tbOrderDubboServiceImpl;
    @Resource
    private JedisDao jedisDaoImpl;

    @Value("${passport.getUserInfo.url}")
    String passportUrl;
    @Value("${cart.key}")
    String cartKey;

    @Override
    public List<TbItemChild> showOrderCart(List<Long> ids, HttpServletRequest request) {
        // 获取redis中的key
        String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
        String jsonUser = HttpClientUtil.doPost(passportUrl + token);
        EgoResult er = JsonUtils.jsonToPojo(jsonUser, EgoResult.class);
        String key = cartKey + ((LinkedHashMap) er.getData()).get("username");

        // 获取购物车中所有的数据
        String json = jedisDaoImpl.get(key);
        List<TbItemChild> list = JsonUtils.jsonToList(json, TbItemChild.class);

        // 判断并取出购物车中加入结算订单的数据
        List<TbItemChild> listNew = new ArrayList<>();
        for (TbItemChild tbItemChild : list) {
            for (long id : ids) {
                if (tbItemChild.getId().longValue() == id) {
                    // 判断商品库存是否充足
                    if (tbItemDubboServiceImpl.selById(id).getNum() >= tbItemChild.getNum()) {
                        tbItemChild.setEnough(true);
                    } else {
                        tbItemChild.setEnough(false);
                    }

                    listNew.add(tbItemChild);
                }
            }
        }

        return listNew;
    }

    @Override
    public EgoResult createOrder(MyOrderParams myOrderParams, HttpServletRequest request) {
        // 获取redis中的用户信息
        String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
        String jsonUser = HttpClientUtil.doPost(passportUrl + token);
        EgoResult er = JsonUtils.jsonToPojo(jsonUser, EgoResult.class);
        Map mapUser = (LinkedHashMap) er.getData();

        long userId = Long.parseLong(mapUser.get("id").toString());
        String username = mapUser.get("username").toString();
        long orderId = IDUtils.genItemId();
        Date date = new Date();

        // 订单表数据
        TbOrder tbOrder = new TbOrder();
        tbOrder.setPayment(myOrderParams.getPayment());
        tbOrder.setPaymentType(myOrderParams.getPaymentType());
        tbOrder.setOrderId(orderId + "");
        tbOrder.setUserId(userId);
        tbOrder.setBuyerNick(username);
        tbOrder.setCreateTime(date);
        tbOrder.setUpdateTime(date);
        tbOrder.setBuyerRate(0);

        // 订单商品表数据
        for (TbOrderItem tbOrderItem : myOrderParams.getOrderItems()) {
            tbOrderItem.setId(IDUtils.genItemId() + "");
            tbOrderItem.setOrderId(orderId + "");
        }

        // 收货人信息表
        TbOrderShipping orderShipping = myOrderParams.getOrderShipping();
        orderShipping.setOrderId(orderId + "");
        orderShipping.setCreated(date);
        orderShipping.setUpdated(date);

        EgoResult egoResult = new EgoResult();
        try {
            int result = tbOrderDubboServiceImpl.insOrder(tbOrder, myOrderParams.getOrderItems(), myOrderParams.getOrderShipping());
            if (result == 1) {
                er.setStatus(200);
                // 删除购物车redis数据中已经购买的商品
                String json = jedisDaoImpl.get(cartKey + mapUser.get("username"));
                List<TbItemChild> listCart = JsonUtils.jsonToList(json, TbItemChild.class);
                List<TbItemChild> listNew = new ArrayList<>();
                for (TbItemChild cartItem : listCart) {
                    for (TbOrderItem orderItem : myOrderParams.getOrderItems()) {
                        if (cartItem.getId().longValue() == Long.parseLong(orderItem.getItemId())) {
                            listNew.add(cartItem);
                        }
                    }
                }

                // 删除已经购买的商品
                for (TbItemChild tbItemChild : listNew) {
                    listCart.remove(tbItemChild);
                }

                // 将新的购物车数据重新放入redis中
                jedisDaoImpl.set(cartKey + mapUser.get("username"), JsonUtils.objectToJson(listCart), 0);
            }
        } catch (Exception e) {
            er.setMsg(e.getMessage());
            e.printStackTrace();
        }

        return er;
    }
}
