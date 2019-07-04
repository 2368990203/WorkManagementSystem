package team.work.core.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import team.work.utils.kit.IdKit;
import java.io.Serializable;


@SuppressWarnings("serial")
@TableName(value = "academy_info")
public class AcademyInfo extends Model<AcademyInfo> {
  private String id = IdKit.getId(this.getClass());
  private String code;
  private String name;
  private Integer status;
  private Integer campus;
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
  public String getCode() {
      return code;
  }
  public void setCode(String code) {
      this.code = code;
  }
  public String getName() {
      return name;
  }
  public void setName(String name) {
      this.name = name;
  }
  public Integer getStatus() {
      return status;
  }
  public void setStatus(Integer status) {
      this.status = status;
  }
  public Integer getCampus() {
      return campus;
  }
  public void setCampus(Integer campus) {
      this.campus = campus;
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
