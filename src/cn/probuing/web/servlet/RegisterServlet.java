package cn.probuing.web.servlet;

import cn.probuing.domain.User;
import cn.probuing.service.RegisterService;
import cn.probuing.utils.CommonsUtils;
import cn.probuing.utils.MailUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author WX
 * @date 2018/4/18 16:08
 * @describe: 注册功能web层 Servlet
 * good luck
 */
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        // TODO:获取传递的参数
        Map<String, String[]> parameterMap = request.getParameterMap();
        //封装User对象
        User user = new User();
        try {
            //指定类型转换器（String ---> date）
            ConvertUtils.register(new Converter() {
                @Override
                public Object convert(Class aClass, Object o) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = null;
                    try {
                        date = format.parse((String) o);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return date;
                }
            }, Date.class);
            //映射封装
            BeanUtils.populate(user, parameterMap);
            String activeCode = CommonsUtils.getUUID();


        user.setUid(activeCode);
        //激活码设置为UUID
        user.setCode(activeCode);
        RegisterService registerService = new RegisterService();
        boolean regist = registerService.regist(user);
        //注册成功
        if (regist) {
            //发送激活邮件
            String emailMsg = "恭喜您注册成功，请点击下面的连接" +
                    " <a href='192.168.1.13:8080/mall/active?activeCode=" + activeCode + "'>" + "" +
                    "http://192.168.1.13:8080/mall/active?activeCode=" + activeCode + "</a>";
            MailUtils.sendMail(user.getEmail(),emailMsg);
            //跳转到注册成功页面
            response.sendRedirect(request.getContextPath() + "/registerSuccess.jsp");
        } else {
            response.sendRedirect(request.getContextPath() + "/registerFail.jsp");
        }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
