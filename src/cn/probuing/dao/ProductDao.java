package cn.probuing.dao;

import cn.probuing.domain.Product;
import cn.probuing.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * @Auther: wxblack-mac
 * @Date: 2018/4/19 18:11
 * @Description:
 */
public class ProductDao {

    public List<Product> findHotProduct() throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from product where is_hot=? limit ?,?";
        List<Product> productList = runner.query(sql, new BeanListHandler<Product>(Product.class), 1, 0, 9);

        return productList;
    }

    /**
     * 数据库操作查询最新商品
     *
     * @return
     */
    public List<Product> findNewProduct() throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from product order by pdate desc limit ?,?";
        List<Product> products = runner.query(sql, new BeanListHandler<Product>(Product.class), 0, 9);
        return products;
    }
}
