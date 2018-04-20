package cn.probuing.web.servlet;

import cn.probuing.domain.Product;
import cn.probuing.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @Auther: wxblack-mac
 * @Date: 2018/4/20 14:29
 * @Description: 商品详情web Servlet
 */
public class ProductInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取商品的pid
        String pid = request.getParameter("pid");
        String currentPage = request.getParameter("currentPage");
        String cid = request.getParameter("cid");
        ProductService productService = new ProductService();
        Product product = productService.findProductByPid(pid);
        //存储数据到域中
        request.setAttribute("product", product);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("cid", cid);
        //创建cookie存储pid
        //获得客户端携带的cookie 第一次访问赋值
        String pids = pid;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("pids".equals(cookie.getName())) {
                    pids = cookie.getValue();
                    String[] split = pids.split("-");
                    List<String> asList = Arrays.asList(split);
                    LinkedList<String> list = new LinkedList<>(asList);
                    //判断集合中是否存在当前的pid
                    if (list.contains(pid)) {
                        list.remove(pid);
                    }
                    // TODO:  不包含pid 直接将pid放到list中
                    list.addFirst(pid);
                    StringBuffer sb = new StringBuffer();
                    //为了前台布局 限制显示长度
                    for (int i = 0; i < list.size()&&i<7; i++) {
                        sb.append(list.get(i));

                        sb.append("-");
                    }
                    pids = sb.substring(0, sb.length()-1);
                    System.out.println(pids);
                }
            }
        }
        Cookie cookie_pids = new Cookie("pids", pids);
        response.addCookie(cookie_pids);
        //请求转发
        request.getRequestDispatcher("/product_info.jsp").forward(request, response);

    }
}
