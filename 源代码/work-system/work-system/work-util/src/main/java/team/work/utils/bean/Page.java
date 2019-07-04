package team.work.utils.bean;


public class Page {

    private Integer pageSize;
    private Object data;
    private Integer totalCount;
    private Integer totalPage;
    private Integer currentPage;

    public Page(){
        super();
    }

    public Page(Object data,int... p){
        this.data = data;
        this.pageSize = p[0];
        this.totalCount = p[1];
        this.totalPage = p[2];
        this.currentPage = p[3];
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }
}
