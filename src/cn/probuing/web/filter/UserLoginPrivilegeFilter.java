package cn.probuing.web.filter;

import cn.probuing.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Auther: wxblack-mac
 * @Date: 2018/5/3 11:12
 * @Description: 用户权限校验拦截器 校验用户是否登录
 */
public class UserLoginPrivilegeFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //校验用户是否登录，校验session中是否存在user对象
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        //取出购物车
        if (user == null) {
            //未登录 跳转到登录
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        } else {
            //已经登录，放行
            chain.doFilter(req, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
