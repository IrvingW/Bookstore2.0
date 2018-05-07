package service.imp;

import common.constants.OrderStatus;
import dao.OrderDao;
import dao.impl.OrderDaoImpl;
import model.Order;
import org.aspectj.weaver.ast.Or;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import service.RmiInterf;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RmiInterfImpl extends UnicastRemoteObject implements RmiInterf {
    private static final long serialVersionUID = 42L;

    public RmiInterfImpl() throws RemoteException{
        super();
    }

    @Override
    public Map returnResult(int orderID, String trackingNo) {
        Map result = new HashMap();
        result.put("success",true);
        String response = "订单"+orderID+"发货成功！";
        result.put("response",response);
        return result;
    }


    /*
    public static void main(String[] args){
        try{
            RmiInterf server = new RmiInterfImpl();
            LocateRegistry.createRegistry(1099);
            java.rmi.Naming.rebind("rmi://localhost:1099/server",server);
            System.out.println("Server working...");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    */

}
