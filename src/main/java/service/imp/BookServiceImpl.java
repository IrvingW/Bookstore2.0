package service.imp;

import common.cache.AllBookCategory;
import common.utils.BeanUtil;
import dao.BookDao;
import dao.CategoryDao;
import dao.ImageDao;
import model.Book;
import model.BookProfile;
import model.Category1;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import redis.RedisClient;
import redis.clients.jedis.Jedis;
import service.BaseService;
import service.BookService;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookServiceImpl extends BaseServiceImpl implements BookService {
    private BookDao bookDao;
    private ImageDao imageDao;
    private CategoryDao categoryDao;
    private RedisClient redisClient;

    /* ===================================================== */

    public BookDao getBookDao() {
        return bookDao;
    }

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public CategoryDao getCategoryDao() {
        return categoryDao;
    }

    public void setCategoryDao(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    public ImageDao getImageDao() {
        return imageDao;
    }

    public void setImageDao(ImageDao imageDao) {
        this.imageDao = imageDao;
    }

    public RedisClient getRedisClient() {
        return redisClient;
    }

    public void setRedisClient(RedisClient redisClient) {
        this.redisClient = redisClient;
    }

    /* ===================================================== */

    @Override
    public Boolean uploadBook(BookProfile bookProfile) {
        Map bookProfileInMongo = new HashMap();

        bookProfileInMongo.put("intro", bookProfile.getIntro());
        if(bookProfile.getOtherPicture() != null) {
            List otherPictureID = new ArrayList();
            for(File tmp : bookProfile.getOtherPicture()) {
                // save otherPicture
                otherPictureID.add(this.imageDao.saveImage(tmp));
            }
            bookProfileInMongo.put("otherPictureID", otherPictureID);
        }

        Book newBook = new Book();
        newBook.setBookName(bookProfile.getBookName());
        newBook.setIsbn(bookProfile.getIsbn());
        newBook.setAuthor(bookProfile.getAuthor());
        newBook.setPress(bookProfile.getPress());
        newBook.setCategory1(bookProfile.getCategory1());
        newBook.setCategory2(bookProfile.getCategory2());
        newBook.setAmount(bookProfile.getAmount());
        newBook.setPrice(bookProfile.getPrice());

        // saveBookProfile
        newBook.setProfileID(this.bookDao.saveBookProfileInMongo(bookProfileInMongo));
        if(bookProfile.getCoverPicture() != null) {
            // save coverPicture
            newBook.setImageID(this.imageDao.saveImage(bookProfile.getCoverPicture()));
        }
        else {
            newBook.setImageID("");
        }
        this.bookDao.save(newBook);

        return true;
    }

    @Override
    public BookProfile showBookProfile(int bookID) {
        // 这里的返回值不仅包括mongodb中的内容，也包括mysql中的内容
        Book book = this.bookDao.getBookByID(bookID);
        Map bookProfileInMongo = this.bookDao.getBookProfileInMongo(book.getProfileID());
        BookProfile bookProfile = new BookProfile();

        bookProfile.setBookID(book.getBookID());
        bookProfile.setBookName(book.getBookName());
        bookProfile.setIsbn(book.getIsbn());
        bookProfile.setAuthor(book.getAuthor());
        bookProfile.setPress(book.getPress());
        bookProfile.setCategory1(book.getCategory1());
        bookProfile.setCategory2(book.getCategory2());
        bookProfile.setImageID(book.getImageID());
        bookProfile.setAmount(book.getAmount());
        bookProfile.setPrice(book.getPrice());
        bookProfile.setIntro((String)bookProfileInMongo.get("intro"));
        bookProfile.setOtherPictureIDList((List<String>)bookProfileInMongo.get("otherPictureID"));

        return bookProfile;
    }

    @Override
    public List<Book> showAllBooks() {
        List<Book> bookList = this.bookDao.getAllBooks();
        return bookList;
    }

    @Override
    public List<Book> searchByText(String searchText) {
        List<Book> bookList = this.bookDao.serachByText(searchText);
        return bookList;
    }

    @Override
    public List<Book> showBooksByCategory1Name(String category1Name) {
        List<Book> bookList = this.bookDao.getBooksByCategory1Name(category1Name);
        return bookList;
    }

    @Override
    public List<Book> showBooksByCategory2Name(String category2Name) {
        List<Book> bookList = this.bookDao.getBooksByCategory2Name(category2Name);
        return bookList;
    }

    @Override
    public Boolean updateBook(BookProfile bookProfile) {
        Integer bookID = bookProfile.getBookID();
        Book oldBook = this.bookDao.getBookByID(bookID);

        Map bookProfileInMongo = this.bookDao.getBookProfileInMongo(oldBook.getProfileID());
        bookProfileInMongo.put("intro", bookProfile.getIntro());
        if(bookProfile.getOtherPicture() != null) {
            List<String> oldOtherPictureID = (List<String>)bookProfileInMongo.get("otherPictureID");
            for(String tmp : oldOtherPictureID) {
                this.imageDao.deleteImageById(tmp);
            }
            List otherPictureID = new ArrayList();
            for(File tmp : bookProfile.getOtherPicture()) {
                // save otherPicture
                otherPictureID.add(this.imageDao.saveImage(tmp));
            }
            bookProfileInMongo.put("otherPictureID", otherPictureID);
        }

        oldBook.setBookName(bookProfile.getBookName());
        oldBook.setIsbn(bookProfile.getIsbn());
        oldBook.setAuthor(bookProfile.getAuthor());
        oldBook.setPress(bookProfile.getPress());
        oldBook.setCategory1(bookProfile.getCategory1());
        oldBook.setCategory2(bookProfile.getCategory2());
        oldBook.setPrice(bookProfile.getPrice());
        oldBook.setAmount(bookProfile.getAmount());

        // updateBookProfile
        this.bookDao.updateBookProfileInMongo(oldBook, bookProfileInMongo);
        if(bookProfile.getCoverPicture() != null) {
            // save coverPicture
            this.imageDao.deleteImageById(oldBook.getImageID());
            oldBook.setImageID(this.imageDao.saveImage(bookProfile.getCoverPicture()));
        }
        this.bookDao.update(oldBook);

        return true;
    }

    @Override
    public Boolean deleteBook(int bookID) {
        // 从数据库中删除书，禁止调用此函数！！！
        Book book = this.bookDao.getBookByID(bookID);

        String profileID = book.getProfileID();
        if(profileID != null) {
            Map bookProfileInMongo = this.bookDao.getBookProfileInMongo(profileID);
            List<String> oldOtherPictureID = (List<String>)bookProfileInMongo.get("otherPictureID");
            for(String tmp : oldOtherPictureID) {
                if(tmp != null) {
                    this.imageDao.deleteImageById(tmp);    // 删除书的后两张图片
                }
            }
            this.bookDao.deleteBookProfileInMongo(profileID);    // 删除书在mongodb中的信息
        }

        String imageID = book.getImageID();
        if(imageID != null) {
            this.imageDao.deleteImageById(imageID);    // 删除书的封面图片
        }

        this.bookDao.delete(book);    // 删除书在mysql中的信息

        return null;
    }

    @Override
    public List<Category1> showAllCategory1s() {
        /*
        if(AllBookCategory.getAllBookCategory()==null) {
            AllBookCategory.setAllBookCategory(this.categoryDao.getAllCategory());
        }
        return AllBookCategory.getAllBookCategory();
        */
        Jedis jedis = redisClient.getJedis();
        List<byte[]> categoryList = jedis.lrange("category1List11".getBytes(), 0, -1);
        long len = jedis.llen("category1List11".getBytes());
        if(len != 0) {
            System.out.println("in cache");
            List<Category1> result = new ArrayList<>();
            for(byte[] bytes : categoryList) {
                Category1 category1 = (Category1) BeanUtil.BytesToObject(bytes);
                result.add(category1);
            }
            return result;
        } else {
            System.out.println("not in cache");
            List<Category1> category1FromDb = this.categoryDao.getAllCategory();
            jedis.del("category1List11".getBytes());
            for(Category1 category1 : category1FromDb) {
                jedis.lpush("category1List11".getBytes(), BeanUtil.ObjectToBytes(category1));
            }
            return category1FromDb;
        }
    }

    @Override
    public Map getInfoByIsbn(String isbn) {
        String url = "https://api.douban.com/v2/book/isbn/"+isbn;
        Map returnMap = new HashMap();
        String result = "{}";
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
        try {
            HttpGet httpGet = new HttpGet(url);
            HttpResponse response = defaultHttpClient.execute(httpGet);
            HttpEntity httpEntity = response.getEntity();
            result = EntityUtils.toString(httpEntity);
            JSONObject json = JSONObject.fromObject(result);

            String[] fieldList = {"author","publisher","title","summary","pages"};
            String[] resultList = {"","","","",""};
            for(int i=0;i < fieldList.length;i++){
                String parse = json.get(fieldList[i]).toString();
                parse = parse.replace("[","");
                parse = parse.replace("]","");
                parse = parse.replace("//",",");
                parse = parse.replaceAll("\"","");
                resultList[i] = parse;
                //System.out.println(resultList[i]);
                returnMap.put(fieldList[i],resultList[i]);
            }
            returnMap.put("success",true);
            return returnMap;
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            defaultHttpClient.getConnectionManager().shutdown();
        }
        returnMap.put("success",false);
        return returnMap;
    }

    @Override
    public List<Book> showBookByCondition(String category1Name, String category2Name, String searchText) {
        Map conditions = new HashMap();
        if(category1Name != null && !category1Name.equals("")){
            conditions.put("category1",category1Name);
        }
        if(category2Name != null && !category2Name.equals("")){
            conditions.put("category2",category2Name);
        }
        if(searchText != null && !searchText.equals("")){
            conditions.put("searchText",searchText);
        }

        List<Book> bookList = this.bookDao.getBooksByConditions(conditions);
        return bookList;
    }

    @Override
    public List<Book> getRecommendBookList() {
        return this.bookDao.getRecommendBookList();
    }
}
