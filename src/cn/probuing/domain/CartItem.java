package cn.probuing.domain;

/**
 * @Auther: wxblack-mac
 * @Date: 2018/4/24 14:37
 * @Description:购物车项实体
 */
public class CartItem {
    private int buyNum;
    private Product product;
    //价钱小计
    private double subtotal;

    public int getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(int buyNum) {
        this.buyNum = buyNum;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
}
