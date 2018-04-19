package cn.probuing.web.servlet;

import cn.probuing.domain.Category;
import cn.probuing.service.ProductService;
import cn.probuing.utils.JedisPoolUtils;
import com.google.gson.Gson;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 返回分类列表的Web
 */
public class CategoryListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //准备分类数据
        ProductService productService = new ProductService();
        //先从缓存中查询categoryList,如果有直接使用，如果没有则从数据库读取，然后再存储到redis中
        Jedis jedis = JedisPoolUtils.getJedis();
        String categoryListJson = jedis.get("categoryListJson");
        Gson gson = new Gson();
        List<Category> categoryList = null;
        if (categoryListJson == null || "".equals(categoryListJson)) {
            categoryList = productService.findAllCategory();
            //转换json
            categoryListJson = gson.toJson(categoryList);
            jedis.set("categoryListJson", categoryListJson);
            System.out.println("已经存储到redis" + categoryListJson);
        } else {
            System.out.println("查询到缓存" + categoryListJson);
        }

        // TODO: 2018/4/19 处理中文乱码问题
        response.setContentType("text/html;charset=UTF-8");
        //写回数据
        response.getWriter().write(categoryListJson);

    }
}
