package com.ego.manage.service;

import com.ego.commons.pojo.EasyUITree;

import java.util.List;

/**
 * @Auther:pcb
 * @Date:19/5/28
 * @Description:com.ego.manage.service
 * @version:1.0
 */
public interface TbItemCatService {
    /**
     * 根据父菜单id显示所有子菜单并转换为EasyUITree的属性要求
     * @param id
     * @return
     */
    List<EasyUITree> show(long id);
}
