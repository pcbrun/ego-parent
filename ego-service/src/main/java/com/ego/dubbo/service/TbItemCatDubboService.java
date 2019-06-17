package com.ego.dubbo.service;

import com.ego.pojo.TbItemCat;

import java.util.List;

/**
 * @Auther:pcb
 * @Date:19/5/28
 * @Description:com.ego.dubbo.service
 * @version:1.0
 */
public interface TbItemCatDubboService {
    /**
     * 根据父id查询所有子类目
     * @param pid
     * @return
     */
    List<TbItemCat> show(long pid);

    /**
     * 根据id查询类目
     * @param id
     * @return
     */
    TbItemCat selByID(long id);
}
