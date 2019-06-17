package com.ego.manage.service;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbItemParam;

/**
 * @Auther:pcb
 * @Date:19/5/29
 * @Description:com.ego.manage.service
 * @version:1.0
 */
public interface TbItemParamService {
    /**
     * 分页显示商品参数
     * @param page
     * @param rows
     * @return
     */
    EasyUIDataGrid showPage(int page,int rows);

    /**
     * 批量删除商品参数
     * @param ids
     * @return
     */
    int delete(String ids) throws Exception;

    /**
     * 根据商品类目id查询参数模板
     * @param itemCatId
     * @return
     */
    EgoResult showParam(long itemCatId);

    /**
     * 新增商品模板信息
     * @param tbItemParam
     * @return
     */
    EgoResult insert(TbItemParam tbItemParam);
}
