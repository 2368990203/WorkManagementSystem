package team.work.core.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import team.work.utils.kit.IdKit;

import java.io.Serializable;


@SuppressWarnings("serial")
@TableName(value = "sys_menu")
public class SysMenu extends Model<SysMenu> {
  private String id = IdKit.getId(this.getClass());
  private String name;
  private String url;
  private String code;
  private String icon;
  private String parentId;
  private String systemId;
  private String type;
  private Integer status;
  private Integer sort;
  private Integer hide;
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
  public String getUrl() {
      return url;
  }
  public void setUrl(String url) {
      this.url = url;
  }
  public String getCode() {
      return code;
  }
  public void setCode(String code) {
      this.code = code;
  }
  public String getIcon() {
      return icon;
  }
  public void setIcon(String icon) {
      this.icon = icon;
  }
  public String getParentId() {
      return parentId;
  }
  public void setParentId(String parentId) {
      this.parentId = parentId;
  }
  public String getSystemId() {
      return systemId;
  }
  public void setSystemId(String systemId) {
      this.systemId = systemId;
  }
  public String getType() {
        return type;
    }
  public void setType(String type) {
        this.type = type;
    }
  public Integer getStatus() {
      return status;
  }
  public void setStatus(Integer status) {
      this.status = status;
  }
  public Integer getSort() {
      return sort;
  }
  public void setSort(Integer sort) {
      this.sort = sort;
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
  public Integer getHide() {
        return hide;
    }
  public void setHide(Integer hide) {
        this.hide = hide;
    }
  @Override
  protected Serializable pkVal() {
      return id;
  }
}
