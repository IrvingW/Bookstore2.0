package kafka;

import com.opensymphony.xwork2.ActionContext;
import model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KafkaController {
    //private ProducerDemo producerDemo;
    //private ReceiverDemo receiverDemo;

    private String message;
    private String address;
    private String password;
    private Map params;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map getParams() {
        return params;
    }

    public void setParams(Map params) {
        this.params = params;
    }

    /*
    public ProducerDemo getProducerDemo() {
        return producerDemo;
    }

    public ReceiverDemo getReceiverDemo() {
        return receiverDemo;
    }

    public void setProducerDemo(ProducerDemo producerDemo) {
        this.producerDemo = producerDemo;
    }

    public void setReceiverDemo(ReceiverDemo receiverDemo) {
        this.receiverDemo = receiverDemo;
    }
    */



    public String send(){
        System.out.println("on send");
        User user = (User)ActionContext.getContext().getSession().get("userInfo");
        List<Map<String,Object>> cartList = (List<Map<String,Object>>)ActionContext.getContext().getSession().get("buyCart");

        if(user == null) return "fail";
        if(cartList == null) return "fail";

        int userID = user.getUserID();
        System.out.println(userID+address+password);
        ProducerDemo producerDemo = new ProducerDemo();
        producerDemo.sendMsg(this.address,this.password,userID,cartList);
        ActionContext.getContext().getSession().remove("buyCart");
        this.params = new HashMap();
        params.put("success",true);
        return "ajax";
    }

    public String receive(){
        System.out.println("receive");
        //String msg = receiverDemo.receive();
        //ActionContext.getContext().put("msg",msg);
        return "receive";
    }



}
