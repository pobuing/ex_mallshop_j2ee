package cn.probuing.domain;

import java.util.List;

/**
 * @Auther: wxblack-mac
 * @Date: 2018/4/20 11:18
 * @Description: 分页封装数据
 */
public class PageBean<T> {
    // 当前页
    private int currentPage;
    // 当前页显示条数
    private int currentCount;
    // 总条数
    private int totalCount;
    // 总页数
    private int totalPage;
    //当前页显示的数据实体
    private List<T> list;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(int currentCount) {
        this.currentCount = currentCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
