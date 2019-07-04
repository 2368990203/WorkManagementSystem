package team.work.core.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import team.work.utils.kit.IdKit;

import java.io.Serializable;


@SuppressWarnings("serial")
@TableName(value = "sys_menu_auth")
public class SysMenuAuth extends Model<SysMenuAuth> {
  private String id = IdKit.getId(this.getClass());
  private String name;
  private String menuId;
  private String fun;
  private String code;
  private String icon;
  private Integer sort;
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
  public String getMenuId() {
      return menuId;
  }
  public void setMenuId(String menuId) {
      this.menuId = menuId;
  }
  public String getFun() {
      return fun;
  }
  public void setFun(String fun) {
      this.fun = fun;
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
  @Override
  protected Serializable pkVal() {
      return id;
  }
}
