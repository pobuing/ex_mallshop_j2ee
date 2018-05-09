package cn.probuing.web.servlet;

import cn.probuing.domain.Category;
import cn.probuing.domain.Product;
import cn.probuing.service.AdminService;
import cn.probuing.utils.CommonsUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: wxblack-mac
 * @Date: 2018/5/4 16:56
 * @Description: 添加商品后台servlet
 */
public class AdminAddProductServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InputStream is = null;
        FileOutputStream fos = null;
        Product product = new Product();
        Map map = new HashMap<String, Object>();
        try {
            //收集表单数据，封装一个product实体
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            //解析请求获得文件项集合
            List<FileItem> fileItemList = upload.parseRequest(request);
            for (FileItem fileItem : fileItemList) {
                //判断是否是普通表单项
                boolean formField = fileItem.isFormField();
                //普通表单项
                if (formField) {
                    String fieldName = fileItem.getFieldName();
                    String fieldValue = fileItem.getString("utf-8");
                    map.put(fieldName, fieldValue);
                } else {
                    //上传文件表单项
                    String fileName = fileItem.getName();
                    //获取文件输入流
                    is = fileItem.getInputStream();
                    //指定路径
                    String uploadPath = this.getServletContext().getRealPath("upload");
                    File file = new File(uploadPath);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    fos = new FileOutputStream(file + "/" + fileName);
                    IOUtils.copy(is, fos);
                    fileItem.delete();
                    map.put("pimage", "upload/" + fileName);
                }

            }
            BeanUtils.populate(product, map);
            product.setPid(CommonsUtils.getUUID());
            product.setPdate(new Date());
            product.setPflag(0);
            Category category = new Category();
            category.setCid(map.get("cid").toString());
            product.setCategory(category);
            //product传递到service
            AdminService service = new AdminService();
            service.saveProduct(product);

        } catch (FileUploadException | IllegalAccessException | InvocationTargetException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                is.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
    }
}
