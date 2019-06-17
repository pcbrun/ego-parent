package com.ego.manage.service;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbContent;

/**
 * @Auther:pcb
 * @Date:19/5/30
 * @Description:com.ego.manage.service
 * @version:1.0
 */
public interface TbContentService {
    /**
     * 分页显示内容
     * @param categoryId
     * @param page
     * @param rows
     * @return
     */
    EasyUIDataGrid showContent(long categoryId,int page,int rows);

    /**
     * 新增内容
     * @param tbContent
     * @return
     */
    EgoResult insContent(TbContent tbContent);
}
