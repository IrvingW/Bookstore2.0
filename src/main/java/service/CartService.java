package service;

import java.util.Map;

public interface CartService extends BaseService {
    Map showBuyCart();
    boolean addToBuyCart(int bookID, int amount);
    boolean removeFromBuyCart(int bookID, int amount);
    boolean emptyBuyCart();
}
