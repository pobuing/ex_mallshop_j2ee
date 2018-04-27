package cn.probuing.web.servlet;

import cn.probuing.domain.User;
import cn.probuing.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @Auther: wxblack-mac
 * @Date: 2018/4/27 14:50
 * @Description:
 */
public class UserServlet extends BaseServlet {
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //获取session域
        HttpSession session = request.getSession();
        //获取用户输入的用户名和密码
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //将用户名和密码传递到service层
        UserService service = new UserService();
        User user = null;
        try {
            user = service.login(username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //判断用户是否登录成功
        if (user != null) {
            //判断用户是否勾选自动登录
            String autoLogin = request.getParameter("autoLogin");
            if ("true".equals(autoLogin)) {
                //自动登录 将用户存储到cookie中
                Cookie cookie_username = new Cookie("cookie_username", user.getUsername());
                //两天保留cookie时间
                cookie_username.setMaxAge(10 * 60 * 2);
                Cookie cookie_password = new Cookie("cookie_password", user.getPassword());
                cookie_password.setMaxAge(10 * 60 * 2);
                response.addCookie(cookie_username);
                response.addCookie(cookie_password);
            }
            //将user对象存到session中
            session.setAttribute("user", user);
            //重定向到首页
            response.sendRedirect(request.getContextPath() + "/product?method=index");
        } else {
            request.setAttribute("loginInfo", "用户名或密码错误");
            //登录失败 跳转到登录页
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }

    }

}
