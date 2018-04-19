package cn.probuing.domain;

/**
 * @Auther: wxblack-mac
 * @Date: 2018/4/19 18:08
 * @Description: 分类实体类
 */
public class Category {
    private int cid;
    private String cname;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }
}
