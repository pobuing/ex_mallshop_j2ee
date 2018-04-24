package cn.probuing.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: wxblack-mac
 * @Date: 2018/4/24 14:38
 * @Description:购物车整体对象
 */
public class Cart {
    private Map<String,CartItem> cartItems = new HashMap<>();
    //商品的总计
    private double total;

    public Map<String, CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Map<String, CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
