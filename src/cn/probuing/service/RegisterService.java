package cn.probuing.service;

import cn.probuing.dao.UserDao;
import cn.probuing.domain.User;

import java.sql.SQLException;

/**
 * @Auther: wxblack-mac
 * @Date: 2018/4/18 16:34
 * @Description: 注册service
 */
public class RegisterService {
    /**
     * 注册方法
     *
     * @param user 要注册的user
     * @return 是否注册成功
     */
    public boolean regist(User user) {
        UserDao registerDao = new UserDao();
        int row = 0;
        try {
            row = registerDao.regist(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return row > 0 ? true : false;
    }
}
