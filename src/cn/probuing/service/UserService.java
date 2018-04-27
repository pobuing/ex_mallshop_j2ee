package cn.probuing.service;

import cn.probuing.dao.UserDao;
import cn.probuing.domain.User;

import java.sql.SQLException;

/**
 * @Auther: wxblack-mac
 * @Date: 2018/4/27 14:53
 * @Description:
 */
public class UserService {
    /**
     * 用户登录方法
     * @param username
     * @param password
     * @return
     */
    public User login(String username, String password) throws SQLException {
        UserDao dao = new UserDao();
        return dao.login(username,password);
    }
}
