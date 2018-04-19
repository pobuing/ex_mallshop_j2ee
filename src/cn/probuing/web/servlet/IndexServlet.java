package cn.probuing.web.servlet;

import cn.probuing.domain.Product;
import cn.probuing.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Auther: wxblack-mac
 * @Date: 2018/4/19 17:59
 * @Description:首页的web servlet
 */
public class IndexServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ProductService productService = new ProductService();


        //准备热门商品数据----List<Product>
        List<Product> hotProductList = productService.findHotProduct();
        request.setAttribute("hotProductList", hotProductList);
        //准备最新商品数据
        List<Product> newProductList = productService.findNewProduct();
        request.setAttribute("newProductList", newProductList);
        request.getRequestDispatcher("/index.jsp")
                .forward(request,response);
    }
}
