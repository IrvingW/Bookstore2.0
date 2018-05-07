package dao.impl;

import dao.OrderItemDao;
import model.OrderItem;
import org.hibernate.query.Query;

import java.util.List;

public class OrderItemDaoImpl extends BaseDaoImpl implements OrderItemDao {
    @Override
    public List<OrderItem> getOrderItemByOrderID(int orderID) {
        String hql = "from OrderItem where orderID = :orderID";
        Query query = getSession().createQuery(hql).setParameter("orderID",orderID);
        List<OrderItem> orderItems = query.list();
        return orderItems;
    }
}
