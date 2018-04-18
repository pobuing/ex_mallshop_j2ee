package cn.probuing.dao;

import cn.probuing.domain.User;
import cn.probuing.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;

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
}
