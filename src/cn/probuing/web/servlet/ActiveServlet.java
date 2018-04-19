package cn.probuing.web.servlet;

import cn.probuing.service.RegisterService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Auther: wxblack-mac
 * @Date: 2018/4/19 10:30
 * @Description: 激活webServlet 用户通过邮件链接点击进入
 */
public class ActiveServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取传递的激活码
        String activeCode = request.getParameter("activeCode");
        //执行Service
        RegisterService service = new RegisterService();
        service.active(activeCode);
        //跳转到登录页面
        response.sendRedirect(request.getContextPath() + "/login.jsp");
    }
}
