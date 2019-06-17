package com.ego.dubbo.service;

import com.ego.commons.pojo.EasyUIDataGrid;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemParamItem;

import java.util.List;

/**
 * @Auther:pcb
 * @Date:19/5/27
 * @Description:com.ego.dubbo.service
 * @version:1.0
 */
public interface TbItemDubboService {
    /**
     * 商品分页查询
     * @param page
     * @param rows
     * @return
     */
    EasyUIDataGrid show(int page,int rows);

    /**
     * 根据商品id查询商品信息
     * @param id
     * @return
     */
    TbItem selById(Long id);

    /**
     * 根据商品id查询商品参数信息
     * @param itemId
     * @return
     */
    TbItemParamItem selParamByItemId(long itemId);

    /**
     * 根据商品id查询商品描述信息
     * @param itemId
     * @return
     */
    TbItemDesc selDescByItemId(long itemId);

    /**
     * 根据id批量修改商品状态
     * @param tbItem
     * @return
     */
    int updItemStatus(TbItem tbItem);

    /**
     * 新增商品
     * @param tbItem
     * @return
     */
    int insItem(TbItem tbItem);

    /**
     * 新增商品,商品描述和商品参数
     * @param tbItem
     * @param tbItemDesc
     * @return
     */
    int insItemDesc(TbItem tbItem, TbItemDesc tbItemDesc, TbItemParamItem tbItemParamItem) throws Exception;

    /**
     * 根据id修改商品，商品描述和商品参数
     * @param tbItem
     * @param tbItemDesc
     * @param tbItemParamItem
     * @return
     */
    int updItem(TbItem tbItem,TbItemDesc tbItemDesc,TbItemParamItem tbItemParamItem) throws Exception;

    /**
     * 通过状态查询所有可用数据
     * @param status
     * @return
     */
    List<TbItem> selAllByStatus(byte status);
}
