package com.ego.item.service;

/**
 * @Auther:pcb
 * @Date:19/6/9
 * @Description:com.ego.item.service
 * @version:1.0
 */
public interface TbItemDescService {
    /**
     * 根据商品id查询商品描述
     * @param itemId
     * @return
     */
    String showDesc(long itemId);
}
