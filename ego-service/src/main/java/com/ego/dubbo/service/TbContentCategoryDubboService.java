package com.ego.dubbo.service;

import com.ego.pojo.TbContentCategory;

import java.util.List;

/**
 * @Auther:pcb
 * @Date:19/5/30
 * @Description:com.ego.dubbo.service
 * @version:1.0
 */
public interface TbContentCategoryDubboService {
    /**
     * 根据父id查询所有子类目
     * @param pid
     * @return
     */
    List<TbContentCategory> selByPid(long pid);

    /**
     * 新增内容类目
     * @param tbContentCategory
     * @return
     */
    int insTbContentCategory(TbContentCategory tbContentCategory);

    /**
     * 根据id修改TbContentCategory属性
     * @param tbContentCategory
     * @return
     */
    int updTbContentCategory(TbContentCategory tbContentCategory);

    /**
     * 通过id查询内容分类详细信息
     * @param id
     * @return
     */
    TbContentCategory selById(long id);
}
