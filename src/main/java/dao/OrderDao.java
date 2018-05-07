package dao;

import model.Order;

import java.util.List;

public interface OrderDao extends BaseDao {
    List<Order> getOrdersByUserID(int userID);
    Order getOrderByID(int orderID);
    Integer getMaxOrderID();
    List<Order> getAllOrders();
}
