package action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import model.Book;
import model.BookProfile;
import model.Order;
import model.User;
import service.AdminService;
import service.BookService;
import service.OrderService;
import service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminAction extends ActionSupport {
    private AdminService adminService;
    private BookService bookService;
    private OrderService orderService;

    private int userID;
    private String email;
    private String password;
    private Map params;
    private BookProfile bookProfile;

    public AdminService getAdminService(){return adminService;}
    public void setAdminService(AdminService adminService){this.adminService = adminService;}
    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }
    public Map getParams() {
        return params;
    }
    public void setParams(Map params) {
        this.params = params;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public BookService getBookService() {
        return bookService;
    }

    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    public BookProfile getBookProfile() {
        return bookProfile;
    }

    public void setBookProfile(BookProfile bookProfile) {
        this.bookProfile = bookProfile;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }



    public String showAllUserList(){
        List<User> list = this.adminService.showAllUserList();
        List<Book> bookList = this.bookService.showAllBooks();
        List<Order> orderList = this.orderService.showAllOrders();
        ActionContext.getContext().put("userList",list);
        ActionContext.getContext().put("bookList",bookList);
        ActionContext.getContext().put("orderList",orderList);
        return "showAllUserList";
    }

    public String getOldUserInfo(){
        this.params = new HashMap();
        User user = this.adminService.getOldUserInfo(userID);
        params.put("userID",user.getUserID());
        params.put("email",user.getEmail());
        params.put("password",user.getPassword());
        return "ajax";
    }

    public String deleteUser(){
        if(this.adminService.deleteUser(userID)){
            return "deleteUser";
        }
        return null;
    }

    public String addUser(){
        if(this.adminService.addUser(email,password)){
            return "addUser";
        }
        return null;
    }

    public String updateUser(){
        if(this.adminService.updateUser(userID,email,password)){
            return "updateUser";
        }
        return null;
    }

    public String showBookRelease(){
        ActionContext.getContext().put("category1List",this.bookService.showAllCategory1s());
        return "showBookRelease";
    }

    public String uploadBook(){
        this.bookService.uploadBook(bookProfile);
        return "uploadBook";
    }
}
