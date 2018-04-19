package cn.probuing.service;

import cn.probuing.dao.ProductDao;
import cn.probuing.domain.Product;

import java.sql.SQLException;
import java.util.List;

/**
 * @Auther: wxblack-mac
 * @Date: 2018/4/19 18:09
 * @Description: 商品服务类
 */
public class ProductService {

    /**
     * 获得最热商品
     *
     * @return 商品list
     */
    public List<Product> findHotProduct() {
        ProductDao productDao = new ProductDao();
        List<Product> hotProducts = null;
        try {
            hotProducts = productDao.findHotProduct();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hotProducts;
    }

    /**
     * 获得最新商品
     *
     * @return
     */
    public List<Product> findNewProduct() {
        ProductDao productDao = new ProductDao();
        List<Product> newProducts = null;
        try {
            newProducts = productDao.findNewProduct();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newProducts;
    }
}
