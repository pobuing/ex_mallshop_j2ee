package cn.probuing.web.servlet;

import cn.probuing.domain.PageBean;
import cn.probuing.domain.Product;
import cn.probuing.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: wxblack-mac
 * @Date: 2018/4/20 11:15
 * @Description: 商品列表页
 */
public class ProductListByCidServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获得cid
        String cid = request.getParameter("cid");
        String currentPage = request.getParameter("currentPage");
        if (currentPage == null) {
            currentPage = "1";
        }
        int currentCount = 12;
        //根据cid查询商品
        ProductService productService = new ProductService();
        PageBean<Product> pageBean = productService.findProductListByCid(cid, Integer.parseInt(currentPage), currentCount);
        request.setAttribute("pageBean", pageBean);
        request.setAttribute("cid", cid);
        //定义一个用于记录历史商品信息的集合
        List<Product> historyProduct = new ArrayList<Product>();
        //获得客户端携带的pids的cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("pids")) {
                    String pids = cookie.getValue();
                    String[] split = pids.split("-");
                    for (String p : split) {
                        Product product = productService.findProductByPid(p);
                        historyProduct.add(product);
                    }
                }
            }
        }
        //将历史记录集合放入到域中
        request.setAttribute("historyProduct", historyProduct);
        //请求转发
        request.getRequestDispatcher("/product_list.jsp").forward(request, response);
    }
}
