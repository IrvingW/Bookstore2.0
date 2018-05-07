package action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import model.Book;
import model.UserProfile;
import service.*;
import service.imp.CategoryCheckPermission;
import service.imp.SimpleLoginModule;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import java.util.List;

public class PermissionAction extends ActionSupport {
    private String category1Name;
    private String category2Name;
    private String searchText;
    private String username;
    private String password;

    private LoginModule loginModule;
    private UserService userService;
    private BookService bookService;

    public BookService getBookService() {
        return bookService;
    }

    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public LoginModule getLoginModule() {
        return loginModule;
    }

    public void setLoginModule(LoginModule loginModule) {
        this.loginModule = loginModule;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCategory1Name() {
        return category1Name;
    }

    public String getCategory2Name() {
        return category2Name;
    }

    public void setCategory1Name(String category1Name) {
        this.category1Name = category1Name;
    }

    public void setCategory2Name(String category2Name) {
        this.category2Name = category2Name;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public String getSearchText() {
        return searchText;
    }

    public String showAllBooks(){
        if(this.category1Name == null) this.category1Name = "";
        if(this.category2Name == null) this.category2Name = "";
        if(this.searchText == null) this.searchText = "";

        String checkStatement = "";
        if(category1Name != "") checkStatement = this.category1Name;
        else if(category2Name != "") checkStatement = this.category2Name;
        else if(searchText != "") checkStatement = this.searchText;
        else checkStatement = "";
        if(checkStatement != ""){
            System.out.println(checkStatement);
            String type = "child";
            UserProfile userProfile = this.userService.showUserProfile();
            if(userProfile != null){
                int age = Integer.parseInt(userProfile.getAge());
                System.out.println(age);
                if(age < 18) type = "child";
                else type = "adult";
            }
            System.out.println("type="+type);
            System.setProperty("java.security.policy", "/Users/lvjiawei/YetAnotherBookstore/src/main/java/PermissionTest.policy");
            System.setSecurityManager(new SecurityManager());
            CategoryCheckPermission permission = new CategoryCheckPermission(checkStatement,type);
            SecurityManager manager = System.getSecurityManager();
            try{
                if (manager != null){
                    System.out.println("manage not null");
                    manager.checkPermission(permission);
                }
            } catch (SecurityException e){
                return "fail";
            }
        }
        List<Book> bookList = this.bookService.showBookByCondition(category1Name,category2Name,searchText);
        ActionContext.getContext().put("category1Name", this.category1Name);
        ActionContext.getContext().put("category2Name", this.category2Name);
        ActionContext.getContext().put("searchName", this.searchText);
        ActionContext.getContext().put("category1List",this.bookService.showAllCategory1s());
        ActionContext.getContext().put("allBooks",bookList);
        return "showAllBooks";
    }

    public String LoginAuth(){
        System.setProperty("java.security.auth.login.config","/Users/lvjiawei/YetAnotherBookstore/src/main/java/jaas.config");
        System.setProperty("java.security.policy", "/Users/lvjiawei/YetAnotherBookstore/src/main/java/PermissionTest.policy");
        System.setSecurityManager(new SecurityManager());

        /*
        try{
            LoginContext loginContext = new LoginContext("Login1", new SimpleCallbackHandler(username,password.toCharArray()));
            Subject subject = loginContext.getSubject();
            loginContext.login();
            System.out.println("login success");
            Subject.doAsPrivileged(subject,new SysPropAction("user.home"),null);
            return "success";
        } catch (LoginException e){
            e.printStackTrace();
            return "fail";
        }
        */


        try {
            SimpleCallbackHandler simpleCallbackHandler = new SimpleCallbackHandler(username,password.toCharArray());
            Subject subject = new Subject();
            loginModule.initialize(subject,simpleCallbackHandler,null,null);
            if(loginModule.login()){
                System.out.println("login success");
                Subject.doAsPrivileged(subject,new SysPropAction("user.home"),null);
                return "success";
            }else {
                System.out.println("login fail");
                return "fail";
            }
        } catch (LoginException e){
            e.printStackTrace();
        }

        return "success";
    }
}
