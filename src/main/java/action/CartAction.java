package action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import service.CartService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartAction extends ActionSupport {
    private CartService cartService;

    private int bookID;
    private int amount;
    private Map params;
    private List<Map<String,Object>> cart;

    public List<Map<String, Object>> getCart() {
        return cart;
    }

    public void setCart(List<Map<String, Object>> cart) {
        this.cart = cart;
    }

    public Map getParams() {
        return params;
    }

    public void setParams(Map params) {
        this.params = params;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public CartService getCartService() {
        return cartService;
    }

    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }


    public String addToBuyCart(){
        params = new HashMap();
        boolean result = this.cartService.addToBuyCart(this.bookID,this.amount);
        if(result){
            params.put("success",true);
        }
        else{
            params.put("success",false);
        }
        return "ajax";
    }

    public String addOneToCart(){
        boolean result = this.cartService.addToBuyCart(this.bookID,1);
        if(result) return "addOneToCart";
        return "fail";
    }

    public String removeOneFromCart(){
        boolean result = this.cartService.removeFromBuyCart(this.bookID,-1);
        if(result) return "removeOneFromCart";
        return "fail";
    }

    public String showBuyCart(){
        Map cartInfo = this.cartService.showBuyCart();
        this.cart = (List<Map<String,Object>>)cartInfo.get("cartList");
        int totalPrice = (int) cartInfo.get("totalPrice");
        ActionContext.getContext().put("cartList",cart);
        ActionContext.getContext().put("totalPrice",totalPrice);
        return "showBuyCart";
    }

    public String emptyBuyCart(){
        this.cartService.emptyBuyCart();
        return "emptyBuyCart";
    }


}
