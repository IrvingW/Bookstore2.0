package dao.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import dao.BookDao;
import model.Book;
import org.bson.types.ObjectId;
import org.hibernate.query.Query;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BookDaoImpl extends BaseDaoImpl implements BookDao {
    @Override
    public Book getBookByID(int bookID) {
        String hql = "from Book b where b.bookID = :bookID";
        Query query = getSession().createQuery(hql).setParameter("bookID", bookID);
        List<Book> books = query.list();
        Book book = books.size() == 1 ? books.get(0) : null;
        return book;
    }

    @Override
    public List<Book> getAllBooks() {
        String hql = "from Book order by bookName";
        Query query = getSession().createQuery(hql);
        List<Book> result = query.list();
        return result;
    }

    @Override
    public List<Book> getBooksByCategory1Name(String category1Name) {
        String hql = "from Book b where b.category1 = :category1Name order by bookName";
        Query query = getSession().createQuery(hql).setParameter("category1Name",category1Name);
        List<Book> result = query.list();
        return result;
    }

    @Override
    public List<Book> getBooksByCategory2Name(String category2Name) {
        String hql = "from Book b where b.category2 = :category2Name order by bookName";
        Query query = getSession().createQuery(hql).setParameter("category2Name",category2Name);
        List<Book> result = query.list();
        return result;
    }

    @Override
    public List<Book> serachByText(String searchText) {
        // 全局搜索，搜索字符串在书名、作者、出版社等多个字段同时尝试匹配
        String hql = " select b from Book as b where 1=1 ";
        hql += " or b.bookName like :bookName ";
        hql += " or b.author like :author ";
        hql += " or b.press like :press ";
        hql += " order by bookName ";
        Query query = getSession().createQuery(hql);
        query.setParameter("bookName", "%"+searchText+"%");
        query.setParameter("author", "%"+searchText+"%");
        query.setParameter("press", "%"+searchText+"%");
        List<Book> result = query.list();
        return result;
    }

    @Override
    public Map getBookProfileInMongo(String profileID) {
        DBCollection collection = getMongoDb().getCollection("book_profile");
        DBObject query=new BasicDBObject("_id", new ObjectId(profileID));
        DBObject obj = collection.findOne(query);
        Map bookProfileInMongo = (obj!=null) ? (Map)obj : null;
        return bookProfileInMongo;
    }

    @Override
    public String saveBookProfileInMongo(Map bookProfileInMongo) {
        DBCollection collection = getMongoDb().getCollection("book_profile");
        BasicDBObject document = new BasicDBObject(bookProfileInMongo);
        collection.insert(document);
        return ((ObjectId)document.get("_id")).toString();
    }

    @Override
    public boolean updateBookProfileInMongo(Book book, Map bookProfileInMongo) {
        DBCollection collection = getMongoDb().getCollection("book_profile");
        DBObject query=new BasicDBObject("_id", new ObjectId(book.getProfileID()));
        DBObject old = collection.findOne(query);

        BasicDBObject document = new BasicDBObject(bookProfileInMongo);
        if(old!=null) {
            collection.update(query, document);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean deleteBookProfileInMongo(String profileID) {
        DBCollection collection = getMongoDb().getCollection("book_profile");
        DBObject query=new BasicDBObject("_id", new ObjectId(profileID));
        collection.remove(query);
        return true;
    }

    @Override
    public List<Book> getBooksByConditions(Map condition) {
        String hqlTables = " from Book as b";
        String hqlConditions = " where 1=1 ";
        String hql = " select b ";

        List<Object> args = new ArrayList<Object>();
        List<Type> types = new ArrayList<Type>();

        if(condition.containsKey("category1")) {
            hqlConditions += " and b.category1=? ";
            args.add(condition.get("category1"));
            types.add(new StringType());
        }
        if(condition.containsKey("category2")) {
            hqlConditions += " and b.category2=? ";
            args.add(condition.get("category2"));
            types.add(new StringType());
        }
        if(condition.containsKey("searchText")) {
            hqlConditions += " and (b.bookName like ? or b.author like ? or b.press like ?) ";
            args.add("%"+condition.get("searchText")+"%");
            types.add(new StringType());

            args.add("%"+condition.get("searchText")+"%");
            types.add(new StringType());

            args.add("%"+condition.get("searchText")+"%");
            types.add(new StringType());
        }
        hqlConditions += " order by bookName ";
        hql = hql + hqlTables + hqlConditions;
        System.out.println(hql);
        Query query = getSession().createQuery(hql);
        query.setParameters(args.toArray(), types.toArray(new Type[0]));
        List<Book> books = query.list();
        return books;
    }

    @Override
    public List<Book> getRecommendBookList() {
        // 查找推荐书籍：被借阅次数最多的totalCount本书
        int totalCount = 8;
        String hql1 = "select b.bookID from Book as b, OrderItem as oi where b.bookID=oi.bookID group by b.bookID order by count(*) desc";
        Query query1 = getSession().createQuery(hql1);
        query1.setFirstResult(0);
        query1.setMaxResults(totalCount);
        List<Integer> bookIDList1 = query1.list();
        System.out.println("bookIDList1");
        System.out.println(bookIDList1.size());
        if(bookIDList1.size() < totalCount) { /////////
            String hql2 = "select b.bookID from Book as b, BookRelease as br where b.bookID=br.bookID and not exists (from BorrowHistory as bh where bh.bookID=b.bookID) order by br.releaseTime desc";
            Query query2 = getSession().createQuery(hql2);
            query2.setFirstResult(0);
            query2.setMaxResults(totalCount-bookIDList1.size());
            List<Integer> bookIDList2 = query2.list();
            System.out.println("bookIDList2");
            System.out.println(bookIDList2.size());
            bookIDList1.addAll(bookIDList2);
        }
        List<Book> bookList = new ArrayList<Book>();
        for(Integer bookID : bookIDList1) {
            bookList.add(this.getBookByID(bookID));
        }
        return bookList;
    }
}
