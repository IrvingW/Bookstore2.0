package action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import model.Book;
import model.OrderProfile;
import service.CartService;
import service.OrderService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderAction extends ActionSupport{
    private CartService cartService;
    private OrderService orderService;

    private String address;
    private String password;
    private Map params;

    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    public CartService getCartService() {
        return cartService;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Map getParams() {
        return params;
    }

    public void setParams(Map params) {
        this.params = params;
    }

    public String buyCheckout(){
        Map buyInfo = this.cartService.showBuyCart();
        List<Map<String, Object>> cartList = (List<Map<String, Object>>)buyInfo.get("cartList");
        Integer totalPrice = (Integer)buyInfo.get("totalPrice");
        ActionContext.getContext().put("cartList",cartList.isEmpty()?null:cartList);
        ActionContext.getContext().put("totalPrice",totalPrice);
        return "buyCheckout";
    }

    public String createOrder() throws Exception{
        this.params = new HashMap();
        params = this.orderService.createOrder(address,password);
        return "ajax";
    }

    public String showMyOrder(){
        this.params = this.orderService.showMyOrder();
        List orderProfileList = (List)params.get("orderProfileList");
        ActionContext.getContext().put("orderList",orderProfileList);
        return "showMyOrder";
    }
}
