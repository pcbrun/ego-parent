package com.ego.dubbo.service;

import com.ego.pojo.TbItemDesc;

/**
 * @Auther:pcb
 * @Date:19/5/28
 * @Description:com.ego.dubbo.service
 * @version:1.0
 */
public interface TbItemDescDubboService {
    /**
     * 新增商品描述
     * @param itemDesc
     * @return
     */
    int insTbItemDesc(TbItemDesc itemDesc);

    /**
     * 根据主键商品id查询商品描述
     * @param itemId
     * @return
     */
    TbItemDesc selByItemId(long itemId);
}
