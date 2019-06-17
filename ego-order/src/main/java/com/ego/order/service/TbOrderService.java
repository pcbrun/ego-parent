package com.ego.order.service;

import com.ego.commons.pojo.EgoResult;
import com.ego.commons.pojo.TbItemChild;
import com.ego.order.pojo.MyOrderParams;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Auther:pcb
 * @Date:19/6/12
 * @Description:com.ego.order.service
 * @version:1.0
 */
public interface TbOrderService {
    /**
     * 获取订单信息包含的商品
     * @param ids
     * @param request
     * @return
     */
    List<TbItemChild> showOrderCart(List<Long> ids, HttpServletRequest request);

    /**
     * 创建订单
     * @param myOrderParams
     * @param request
     * @return
     */
    EgoResult createOrder(MyOrderParams myOrderParams,HttpServletRequest request);
}
