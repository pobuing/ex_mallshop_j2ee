package cn.probuing.web.servlet;

import cn.probuing.domain.*;
import cn.probuing.service.ProductService;
import cn.probuing.utils.JedisPoolUtils;
import com.google.gson.Gson;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * @Auther: wxblack-mac
 * @Date: 2018/4/20 17:24
 * @Description:
 */
public class ProductServlet extends BaseServlet {
   /* protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获得请求的指定方法的参数
        String methodName = request.getParameter("method");
        if ("productList".equals(methodName)) {
            productList(request, response);
        } else if ("categoryList".equals(methodName)) {
            categoryList(request, response);
        } else if ("index".equals(methodName)) {
            index(request, response);
        } else if ("productInfo".equals(methodName)) {
            productInfo(request, response);
        }

    }*/

    //显示商品类别的功能
    public void categoryList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
//            System.out.println("已经存储到redis" + categoryListJson);
        } else {
//            System.out.println("查询到缓存" + categoryListJson);
        }

        // TODO: 2018/4/19 处理中文乱码问题
        response.setContentType("text/html;charset=UTF-8");
        //写回数据
        response.getWriter().write(categoryListJson);
    }

    //显示首页功能
    public void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ProductService productService = new ProductService();


        //准备热门商品数据----List<Product>
        List<Product> hotProductList = productService.findHotProduct();
        //准备最新商品数据
        List<Product> newProductList = productService.findNewProduct();
        //准备分类数据
        List<Category> categoryList = productService.findAllCategory();

        //放置数据到request域中
        request.setAttribute("hotProductList", hotProductList);
        request.setAttribute("newProductList", newProductList);
        request.setAttribute("categoryList", categoryList);
        request.getRequestDispatcher("/index.jsp")
                .forward(request, response);
    }

    //显示商品的详细信息
    public void productInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取商品的pid
        String pid = request.getParameter("pid");
        String currentPage = request.getParameter("currentPage");
        String cid = request.getParameter("cid");
        ProductService productService = new ProductService();
        Product product = productService.findProductByPid(pid);
        //存储数据到域中
        request.setAttribute("product", product);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("cid", cid);
        //创建cookie存储pid
        //获得客户端携带的cookie 第一次访问赋值
        String pids = pid;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("pids".equals(cookie.getName())) {
                    pids = cookie.getValue();
                    String[] split = pids.split("-");
                    List<String> asList = Arrays.asList(split);
                    LinkedList<String> list = new LinkedList<>(asList);
                    //判断集合中是否存在当前的pid
                    if (list.contains(pid)) {
                        list.remove(pid);
                    }
                    // TODO:  不包含pid 直接将pid放到list中
                    list.addFirst(pid);
                    StringBuffer sb = new StringBuffer();
                    //为了前台布局 限制显示长度
                    for (int i = 0; i < list.size() && i < 7; i++) {
                        sb.append(list.get(i));

                        sb.append("-");
                    }
                    pids = sb.substring(0, sb.length() - 1);
                    System.out.println(pids);
                }
            }
        }
        Cookie cookie_pids = new Cookie("pids", pids);
        response.addCookie(cookie_pids);
        //请求转发
        request.getRequestDispatcher("/product_info.jsp").forward(request, response);

    }

    //根据商品的类别获取商品的列表
    public void productList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获得cid
        String cid = request.getParameter("cid");
        String currentPage = request.getParameter("currentPage");
        if (currentPage == null) {
            currentPage = "1";
        }
        int currentCount = 12;
        //根据cid查询商品
        ProductService productService = new ProductService();
        PageBean<Product> pageBean = productService.findProductListByCid(cid, Integer.parseInt(currentPage), currentCount);
        request.setAttribute("pageBean", pageBean);
        request.setAttribute("cid", cid);
        //定义一个用于记录历史商品信息的集合
        List<Product> historyProduct = new ArrayList<Product>();
        //获得客户端携带的pids的cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("pids")) {
                    String pids = cookie.getValue();
                    String[] split = pids.split("-");
                    for (String p : split) {
                        Product product = productService.findProductByPid(p);
                        historyProduct.add(product);
                    }
                }
            }
        }
        //将历史记录集合放入到域中
        request.setAttribute("historyProduct", historyProduct);
        //请求转发
        request.getRequestDispatcher("/product_list.jsp").forward(request, response);
    }

    public void addProductToCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //从session域对象中获得购物车
        HttpSession session = request.getSession();
        ProductService service = new ProductService();
        //获得放入购物车的商品的pid
        String pid = request.getParameter("pid");
        //获得该商品的数量
        int buyNum = Integer.parseInt(request.getParameter("buyNum"));
        //获得购买的product对象
        Product product = service.findProductByPid(pid);
        //计算小计
        double subtotal = product.getShop_price() * buyNum;
        //封装购物车对象
        CartItem cartItem = new CartItem();
        cartItem.setBuyNum(buyNum);
        cartItem.setProduct(product);
        cartItem.setSubtotal(subtotal);
        Cart cart = (Cart) session.getAttribute("cart");
        //判断购物车是否存在
        if (cart == null) {
            cart = new Cart();
        }
        Map<String, CartItem> cartItems = cart.getCartItems();
        //存储购物项
        //新买的商品的小计
        double newsubTotal = buyNum * product.getShop_price();
        //判断购物车中是否已经包含此商品
        if (cartItems.containsKey(product.getPid())) {
            //购物车中已经有该商品，将现在买的数量与原有的数量进行相加操作
            CartItem item = cartItems.get(pid);
            int oldBuyNum = item.getBuyNum();
            //相加数量
            oldBuyNum += buyNum;
            item.setBuyNum(oldBuyNum);
            //重新计算小计
//            item.setShbtotal(oldBuyNum*product.getShop_price());
            //原来的商品小计
            double oldSubTotal = item.getSubtotal();
            //现在的商品item购买的小计
            item.setSubtotal(oldSubTotal + newsubTotal);
        } else {
            cartItems.put(product.getPid(), cartItem);
        }
        //计算商品总计
        double total = cart.getTotal() + newsubTotal;
        cart.setTotal(total);
        //数据存储到session域对象中
        session.setAttribute("cart", cart);
        //跳转到购物页面
//        request.getRequestDispatcher("/cart.jsp").forward(request, response);
        /**
         * 防止刷新 重复调用方法 采用重定向
         */
        response.sendRedirect(request.getContextPath() + "/cart.jsp");
    }

    /**
     * 从购物车中删除某一个商品
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void delProFromCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pid = request.getParameter("pid");
        //删除session购物车中的购物项集合中的item
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart != null) {
            Map<String, CartItem> cartItems = cart.getCartItems();
            CartItem removeItem = cartItems.remove(pid);
            //重新计算总价
            cart.setTotal(cart.getTotal() - removeItem.getSubtotal());
        }
        session.setAttribute("cart", cart);
        //跳转回购物车页面
        response.sendRedirect(request.getContextPath() + "/cart.jsp");
    }

    /**
     * 清空购物车
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void clearCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("cart");
        //跳转到购物车页面
        response.sendRedirect(request.getContextPath() + "/cart.jsp");
    }
}