package team.work.core.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import team.work.utils.kit.IdKit;
import java.io.Serializable;


@SuppressWarnings("serial")
@TableName(value = "sys_role")
public class SysRole extends Model<SysRole> {
  private String id = IdKit.getId(this.getClass());
  private String systemId;
  private String name;
  private String visible;
  private Integer level;
  private Integer isDefault;
  private String remark;
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
  public String getSystemId() {
      return systemId;
  }
  public void setSystemId(String systemId) {
      this.systemId = systemId;
  }
  public String getName() {
      return name;
  }
  public void setName(String name) {
      this.name = name;
  }
  public String getVisible() {
      return visible;
  }
  public void setVisible(String visible) {
      this.visible = visible;
  }
  public Integer getLevel() {
      return level;
  }
  public void setLevel(Integer level) {
      this.level = level;
  }
  public Integer getIsDefault() {
      return isDefault;
  }
  public void setIsDefault(Integer isDefault) {
      this.isDefault = isDefault;
  }
  public String getRemark() {
      return remark;
  }
  public void setRemark(String remark) {
      this.remark = remark;
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
