package model;

public class OrderItem {
    private int orderItemID;
    private int orderID;
    private int bookID;
    private int bookAmount;
    private int bookPrice;

    public OrderItem(){};

    public OrderItem(int orderID,int bookID,int bookAmount,int bookPrice){
        super();
        this.orderID = orderID;
        this.bookID = bookID;
        this.bookAmount = bookAmount;
        this.bookPrice = bookPrice;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public int getBookID() {
        return bookID;
    }

    public int getBookAmount() {
        return bookAmount;
    }

    public int getBookPrice() {
        return bookPrice;
    }

    public int getOrderItemID() {
        return orderItemID;
    }

    public void setBookAmount(int bookAmount) {
        this.bookAmount = bookAmount;
    }

    public void setBookPrice(int bookPrice) {
        this.bookPrice = bookPrice;
    }

    public void setOrderItemID(int orderItemID) {
        this.orderItemID = orderItemID;
    }
}
