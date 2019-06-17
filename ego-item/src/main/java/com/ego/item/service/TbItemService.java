package com.ego.item.service;

import com.ego.commons.pojo.TbItemChild;

/**
 * @Auther:pcb
 * @Date:19/6/9
 * @Description:com.ego.item.service
 * @version:1.0
 */
public interface TbItemService {
    /**
     * 显示商品详情
     * @param id
     * @return
     */
    TbItemChild show(Long id);
}
