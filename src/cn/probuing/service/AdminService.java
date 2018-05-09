package cn.probuing.service;

import cn.probuing.dao.AdminDao;
import cn.probuing.domain.Category;
import cn.probuing.domain.Order;
import cn.probuing.domain.Product;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @Auther: wxblack-mac
 * @Date: 2018/5/4 16:06
 * @Description:
 */
public class AdminService {
    /**
     * 查询获取所有分类
     * @return
     */
    public List<Category> findAllCategory() throws SQLException {
        AdminDao dao = new AdminDao();
        return dao.findAllCategory();
    }

    /**
     *
     * @param product
     */
    public void saveProduct(Product product) throws SQLException {
        AdminDao dao = new AdminDao();
        dao.saveProduct(product);
    }

    /**
     * 查询所有的订单数据
     * @return
     */
    public List<Order> findAllOrders() throws SQLException {
        AdminDao dao = new AdminDao();
        return dao.findAllOrders();
    }

    /**
     *
     * @param oid
     * @return
     */
    public List<Map<String, Object>> findOrderInfoByOid(String oid) throws SQLException {

        return new AdminDao().findOrderInfoByOid(oid);
    }
}
