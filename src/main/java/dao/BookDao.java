package dao;

import model.Book;

import java.util.List;
import java.util.Map;

public interface BookDao extends BaseDao{
    Book getBookByID(int bookID);
    List<Book> getAllBooks();
    List<Book> getBooksByCategory1Name(String category1Name);
    List<Book> getBooksByCategory2Name(String category2Name);
    List<Book> serachByText(String searchText);
    List<Book> getBooksByConditions(Map condition);

    Map getBookProfileInMongo(String profileID);
    String saveBookProfileInMongo(Map bookProfileInMongo);
    boolean updateBookProfileInMongo(Book book, Map bookProfileInMongo);
    boolean deleteBookProfileInMongo(String profileID);
    List<Book> getRecommendBookList();

}
