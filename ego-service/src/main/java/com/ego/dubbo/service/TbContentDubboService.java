package com.ego.dubbo.service;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.pojo.TbContent;

import java.util.List;

/**
 * @Auther:pcb
 * @Date:19/5/30
 * @Description:com.ego.dubbo.service
 * @version:1.0
 */
public interface TbContentDubboService {
    /**
     * 分页查询内容
     * @param categoryId
     * @param page
     * @param rows
     * @return
     */
    EasyUIDataGrid selByPage(long categoryId,int page ,int rows);

    /**
     * 新增内容
     * @param tbContent
     * @return
     */
    int insContent(TbContent tbContent);

    /**
     * 查询最近的前n个内容
     * @param count
     * @param isSort
     * @return
     */
    List<TbContent> selByCount(int count,boolean isSort);
}
