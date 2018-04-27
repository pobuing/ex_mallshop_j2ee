package cn.probuing.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther: wxblack-mac
 * @Date: 2018/4/26 15:07
 * @Description: 订单实体类
 */
public class Order {
    List<OrderItem> orderItems = new ArrayList<OrderItem>();
    private String oid;//订单编号
    private Date orderTime;//下单时间
    private double total;//订单总金额
    private int state;//订单状态
    private String address;//收货地址
    private String name;//收货人
    private String telephone;//收货电话
    private User user;//订单属于哪个用户

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
