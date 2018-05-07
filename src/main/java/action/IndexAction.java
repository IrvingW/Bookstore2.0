package action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import model.Book;
import service.BookService;

import java.util.List;

public class IndexAction extends ActionSupport {
    private BookService bookService;
    public BookService getBookService() {
        return bookService;
    }
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    public String header(){
        ActionContext.getContext().put("category1List",this.bookService.showAllCategory1s());
        return "showHeader";
    }

    public String index() {
        ActionContext.getContext().put("title","在线书店");
        //List<Book> recommendBookList = this.bookService.getRecommendBookList();
        //ActionContext.getContext().put("recommendBookList",recommendBookList);
        return "showIndex";
    }

    public String locale() {
        return "showIndex";
    }
}
