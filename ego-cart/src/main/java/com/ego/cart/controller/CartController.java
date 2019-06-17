package com.ego.cart.controller;

import com.ego.cart.service.CartService;
import com.ego.commons.pojo.EgoResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Auther:pcb
 * @Date:19/6/11
 * @Description:com.ego.cart.controller
 * @version:1.0
 */
@Controller
public class CartController {
    @Resource
    private CartService cartServiceImpl;

    /**
     * 添加购物车
     *
     * @param id
     * @return
     */
    @RequestMapping("/cart/add/{id}.html")
    public String addCart(@PathVariable long id, int num, HttpServletRequest request) {
        cartServiceImpl.addCart(id, num, request);
        return "cartSuccess";
    }

    /**
     * 显示购物车
     *
     * @return
     */
    @RequestMapping("/cart/cart.html")
    public String showCart(Model model,HttpServletRequest request) {
        model.addAttribute("cartList",cartServiceImpl.showCart(request));
        return "cart";
    }

    /**
     * 修改购物车商品数量
     * @param itemId
     * @param num
     * @param request
     * @return
     */
    @RequestMapping("/cart/update/num/{itemId}/{num}.action")
    @ResponseBody
    public EgoResult updCart(@PathVariable long itemId,@PathVariable int num, HttpServletRequest request){
        return cartServiceImpl.updCart(itemId,num,request);
    }

    /**
     * 删除购物车商品
     * @param itemId
     * @param request
     * @return
     */
    @RequestMapping("/cart/delete/{itemId}.action")
    @ResponseBody
    public EgoResult delCart(@PathVariable long itemId,HttpServletRequest request){
        return cartServiceImpl.delCart(itemId,request);
    }
}
