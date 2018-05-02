package cn.probuing.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Auther: wxblack-mac
 * @Date: 2018/5/2 15:18
 * @Description:支付成功回调
 */
public class PayCallBackServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 浏览器重定向
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println("<h1>付款成功！等待商城进一步操作！等待收货...</h1>");
    }
}
