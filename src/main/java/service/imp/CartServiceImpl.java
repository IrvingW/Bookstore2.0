package service.imp;

import dao.BookDao;
import model.Book;
import service.CartService;

import java.util.*;

public class CartServiceImpl extends BaseServiceImpl implements CartService {
    private BookDao bookDao;

    public BookDao getBookDao() {
        return bookDao;
    }

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }


    @Override
    public boolean addToBuyCart(int bookID, int amount) {
        Book book = this.bookDao.getBookByID(bookID);
        if(book == null) return false;
        if(amount <= 0) return false;

        List<Map<String, Object>> cartList;
        if(getHttpSession().containsKey("buyCart")) {
            cartList = (List<Map<String, Object>>)getHttpSession().get("buyCart");
        }
        else {
            cartList = new ArrayList<Map<String, Object>>();
            getHttpSession().put("buyCart", cartList);
        }

        // 如果bookID已存在session中，加上相应数量
        for(Map<String, Object> cartListItem : cartList) {
            if((int)cartListItem.get("bookID") == bookID) {
                int oldAmount = (int)cartListItem.get("amount");
                int oldPrice = (int)cartListItem.get("price");
                cartListItem.put("amount",oldAmount+amount);
                cartListItem.put("price",oldPrice+amount*book.getPrice());
                return true;
            }
        }

        Map<String, Object> newCartListItem = new HashMap();
        newCartListItem.put("bookID", bookID);
        newCartListItem.put("bookName", book.getBookName());
        newCartListItem.put("amount",amount);
        newCartListItem.put("price",amount*book.getPrice());
        cartList.add(newCartListItem);
        return true;
    }

    @Override
    public boolean removeFromBuyCart(int bookID, int amount) {
        Book book = this.bookDao.getBookByID(bookID);
        List<Map<String, Object>> cartList;
        if(getHttpSession().containsKey("buyCart")) {
            cartList = (List<Map<String, Object>>)getHttpSession().get("buyCart");
        }
        else {
            cartList = new ArrayList<Map<String, Object>>();
        }

        /*
        for(Map<String, Object> cartListItem : cartList) {
            if((int)cartListItem.get("bookID") == bookID) {
                int oldAmount = (int)cartListItem.get("amount");
                int newAmount = oldAmount - amount;
                if(newAmount <= 0){
                    cartListItem.clear();
                }
                else{
                    int newPrice = newAmount * book.getPrice();
                    cartListItem.put("amount",newAmount);
                    cartListItem.put("price",newPrice);
                }
            }
        }

        int cnt = 0;
        for(Map<String,Object> cartListItem : cartList){
            if(!cartListItem.isEmpty()) cnt++;
        }
        if(cnt == 0) getHttpSession().remove("buyCart");

        return true;
        */


        Iterator iterator = cartList.iterator();
        while(iterator.hasNext()) {
            Map<String, Object> cartListItem = (Map<String, Object>) iterator.next();
            int existedBookID = (int)cartListItem.get("bookID");
            if(existedBookID == bookID) {
                int oldAmount = (int)cartListItem.get("amount");
                int newAmount = oldAmount + amount;
                if(newAmount <= 0){
                    iterator.remove();
                    //return true;
                }
                else{
                    int oldPrice = (int)cartListItem.get("price");
                    int newPrice = newAmount * book.getPrice();
                    cartListItem.put("price",newPrice);
                    cartListItem.put("amount",newAmount);
                    //return true;
                }
            }
        }
        int cnt = 0;
        for(Map<String,Object> cartListItem : cartList){
            if(!cartListItem.isEmpty()) cnt++;
        }
        System.out.println(cnt);
        if(cnt == 0) emptyBuyCart();

        return true;

    }

    @Override
    public boolean emptyBuyCart() {
        if(getHttpSession().containsKey("buyCart")) {
            getHttpSession().remove("buyCart");
        }
        return true;
    }

    @Override
    public Map showBuyCart() {
        List<Map<String, Object>> cartList;
        Map result = new HashMap();
        Integer totalPrice = 0;
        if(getHttpSession().containsKey("buyCart")) {
            cartList = (List<Map<String, Object>>)getHttpSession().get("buyCart");
        }
        else {
            cartList = new ArrayList<Map<String, Object>>();
        }

        for(Map<String, Object> cartListItem : cartList) {
            int bookID = (int)cartListItem.get("bookID");
            Book book = this.bookDao.getBookByID(bookID);
            String imageID = book.getImageID();
            cartListItem.put("imageID",imageID);
            int price = (int) cartListItem.get("price");
            totalPrice += price;
        }
        result.put("cartList", cartList);
        result.put("totalPrice", totalPrice);
        return result;
    }
}
