package kafka;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Checkout implements Serializable {
    private String password;
    private String address;
    private int userID;
    private List<Map<String,Object>> cartList;

    public Checkout(){
        super();
    }

    public Checkout(String address,String password,int userID,List<Map<String,Object>> cartList){
        this.address = address;
        this.password = password;
        this.userID = userID;
        this.cartList = cartList;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public List<Map<String, Object>> getCartList() {
        return cartList;
    }

    public void setCartList(List<Map<String, Object>> cartList) {
        this.cartList = cartList;
    }
}
