package cn.probuing.web.servlet;

import cn.probuing.service.RegisterService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Auther: wxblack-mac
 * @Date: 2018/4/19 15:22
 * @Description:检查用户名是否存在
 */
public class CheckUserNameServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        RegisterService service = new RegisterService();
        boolean isExists = service.checkUserName(username);
        String resultJson = "{\"isExist\":" + isExists + "}";
        //将验证结果写回到页面中
        response.getWriter().write(resultJson);
    }
}
