package team.work.utils.bean;

/**
 * Created by htc on 2017/9/7.
 * 查询条件
 */
public class Where {
    private String attr;
    private String opt;
    public Object val;

    public Where(){
        super();
    }
    public Where(String field){
        this.attr = "";
        this.opt = "field";
        this.val = field;
    }
    public Where(String attr,Object val){
        this.attr = attr;
        this.opt = "order";
        this.val = val;
    }
    public Where(String attr,String opt,Object val){
        this.attr = attr;
        this.opt = opt;
        this.val = val;
    }


    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
    }

    public Object getVal() {
        return val;
    }

    public void setVal(Object val) {
        this.val = val;
    }
}
