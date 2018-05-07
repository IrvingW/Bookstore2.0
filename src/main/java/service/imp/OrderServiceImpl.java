package service.imp;

import common.constants.OrderStatus;
import common.utils.AESUtil;
import common.utils.PasswordUtil;
import dao.BookDao;
import dao.OrderDao;
import dao.OrderItemDao;
import dao.UserDao;
import model.*;
import service.BaseService;
import service.OrderService;

import java.util.*;

public class OrderServiceImpl extends BaseServiceImpl implements OrderService {
    private OrderDao orderDao;
    private OrderItemDao orderItemDao;
    private BookDao bookDao;
    private UserDao userDao;

    public OrderDao getOrderDao() {
        return orderDao;
    }

    public OrderItemDao getOrderItemDao() {
        return orderItemDao;
    }

    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public void setOrderItemDao(OrderItemDao orderItemDao) {
        this.orderItemDao = orderItemDao;
    }

    public BookDao getBookDao() {
        return bookDao;
    }

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Map showMyOrder() {
        User user = getLoginedUserInfo();
        int userID = user.getUserID();
        List<Order> orders = this.orderDao.getOrdersByUserID(userID);
        List<OrderProfile> orderProfileList = new ArrayList<>();
        if(orders != null){
            for(Order order : orders){
                OrderProfile orderProfile = new OrderProfile(order.getBuyerID(),order.getOrderDate(),order.getTotalPrice(),
                        order.getStatus(),order.getAddress());

                int orderID = order.getOrderID();
                List<OrderItem> orderItemList = this.orderItemDao.getOrderItemByOrderID(orderID);
                List<OrderItemProfile> orderItemProfileList = new ArrayList<>();
                    for(OrderItem orderItem : orderItemList){
                        int bookID = orderItem.getBookID();
                        Book book = this.bookDao.getBookByID(bookID);
                        OrderItemProfile orderItemProfile = new OrderItemProfile(orderItem.getOrderID(),orderItem.getBookID(),
                                orderItem.getBookAmount(),orderItem.getBookPrice());
                        orderItemProfile.setAuthor(book.getAuthor());
                        orderItemProfile.setBookName(book.getBookName());
                        orderItemProfile.setCategory1(book.getCategory1());
                        orderItemProfile.setCategory2(book.getCategory2());
                        orderItemProfile.setIsbn(book.getIsbn());
                        orderItemProfile.setPress(book.getPress());
                        orderItemProfile.setImageID(book.getImageID());
                        orderItemProfileList.add(orderItemProfile);
                    }
                orderProfile.setOrderID(orderID);
                orderProfile.setPayDate(order.getPayDate());
                orderProfile.setOrderStatus(order.getStatus().toString());
                orderProfile.setOrderItemProfileList(orderItemProfileList);
                orderProfileList.add(orderProfile);
            }
        }
        Map result = new HashMap();
        result.put("orderProfileList",orderProfileList);
        return result;
    }

