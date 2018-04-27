package cn.probuing.dao;

import cn.probuing.domain.User;
import cn.probuing.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;

/**
 * @Auther: wxblack-mac
 * @Date: 2018/4/18 16:36
 * @Description: user数据库操作类
 */
public class UserDao {
    /**
     * @param user 要操作的数据源
     * @return 影响的行数
     */
    public int regist(User user) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?)";
        int update = runner.update(sql,
                user.getUid(),
                user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getEmail(),
                user.getTelephone(),
                user.getBirthday(),
                user.getSex(),
                user.getState(),
                user.getCode());
        return update;
    }

    /**
     * 数据库激活方法
     *
     * @param activeCode
     */
    public void active(String activeCode) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "update user set state=? where code=?";
        runner.update(sql, 1, activeCode);

    }

    /**
     * 数据库查询用户名是否存在
     *
     * @param username 要查询的用户名
     * @return 查到的数目
     * @throws SQLException
     */
    public Long checkUserName(String username) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select count(*) from user where username=?";
        return (Long) runner.query(sql, new ScalarHandler(), username);

    }

    /**
     * 查询数据库，用户登录
     *
     * @param username
     * @param password
     * @return
     */
    public User login(String username, String password) throws SQLException {
        QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "select * from user where username=? and password=?";
        User user = runner.query(sql, new BeanHandler<User>(User.class), username, password);
        return user;
    }
}
