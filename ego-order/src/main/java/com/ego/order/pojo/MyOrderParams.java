package com.ego.order.pojo;

import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;

import java.util.List;

/**
 * @Auther:pcb
 * @Date:19/6/12
 * @Description:com.ego.order.pojo
 * @version:1.0
 */
public class MyOrderParams {
    private String payment;
    private int paymentType;
    private List<TbOrderItem> orderItems;
    private TbOrderShipping orderShipping;

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public int getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(int paymentType) {
        this.paymentType = paymentType;
    }

    public List<TbOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<TbOrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public TbOrderShipping getOrderShipping() {
        return orderShipping;
    }

    public void setOrderShipping(TbOrderShipping orderShipping) {
        this.orderShipping = orderShipping;
    }

    @Override
    public String toString() {
        return "MyOrderParams{" +
                "payment='" + payment + '\'' +
                ", paymentType=" + paymentType +
                ", orderItems=" + orderItems +
                ", orderShipping=" + orderShipping +
                '}';
    }
}
