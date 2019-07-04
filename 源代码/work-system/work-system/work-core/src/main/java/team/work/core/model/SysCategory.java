package team.work.core.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import team.work.utils.kit.IdKit;

import java.io.Serializable;


@SuppressWarnings("serial")
@TableName(value = "sys_category")
public class SysCategory extends Model<SysCategory> {
  private String id = IdKit.getId(this.getClass());
  private String name;
  private String icon;
  private String code;
  private String parentId;
  private String superId;
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
  public String getName() {
      return name;
  }
  public void setName(String name) {
      this.name = name;
  }
  public String getIcon() {
      return icon;
  }
  public void setIcon(String icon) {
      this.icon = icon;
  }
  public String getCode() {
      return code;
  }
  public void setCode(String code) {
      this.code = code;
  }
  public String getParentId() {
      return parentId;
  }
  public void setParentId(String parentId) {
      this.parentId = parentId;
  }
  public String getSuperId() {
      return superId;
  }
  public void setSuperId(String superId) {
      this.superId = superId;
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
