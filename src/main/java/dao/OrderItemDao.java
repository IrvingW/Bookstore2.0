package dao;

import model.OrderItem;

import java.util.List;

public interface OrderItemDao extends BaseDao {
    List<OrderItem> getOrderItemByOrderID(int orderID);
}
