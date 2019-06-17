package com.ego.dubbo.service;

import com.ego.pojo.TbOrder;
import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;

import java.util.List;

/**
 * @Auther:pcb
 * @Date:19/6/12
 * @Description:com.ego.dubbo.service
 * @version:1.0
 */
public interface TbOrderDubboService {
    /**
     * 创建订单
     * @param tbOrder
     * @param listOrderItem
     * @param tbOrderShipping
     * @return
     */
    int insOrder(TbOrder tbOrder, List<TbOrderItem> listOrderItem, TbOrderShipping tbOrderShipping) throws Exception;
}
