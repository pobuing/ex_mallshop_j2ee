package cn.probuing.web.filter;

import cn.probuing.domain.User;
import cn.probuing.service.UserService;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @Auther: wxblack-mac
 * @Date: 2018/5/3 11:05
 * @Description:
 */
public class AutoLoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //参数强转
        HttpServletRequest request = (HttpServletRequest) req;
        //取出session中的uer对象
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            String cookie_username = null;
            String cookie_password = null;
            //获取携带的用户名和密码cookie
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    //取出想要的用户登录方面的cookie
                    if ("cookie_username".equals(cookie.getName())) {
                        cookie_username = cookie.getValue();
                    }
                    //取出缪吗
                    if ("cookie_password".equals(cookie.getName())) {
                        cookie_password = cookie.getValue();
                    }
                }
            }
            if (cookie_username != null && cookie_password != null) {
                UserService userService = new UserService();
                try {
                    userService.login(cookie_username, cookie_password);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                // TODO:  自动登录
                request.getSession().setAttribute("user", user);
            }
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
