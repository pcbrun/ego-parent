package com.ego.item.service;

import com.ego.item.pojo.PortalMenu;

/**
 * @Auther:pcb
 * @Date:19/5/29
 * @Description:com.ego.item.service
 * @version:1.0
 */
public interface TbItemCatService {
    /**
     * 查询所有商品分类并转换为特定类
     * @return
     */
    PortalMenu showCatMenu();
}
