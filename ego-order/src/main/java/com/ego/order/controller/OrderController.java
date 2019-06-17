package com.ego.order.controller;

import com.ego.commons.pojo.EgoResult;
import com.ego.order.pojo.MyOrderParams;
import com.ego.order.service.TbOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Auther:pcb
 * @Date:19/6/12
 * @Description:com.ego.order.controller
 * @version:1.0
 */
@Controller
public class OrderController {
    @Resource
    private TbOrderService tbOrderServiceImpl;

    /**
     * 显示订单确认页面
     * @return
     */
    @RequestMapping("/order/order-cart.html")
    public String orderCart(@RequestParam("id") List<Long> ids, Model model, HttpServletRequest request){
        model.addAttribute("cartList",tbOrderServiceImpl.showOrderCart(ids,request));
        return "order-cart";
    }

    /**
     * 创建订单
     * @param myOrderParams
     * @param request
     * @return
     */
    @RequestMapping("/order/create.html")
    public String oderCreate(MyOrderParams myOrderParams,HttpServletRequest request){
        EgoResult er = tbOrderServiceImpl.createOrder(myOrderParams, request);
        if(er.getStatus()==200){
        return "my-orders";
        }else {
            return "exception";
        }
    }
}
