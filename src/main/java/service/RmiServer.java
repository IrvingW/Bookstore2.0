package service;

import org.springframework.beans.factory.annotation.Autowired;
import service.imp.RmiInterfImpl;
import service.RmiInterf;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;


public class RmiServer {

    public static void main(String[] args){

        try{
            RmiInterfImpl server = new RmiInterfImpl();
            LocateRegistry.createRegistry(1097);
            java.rmi.Naming.rebind("rmi://localhost:1097/server",server);
            System.out.println("Server working...");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}

