package cn.probuing.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Auther: wxblack-mac
 * @Date: 2018/4/23 15:14
 * @Description:
 */
public class BaseServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String methodName = req.getParameter("method");
        //获取调用者的字节码对象
        Class<? extends BaseServlet> aClass = this.getClass();
        //获得当前字节码对象中指定的方法
        try {
            Method aClassMethod = aClass.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            //执行相应方法
            aClassMethod.invoke(this, req, resp);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
