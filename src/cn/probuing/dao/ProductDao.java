package cn.probuing.dao;

import cn.probuing.domain.Category;
import cn.probuing.domain.Product;
import cn.probuing.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

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

    /**
     * 数据库操作查询全部分类
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
     * 获取商品总条数
     *
     * @param cid
     * @return
     */
    public int getCount(String cid) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select count(*) from product where cid=?";
        Long query = (Long) runner.query(sql, new ScalarHandler(), cid);
        return query.intValue();
    }

    /**
     * @param cid          分类id
     * @param index        起始索引
     * @param currentCount 每页显示条数
     * @return 商品列表
     */
    public List<Product> findProductByPage(String cid, int index, int currentCount) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from product where cid=? limit ?,?";
        return runner.query(sql, new BeanListHandler<Product>(Product.class), cid, index, currentCount);
    }

    /**
     * 通过pid在数据库查询商品
     *
     * @param pid
     * @return
     */
    public Product findProductByPid(String pid) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from product where pid= ?";
        Product product = runner.query(sql, new BeanHandler<Product>(Product.class), pid);
        return product;
    }
}
