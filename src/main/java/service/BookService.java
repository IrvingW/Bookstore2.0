package service;

import model.Book;
import model.BookProfile;
import model.Category1;

import java.util.List;
import java.util.Map;

public interface BookService extends BaseService {
    Boolean uploadBook(BookProfile bookProfile);
    Boolean updateBook(BookProfile bookProfile);
    Boolean deleteBook(int bookID);
    BookProfile showBookProfile(int bookID);
    List<Book> showAllBooks();
    List<Book> searchByText(String searchText);
    List<Book> showBooksByCategory1Name(String category1Name);
    List<Book> showBooksByCategory2Name(String category2Name);
    List<Category1> showAllCategory1s();
    Map getInfoByIsbn(String isbn);
    List<Book> showBookByCondition(String category1Name,String category2Name,String searchText);
    List<Book> getRecommendBookList();
}
