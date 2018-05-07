package service.imp;

import service.RmiClient;
import service.RmiInterf;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Map;

public class RmiClientImpl implements RmiClient{
    private RmiInterf rmiInterf;

    public RmiInterf getRmiInterf(){return rmiInterf;}

    public void setRmiInterf(RmiInterf rmiInterf){this.rmiInterf = rmiInterf;}

    /*
    @Override
    public String shipping(String sid){
        try {
            System.out.println("client here and sid=" + sid);
            String url = "rmi://localhost:1097/server";
            this.rmiInterf = (RmiInterf) Naming.lookup(url);
            System.out.println(this.rmiInterf.returnResult(sid));
            return this.rmiInterf.returnResult(sid);
        } catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
    */

    @Override
    public Map shipping(int orderID, String trackingNo) {
        try{
            String url = "rmi://localhost:1097/server";
            RmiInterf rmiInterf = (RmiInterf) Naming.lookup(url);
            if(rmiInterf==null) System.out.println("rmiIntef is null");
            return rmiInterf.returnResult(orderID,trackingNo);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
