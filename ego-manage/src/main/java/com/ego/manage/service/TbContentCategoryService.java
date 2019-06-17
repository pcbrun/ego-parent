package com.ego.manage.service;

import com.ego.commons.pojo.EasyUITree;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbContentCategory;

import java.util.List;

/**
 * @Auther:pcb
 * @Date:19/5/30
 * @Description:com.ego.manage.service
 * @version:1.0
 */
public interface TbContentCategoryService {
    /**
     * 根据父菜单id查询所有子菜单并转换为EasyUITree的属性要求
     * @param id
     * @return
     */
    List<EasyUITree> showContentCategory(long id);

    /**
     * 内容类目新增
     * @param tbContentCategory
     * @return
     */
    EgoResult insertContentCategory(TbContentCategory tbContentCategory);

    /**
     * 内容类目重命名
     * @param tbContentCategory
     * @return
     */
    EgoResult updateContentCategory(TbContentCategory tbContentCategory);

    /**
     * 内容类目删除
     * @param tbContentCategory
     * @return
     */
    EgoResult delete(TbContentCategory tbContentCategory);
}
