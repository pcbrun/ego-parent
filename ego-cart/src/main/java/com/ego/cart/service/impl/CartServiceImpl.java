package com.ego.cart.service.impl;

import com.ego.cart.service.CartService;
import com.ego.commons.pojo.EgoResult;
import com.ego.commons.pojo.TbItemChild;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.HttpClientUtil;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.pojo.TbItem;
import com.ego.redis.dao.JedisDao;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Auther:pcb
 * @Date:19/6/11
 * @Description:com.ego.cart.service.impl
 * @version:1.0
 */
@Service
public class CartServiceImpl implements CartService {
    @Reference
    private TbItemDubboService tbItemDubboServiceImpl;
    @Resource
    private JedisDao jedisDaoImpl;

    @Value("${passport.getUserInfo.url}")
    String passportUrl;
    @Value("${cart.key}")
    String cartKey;

    @Override
    public void addCart(long id, int num, HttpServletRequest request) {
        // 集合存放所有购物车商品
        List<TbItemChild> list = new ArrayList<>();

        // 获取redis中的key
        String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
        String jsonUser = HttpClientUtil.doPost(passportUrl + token);
        EgoResult er = JsonUtils.jsonToPojo(jsonUser, EgoResult.class);
        String key = cartKey + ((LinkedHashMap) er.getData()).get("username");

        // 判断redis中是否存在key
        if (jedisDaoImpl.exists(key)) {
            String json = jedisDaoImpl.get(key);
            if (json != null && !json.equals("")) {
                // 存在key就直接把数据redis中的集合取出赋给list
                list = JsonUtils.jsonToList(json, TbItemChild.class);

                for (TbItemChild tbItemChild : list) {
                    // 判断购物车中是否存在该商品
                    if (tbItemChild.getId().longValue() == id) {
                        // 修改此商品数量
                        tbItemChild.setNum(tbItemChild.getNum() + num);
                        // 将购物车数据重新放入到redis中
                        jedisDaoImpl.set(key, JsonUtils.objectToJson(list), 0);
                        return;
                    }
                }
            }
        }

        // 如果redis中不存在key或者不存在此商品
        TbItem tbItem = tbItemDubboServiceImpl.selById(id);
        TbItemChild tbItemChild = new TbItemChild();

        tbItemChild.setId(tbItem.getId());
        tbItemChild.setTitle(tbItem.getTitle());
        tbItemChild.setImages(tbItem.getImage() == null || tbItem.getImage().equals("") ? new String[1] : tbItem.getImage().split(","));
        tbItemChild.setNum(num);
        tbItemChild.setPrice(tbItem.getPrice());

        // 将此商品加入购物车中
        list.add(tbItemChild);

        // 再将购物车数据放入redis中
        jedisDaoImpl.set(key, JsonUtils.objectToJson(list), 0);

    }

    @Override
    public List<TbItemChild> showCart(HttpServletRequest request) {
        // 获取redis中的key
        String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
        String jsonUser = HttpClientUtil.doPost(passportUrl + token);
        EgoResult er = JsonUtils.jsonToPojo(jsonUser, EgoResult.class);
        String key = cartKey + ((LinkedHashMap) er.getData()).get("username");

        if (jedisDaoImpl.exists(key)) {
            String json = jedisDaoImpl.get(key);
            if (json != null && !json.equals("")) {
                return JsonUtils.jsonToList(json, TbItemChild.class);
            }
        }

        return null;
    }

    @Override
    public EgoResult updCart(long itemId, int num, HttpServletRequest request) {
        // 获取redis中的key
        String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
        String jsonUser = HttpClientUtil.doPost(passportUrl + token);
        EgoResult er = JsonUtils.jsonToPojo(jsonUser, EgoResult.class);
        String key = cartKey + ((LinkedHashMap) er.getData()).get("username");

        String json = jedisDaoImpl.get(key);
        List<TbItemChild> list = JsonUtils.jsonToList(json, TbItemChild.class);
        for (TbItemChild tbItemChild : list) {
            if (tbItemChild.getId().longValue() == itemId) {
                tbItemChild.setNum(num);
            }
        }

        String ok = jedisDaoImpl.set(key, JsonUtils.objectToJson(list), 0);
        EgoResult egoResult=new EgoResult();
        if (ok.equals("OK")){
            egoResult.setStatus(200);
        }

        return egoResult;
    }

    @Override
    public EgoResult delCart(long itemId, HttpServletRequest request) {
        // 获取redis中的key
        String token = CookieUtils.getCookieValue(request, "TT_TOKEN");
        String jsonUser = HttpClientUtil.doPost(passportUrl + token);
        EgoResult er = JsonUtils.jsonToPojo(jsonUser, EgoResult.class);
        String key = cartKey + ((LinkedHashMap) er.getData()).get("username");

        String json = jedisDaoImpl.get(key);
        List<TbItemChild> list = JsonUtils.jsonToList(json, TbItemChild.class);
        TbItemChild child=null;

        // Vector、ArrayList迭代时不能进行修改
        for (TbItemChild tbItemChild : list) {
            if (tbItemChild.getId().longValue() == itemId) {
                // 接收要删除的对象
                child=tbItemChild;
            }
        }

        // 从list中删除
        list.remove(child);

        String ok = jedisDaoImpl.set(key, JsonUtils.objectToJson(list), 0);
        EgoResult egoResult=new EgoResult();
        if (ok.equals("OK")){
            egoResult.setStatus(200);
        }

        return egoResult;
    }
}