    @Override
    public Map createOrder(String address,String password) throws Exception{
        Map result = new HashMap<>();
        boolean success = true;
        User user = getLoginedUserInfo();
        System.out.println("encrypt addr=" + address + " encrypr pwd=" + password);

        String decryprAddr = AESUtil.aesDecrypt(address);
        String decryptPwd = AESUtil.aesDecrypt(password);

        List<Map<String,Object>> cartList = new ArrayList<>();
        if(getHttpSession().containsKey("buyCart")) {
            cartList = (List<Map<String, Object>>)getHttpSession().get("buyCart");
        }
        else {
            cartList = new ArrayList<Map<String, Object>>();
        }

        if(user == null){
            success = false;
        }
        else {
            if(!PasswordUtil.checkPassword(decryptPwd, user.getPassword())) {
                success = false;
            }
            else{
                for(Map<String,Object> cartListItem : cartList){
                    int bookID = (int)cartListItem.get("bookID");
                    int amount = (int)cartListItem.get("amount");
                    Book book = this.bookDao.getBookByID(bookID);
                    if((book.getAmount() - amount) < 0){
                        success = false;
                    }
                }
            }
        }

        if(success){
            Integer totalPrice = 0;
            Order newOrder = new Order();
            //int orderID = this.orderDao.getMaxOrderID() + 1;
            newOrder.setBuyerID(user.getUserID());
            newOrder.setOrderDate(new Date());
            newOrder.setPayDate(new Date());
            newOrder.setTotalPrice(totalPrice);
            newOrder.setAddress(decryprAddr);
            newOrder.setStatus(OrderStatus.NOTSHIPPED);
            //newOrder.setOrderID(orderID);
            this.orderDao.save(newOrder);
            int orderID = newOrder.getOrderID();

            for(Map<String,Object> cartListItem : cartList){
                int bookID = (int) cartListItem.get("bookID");
                int amount = (int) cartListItem.get("amount");
                int price = (int) cartListItem.get("price");
                Book book = this.bookDao.getBookByID(bookID);
                OrderItem orderItem = new OrderItem();
                orderItem.setBookID(bookID);
                orderItem.setBookAmount(amount);
                orderItem.setBookPrice(price);
                orderItem.setOrderID(orderID);
                this.orderItemDao.save(orderItem);
                book.setAmount(book.getAmount() - amount);
                this.bookDao.update(book);
                totalPrice += price;
            }
            if(getHttpSession().containsKey("buyCart")) {
                getHttpSession().remove("buyCart");
            }
            newOrder.setTotalPrice(totalPrice);
            this.orderDao.update(newOrder);
        }


        result.put("success",success);
        return result;
    }

    @Override
    public boolean kafkaCreateOrder(int userID, String address, String password, List<Map<String,Object>> cartList) throws Exception {
        boolean success = true;
        User user = this.userDao.getUserById(userID);

        System.out.println("encrypt addr=" + address + " encrypr pwd=" + password);

        String decryprAddr = AESUtil.aesDecrypt(address);
        String decryptPwd = AESUtil.aesDecrypt(password);

        if(!PasswordUtil.checkPassword(decryptPwd, user.getPassword())) {
            success = false;
        }
        else{
            for(Map<String,Object> cartListItem : cartList){
                int bookID = (int)cartListItem.get("bookID");
                int amount = (int)cartListItem.get("amount");
                Book book = this.bookDao.getBookByID(bookID);
                if((book.getAmount() - amount) < 0){
                    success = false;
                }
            }
        }

        if(success){
            Integer totalPrice = 0;
            Order newOrder = new Order();
            newOrder.setBuyerID(user.getUserID());
            newOrder.setOrderDate(new Date());
            newOrder.setPayDate(new Date());
            newOrder.setTotalPrice(totalPrice);
            newOrder.setAddress(decryprAddr);
            newOrder.setStatus(OrderStatus.NOTSHIPPED);
            this.orderDao.save(newOrder);
            int orderID = newOrder.getOrderID();

            for(Map<String,Object> cartListItem : cartList){
                int bookID = (int) cartListItem.get("bookID");
                int amount = (int) cartListItem.get("amount");
                int price = (int) cartListItem.get("price");
                Book book = this.bookDao.getBookByID(bookID);
                OrderItem orderItem = new OrderItem();
                orderItem.setBookID(bookID);
                orderItem.setBookAmount(amount);
                orderItem.setBookPrice(price);
                orderItem.setOrderID(orderID);
                this.orderItemDao.save(orderItem);
                book.setAmount(book.getAmount() - amount);
                this.bookDao.update(book);
                totalPrice += price;
            }
            newOrder.setTotalPrice(totalPrice);
            this.orderDao.update(newOrder);
        }
        return success;
    }

    @Override
    public Map confirmOrder() {
        return null;
    }

    @Override
    public boolean cancelOrder() {
        return false;
    }

    @Override
    public List<Order> showAllOrders() {
        return this.orderDao.getAllOrders();
    }

    @Override
    public Order getOrderById(int orderID) {
        return this.orderDao.getOrderByID(orderID);
    }

    @Override
    public boolean updateOrderStatus(Order order) {
        return this.orderDao.update(order);
    }


}
