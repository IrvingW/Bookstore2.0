package action;

import common.constants.OrderStatus;
import model.Order;
import service.OrderService;
import service.RmiClient;

import com.opensymphony.xwork2.ActionSupport;
import service.imp.RmiClientImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;



public class TestRmi extends ActionSupport {

    private Map params;
    private OrderService orderService;
    //private RmiClient rmiClient;

    private String sid;
    private String trackingNo;
    private int orderID;


    /*
    public RmiClient getRmiClient(){return rmiClient;}
    public void setRmiClient(RmiClient rmiClient){this.rmiClient = rmiClient;}
*/
    public Map getParams() {
        return params;
    }

    public void setParams(Map params) {
        this.params = params;
    }

    public String getSid(){return sid;}

    public void setSid(String sid){this.sid = sid;}


    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }



    public String testrmi() {
        this.params = new HashMap();
        System.out.println("action here and sid="+this.sid);
        //String result = this.rmiClient.shipping(this.sid);
        this.params.put("success",true);
        //this.params.put("response",result);
        return "ajax";
    }

    public String shipOrder(){
        RmiClient rmiClient = new RmiClientImpl();
        this.params = rmiClient.shipping(this.orderID,this.trackingNo);
        Order order = this.orderService.getOrderById(this.orderID);
        if(order != null){
            order.setStatus(OrderStatus.COMPLETED);
            order.setTrackingNo(this.trackingNo);
            order.setFhDate(new Date());
            this.orderService.updateOrderStatus(order);
        }
        return "ajax";
    }
}
