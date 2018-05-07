package webService;

import dao.BookDao;
import dao.OrderDao;
import model.Book;
import model.Order;

import java.util.List;

public class TestWebServiceImpl implements TestWebService {
    private BookDao bookDao;
    private OrderDao orderDao;

    public OrderDao getOrderDao() {
        return orderDao;
    }

    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public BookDao getBookDao() {
        return bookDao;
    }

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public List<Book> showAllBooks() {
        List<Book> bookList = this.bookDao.getAllBooks();
        return bookList;
    }

    @Override
    public List<Order> showMyOrders(int userID) {
        List<Order> orderList = this.orderDao.getOrdersByUserID(userID);
        return orderList;
    }

    @Override
    public List<Order> showAllOrders() {
        List<Order> orderList = this.orderDao.getAllOrders();
        return orderList;
    }
}
