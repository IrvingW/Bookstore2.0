package dao.impl;

import dao.OrderDao;
import model.Order;

import org.hibernate.query.Query;
import java.util.List;

public class OrderDaoImpl extends BaseDaoImpl implements OrderDao {
    @Override
    public List<Order> getOrdersByUserID(int buyerID) {
        String hql = "from Order where buyerID = :buyerID order by orderDate desc";
        Query query = getSession().createQuery(hql).setParameter("buyerID",buyerID);
        List<Order> orders = query.list();
        return orders;
    }

    @Override
    public Order getOrderByID(int orderID) {
        String hql = "from Order where orderID = :orderID";
        Query query = getSession().createQuery(hql).setParameter("orderID",orderID);
        List<Order> orders = query.list();
        Order order = orders.size() == 1 ? orders.get(0) : null;
        return order;
    }

    @Override
    public Integer getMaxOrderID() {
        String hql = "select max(a.orderID) from Order as a";
        Query query = getSession().createQuery(hql);
        List list = query.list();
        if(list == null) return 0;
        Integer id = (int)query.list().get(0);
        return id;
    }

    @Override
    public List<Order> getAllOrders() {
        String hql = "from Order order by orderDate desc";
        Query query = getSession().createQuery(hql);
        List<Order> orders = query.list();
        if(orders != null)  return orders;
        return null;
    }
}
