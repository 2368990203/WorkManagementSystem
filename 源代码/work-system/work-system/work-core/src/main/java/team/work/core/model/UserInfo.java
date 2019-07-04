package team.work.core.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import team.work.utils.kit.IdKit;
import java.io.Serializable;


@SuppressWarnings("serial")
@TableName(value = "user_info")
public class UserInfo extends Model<UserInfo> {
  private String id = IdKit.getId(this.getClass());
  private String sno;
  private Integer isStudy;
  private String proid;
  private String classname;
  private String job;
  private String creator;
  private Integer createTime;
  private String reviser;
  private Integer reviseTime;
  private Integer isDel;

  public String getId() {
      return id;
  }
  public void setId(String id) {
      this.id = id;
  }
  public String getSno() {
      return sno;
  }
  public void setSno(String sno) {
      this.sno = sno;
  }
  public Integer getIsStudy() {
      return isStudy;
  }
  public void setIsStudy(Integer isStudy) {
      this.isStudy = isStudy;
  }
  public String getProid() {
      return proid;
  }
  public void setProid(String proid) {
      this.proid = proid;
  }
  public String getClassname() {
      return classname;
  }
  public void setClassname(String classname) {
      this.classname = classname;
  }
  public String getJob() {
      return job;
  }
  public void setJob(String job) {
      this.job = job;
  }
  public String getCreator() {
      return creator;
  }
  public void setCreator(String creator) {
      this.creator = creator;
  }
  public Integer getCreateTime() {
      return createTime;
  }
  public void setCreateTime(Integer createTime) {
      this.createTime = createTime;
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
  public Integer getIsDel() {
      return isDel;
  }
  public void setIsDel(Integer isDel) {
      this.isDel = isDel;
  }
  @Override
  protected Serializable pkVal() {
      return id;
  }
}
