package com.ego.cart.service;

import com.ego.commons.pojo.EgoResult;
import com.ego.commons.pojo.TbItemChild;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Auther:pcb
 * @Date:19/6/11
 * @Description:com.ego.cart.service
 * @version:1.0
 */
public interface CartService {
    /**
     * 加入购物车
     * @param id
     * @param num
     * @param request
     */
    void addCart(long id, int num, HttpServletRequest request);

    /**
     * 显示购物车
     * @param request
     * @return
     */
    List<TbItemChild> showCart(HttpServletRequest request);

    /**
     * 根据商品id修改购物车商品数量
     * @param itemId
     * @param num
     * @return
     */
    EgoResult updCart(long itemId,int num,HttpServletRequest request);

    /**
     * 根据商品id删除购物车商品
     * @param itemId
     * @param request
     * @return
     */
    EgoResult delCart(long itemId, HttpServletRequest request);
}
