package service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface RmiInterf extends Remote{
    Map returnResult(int orderID, String trackingNo) throws RemoteException;
}
