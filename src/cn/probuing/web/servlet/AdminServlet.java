package cn.probuing.web.servlet;

import cn.probuing.domain.Category;
import cn.probuing.domain.Order;
import cn.probuing.service.AdminService;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @Auther: wxblack-mac
 * @Date: 2018/5/4 16:03
 * @Description: 后台管理adminServlet
 */
public class AdminServlet extends BaseServlet {
    public void findAllCategory(HttpServletRequest request, HttpServletResponse response) {
        try {
            AdminService service = new AdminService();
            List<Category> categoryList = service.findAllCategory();
            Gson gson = new Gson();
            String categoryJson = gson.toJson(categoryList);
            response.setContentType("text/json;charset=UTF-8");
            response.getWriter().write(categoryJson);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得所有订单
     *
     * @param request
     * @param response
     */
    public void findAllOrders(HttpServletRequest request, HttpServletResponse response) {
        //查询所有订单
        AdminService service = new AdminService();
        try {
            List<Order> orderList = service.findAllOrders();
            request.setAttribute("orderList", orderList);
            request.getRequestDispatcher("/admin/order/list.jsp").forward(request, response);
        } catch (SQLException | ServletException | IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 根据订单id查询订单项和商品信息
     *
     * @param request
     * @param response
     */
    public void findOrderInfoByOid(HttpServletRequest request, HttpServletResponse response) {
        try {
            Thread.sleep(2000);
            String oid = request.getParameter("oid");
            AdminService service = new AdminService();
            List<Map<String, Object>> mapList = service.findOrderInfoByOid(oid);
            Gson gson = new Gson();
            String json = gson.toJson(mapList);
            System.out.println(json);
            //处理中文
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}