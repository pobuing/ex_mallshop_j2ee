package cn.probuing.dao;

import cn.probuing.domain.Category;
import cn.probuing.domain.Order;
import cn.probuing.domain.Product;
import cn.probuing.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @Auther: wxblack-mac
 * @Date: 2018/5/4 16:07
 * @Description:
 */
public class AdminDao {
    /**
     * 操作数据库 查询所有分类数据
     *
     * @return
     */
    public List<Category> findAllCategory() throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from category";
        List<Category> categoryList = runner.query(sql, new BeanListHandler<Category>(Category.class));
        return categoryList;
    }

    /**
     * 数据库操作
     *
     * @param product
     */
    public void saveProduct(Product product) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "insert into product value(?,?,?,?,?,?,?,?,?,?)";
        runner.update(sql,
                product.getPid(),
                product.getPname(),
                product.getMarket_price(),
                product.getShop_price(),
                product.getPimage(),
                product.getPdate(),
                product.getIs_hot(),
                product.getPdesc(),
                product.getPflag(),
                product.getCategory().getCid());

    }

    /**
     * 数据库查询订单表
     *
     * @return
     */
    public List<Order> findAllOrders() throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from orders";
        return runner.query(sql, new BeanListHandler<Order>(Order.class));


    }

    /**
     * 数据库操作查询订单信息
     *
     * @param oid
     * @return
     */
    public List<Map<String, Object>> findOrderInfoByOid(String oid) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select p.pimage,p.pname,p.shop_price,i.count,i.subtotal from orderitem i,product p where i.pid = p.pid and i.oid = ?";
        return runner.query(sql, new MapListHandler(), oid);
    }
}
