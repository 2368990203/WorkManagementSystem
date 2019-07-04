package team.work.core.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import team.work.utils.kit.IdKit;
import java.io.Serializable;


@SuppressWarnings("serial")
@Data
@TableName(value = "sys_user")
public class SysUser extends Model<SysUser> {
  private String id = IdKit.getId(this.getClass());
  private String loginName;
  private String password;
  private Integer salt;
  private Integer status;
  private Integer type;
//  private String number;
//  private String phone;
//  private String verifyCode;
//  private Integer errorCount;
//  private Integer unlockTime;
  private String loginIp;
  private Integer loginTime;
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

  public String getLoginName() {
    return loginName;
  }

  public void setLoginName(String loginName) {
    this.loginName = loginName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Integer getSalt() {
    return salt;
  }

  public void setSalt(Integer salt) {
    this.salt = salt;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public String getLoginIp() {
    return loginIp;
  }

  public void setLoginIp(String loginIp) {
    this.loginIp = loginIp;
  }

  public Integer getLoginTime() {
    return loginTime;
  }

  public void setLoginTime(Integer loginTime) {
    this.loginTime = loginTime;
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
