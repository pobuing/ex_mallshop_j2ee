package cn.probuing.domain;

/**
 * @Auther: wxblack-mac
 * @Date: 2018/4/26 15:01
 * @Description:
 */
public class OrderItem {
    private String itemid;//订单项的id
    private int count;//订单项内商品的购买数量
    private double subtotal;//订单项小计
    private Product product;//订单项内的商品
    private Order order;//属于的订单

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
