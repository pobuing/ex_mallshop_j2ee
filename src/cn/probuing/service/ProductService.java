package cn.probuing.service;

import cn.probuing.dao.ProductDao;
import cn.probuing.domain.Category;
import cn.probuing.domain.Order;
import cn.probuing.domain.PageBean;
import cn.probuing.domain.Product;
import cn.probuing.utils.DataSourceUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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

    /**
     * 查询所有分类
     *
     * @return
     */
    public List<Category> findAllCategory() {
        ProductDao productDao = new ProductDao();
        List<Category> categoryList = null;
        try {
            categoryList = productDao.findAllCategory();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryList;
    }

    /**
     * 根据cid查询商品数据
     *
     * @param cid
     * @param currentPage
     * @param currentCount
     * @return
     */
    public PageBean<Product> findProductListByCid(String cid, int currentPage, int currentCount) {
        PageBean<Product> pageBean = new PageBean<>();
        ProductDao productDao = new ProductDao();
        //封装当前页
        pageBean.setCurrentPage(currentPage);
        pageBean.setCurrentCount(currentCount);
        int totalCount = 0;
        try {
            totalCount = productDao.getCount(cid);

            pageBean.setTotalCount(totalCount);
            //封装总页数
            int totalPage = (int) Math.ceil(1.0 * totalCount / currentCount);
            pageBean.setTotalPage(totalPage);
            //获取当前页显示的数据
            int index = (currentPage - 1) * currentCount;
            List<Product> productList = productDao.findProductByPage(cid, index, currentCount);
            pageBean.setList(productList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pageBean;
    }

    /**
     * 通过pid获取product对象
     *
     * @param pid
     * @return
     */
    public Product findProductByPid(String pid) {
        ProductDao productDao = new ProductDao();
        Product product = null;
        try {
            product = productDao.findProductByPid(pid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    /**
     * 提交订单到数据库
     *
     * @param order
     */
    public void submitOrder(Order order) {
        //开启事物
        try {
            DataSourceUtils.startTransaction();
            ProductDao dao = new ProductDao();
            dao.addOrders(order);
            dao.addOrderItem(order);
        } catch (SQLException e) {
            try {
                DataSourceUtils.rollback();
                e.printStackTrace();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                DataSourceUtils.commitAndRelease();
            } catch (SQLException e) {
                try {
                    DataSourceUtils.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

            }
        }

    }

    /**
     * 更新收货人地址
     *
     * @param order
     */
    public void updateOrderAdrr(Order order) throws SQLException {
        ProductDao dao = new ProductDao();
        dao.updateOrderAdrr(order);
    }

    /**
     * 修改订单状态
     *
     * @param order 订单实体
     */
    public void updateOrderState(Order order) throws SQLException {
        ProductDao dao = new ProductDao();
        dao.updateOrderState(order);
    }


    public List<Order> findAllOrders(String uId) throws SQLException {
        ProductDao dao = new ProductDao();
        List<Order> list = dao.findAllOrders(uId);
        return list;
    }

    public List<Map<String, Object>> findAllOrderItemByOid(String oid) throws SQLException {
        ProductDao dao = new ProductDao();
        List<Map<String, Object>> list = dao.findAllOrderItemByOid(oid);
        return list;
    }
}
