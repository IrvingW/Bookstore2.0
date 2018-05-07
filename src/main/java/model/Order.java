package model;

import common.constants.OrderStatus;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Order implements Serializable {
    private int orderID;
    private int buyerID;
    private String address;
    private Date orderDate;
    private Date payDate;
    private Date fhDate;
    private String trackingNo;
    private Integer totalPrice;
    private OrderStatus status;
    private String orderStatus;
    //private List<OrderItem> orderItemList;

    public Order(){}

    public Order(int buyerID, Date orderDate, int totalPrice, OrderStatus status, String address) {
        super();
        this.buyerID = buyerID;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.status = status;
        this.address = address;
    }

    public Date getFhDate() {
        return fhDate;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public Date getPayDate() {
        return payDate;
    }

    public int getBuyerID() {
        return buyerID;
    }

    public int getOrderID() {
        return orderID;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public String getAddress() {
        return address;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBuyerID(int buyerID) {
        this.buyerID = buyerID;
    }

    public void setFhDate(Date fhDate) {
        this.fhDate = fhDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    /*
    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }
    */
}
