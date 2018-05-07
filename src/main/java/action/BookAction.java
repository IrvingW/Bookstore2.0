package action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import model.Book;
import model.BookProfile;
import service.BookService;

import java.util.List;
import java.util.Map;

public class BookAction extends ActionSupport {
    private BookService bookService;

    private String isbn;
    private Map params;
    private String category1Name;
    private String category2Name;
    private String searchText;
    private int bookID;
    private BookProfile bookProfile;


    public BookService getBookService() {
        return bookService;
    }

    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    public Map getParams() {
        return params;
    }

    public void setParams(Map params) {
        this.params = params;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setCategory2Name(String category2Name) {
        this.category2Name = category2Name;
    }

    public void setCategory1Name(String category1Name) {
        this.category1Name = category1Name;
    }

    public String getCategory2Name() {
        return category2Name;
    }

    public String getCategory1Name() {
        return category1Name;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public BookProfile getBookProfile() {
        return bookProfile;
    }

    public void setBookProfile(BookProfile bookProfile) {
        this.bookProfile = bookProfile;
    }



    public String getInfoByIsbn(){
        this.params = this.bookService.getInfoByIsbn(this.isbn);
        return "ajax";
    }

    public String showAllBooks(){
        if(this.category1Name == null) this.category1Name = "";
        if(this.category2Name == null) this.category2Name = "";
        if(this.searchText == null) this.searchText = "";

        List<Book> bookList = this.bookService.showBookByCondition(category1Name,category2Name,searchText);
        ActionContext.getContext().put("category1Name", this.category1Name);
        ActionContext.getContext().put("category2Name", this.category2Name);
        ActionContext.getContext().put("searchName", this.searchText);
        ActionContext.getContext().put("category1List",this.bookService.showAllCategory1s());
        ActionContext.getContext().put("allBooks",bookList);
        return "showBooks";
    }

    public String showBookProfile(){
        this.bookProfile = this.bookService.showBookProfile(this.bookID);
        ActionContext.getContext().put("bookProfile",bookProfile);
        return "showBookProfile";
    }
}
