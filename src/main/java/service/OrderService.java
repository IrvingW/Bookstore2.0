package service;

import model.Order;

import java.util.List;
import java.util.Map;

public interface OrderService extends BaseService {
    Map showMyOrder();
    Map createOrder(String address,String password) throws Exception;
    boolean kafkaCreateOrder(int userID, String address, String password, List<Map<String,Object>> cartList) throws Exception;
    Map confirmOrder();
    boolean cancelOrder();
    List<Order> showAllOrders();
    Order getOrderById(int orderID);
    boolean updateOrderStatus(Order order);

}
