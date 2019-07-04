package team.work.core.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import team.work.utils.kit.IdKit;

import java.io.Serializable;

@SuppressWarnings("serial")
@TableName(value = "classes")
public class Classes extends Model<Classes> {
    private String classNo;
    private Integer createTime;
    private String creator;
    private String grade;
    private String id = IdKit.getId(this.getClass());
    private Integer isDel;
    private String majorCode;
    private String reviser;
    private Integer reviseTime;

    public String getClassNo() {
        return classNo;
    }

    public void setClassNo(String classNo) {
        this.classNo = classNo;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getMajorCode() {
        return majorCode;
    }

    public void setMajorCode(String majorCode) {
        this.majorCode = majorCode;
    }

    public String getReviser() {
        return reviser;
    }

    public void setReviser(String reviser) {
        this.reviser = reviser;
    }

    public Integer getReviseTime() {
        return reviseTime;
    }

    public void setReviseTime(Integer reviseTime) {
        this.reviseTime = reviseTime;
    }

    @Override
    protected Serializable pkVal() {
        return id;
    }
}
