package model;

import common.constants.OrderStatus;

import java.util.Date;
import java.util.List;

public class OrderProfile extends Order{
    private List<OrderItemProfile> orderItemProfileList;

    public OrderProfile() {}

    public OrderProfile(int buyerID, Date orderDate, int totalPrice, OrderStatus status, String address) {
        super(buyerID, orderDate,totalPrice,status,address);
    }

    public List<OrderItemProfile> getOrderItemProfileList() {
        return orderItemProfileList;
    }

    public void setOrderItemProfileList(List<OrderItemProfile> orderItemProfileList) {
        this.orderItemProfileList = orderItemProfileList;
    }
}
