package service;


import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Map;

public interface RmiClient {

    Map shipping(int orderID, String trackingNo);

    /*
    public static void main(String[] args){
        try {
            String url = "rmi://localhost:1099/server";
            RmiInterf RmiObject = (RmiInterf) Naming.lookup(url);
            System.out.println(RmiObject.returnResult("123"));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    */
}
