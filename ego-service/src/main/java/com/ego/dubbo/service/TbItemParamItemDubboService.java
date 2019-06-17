package com.ego.dubbo.service;

import com.ego.pojo.TbItemParamItem;

/**
 * @Auther:pcb
 * @Date:19/6/9
 * @Description:com.ego.dubbo.service
 * @version:1.0
 */
public interface TbItemParamItemDubboService {
    /**
     * 根据商品id查询商品规格参数
     * @param itemId
     * @return
     */
    TbItemParamItem selByItemId(long itemId);
}
