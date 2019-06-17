package com.ego.manage.service;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.commons.pojo.EgoResult;
import com.ego.pojo.TbItem;

/**
 * @Auther:pcb
 * @Date:19/5/27
 * @Description:com.ego.manage.service
 * @version:1.0
 */
public interface TbItemService {

    /**
     * 显示商品
     * @param page
     * @param rows
     * @return
     */
    EasyUIDataGrid showPage(int page,int rows);

    /**
     * 根据商品id查询商品参数信息
     * @param itemId
     * @return
     */
    EgoResult selParamByItemId(long itemId);

    /**
     * 根据商品id查询商品描述信息
     * @param itemId
     * @return
     */
    EgoResult selDescByItemId(long itemId);

    /**
     * 批量修改商品状态
     * @param ids
     * @param status
     * @return
     */
    int updateStatus(String ids,byte status);

    /**
     * 新增商品
     * @param tbItem
     * @param desc
     * @return
     */
    int insert(TbItem tbItem,String desc,String itemParams) throws Exception;

    /**
     * 修改商品
     * @param tbItem
     * @param desc
     * @param itemParams
     * @return
     */
    EgoResult updateItem(TbItem tbItem,String desc,String itemParams,long itemParamId);
}
