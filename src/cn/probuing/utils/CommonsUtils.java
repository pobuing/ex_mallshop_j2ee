package cn.probuing.utils;

import java.util.UUID;

/**
 * @Auther: wxblack-mac
 * @Date: 2018/4/18 16:31
 * @Description:通用工具
 */
public class CommonsUtils {
    /**
     * 获取uuid
     *
     * @return uuid
     */
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }
}
