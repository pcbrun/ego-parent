package com.ego.dubbo.service;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.pojo.TbItemParam;

import java.util.List;

/**
 * @Auther:pcb
 * @Date:19/5/29
 * @Description:com.ego.dubbo.service
 * @version:1.0
 */
public interface TbItemParamDubboService {
    /**
     * 显示商品参数信息
     * @param page
     * @param rows
     * @return 当前页显示数据和总条数
     */
    EasyUIDataGrid showPage(int page,int rows);

    /**
     * 根据id批量删除商品参数
     * @param ids
     * @return
     */
    int delByIds(String ids) throws Exception;

    /**
     * 根据商品类目id查询商品模板
     * @param itemCatId
     * @return
     */
    TbItemParam showParam(long itemCatId);

    /**
     * 新增商品模板，支持主键自增
     * @param tbItemParam
     * @return
     */
    int insParam(TbItemParam tbItemParam);
}
