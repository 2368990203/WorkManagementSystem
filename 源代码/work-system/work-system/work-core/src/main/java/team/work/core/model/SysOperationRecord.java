package team.work.core.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import team.work.utils.kit.IdKit;
import java.io.Serializable;


@SuppressWarnings("serial")
@TableName(value = "sys_operation_record")
public class SysOperationRecord extends Model<SysOperationRecord> {
  private String id = IdKit.getId(this.getClass());
  private Integer server;
  private String control;
  private String function;
  private String ipAddr;
  private Integer status;
  private String parameter;
  private String creator;
  private Integer createTime;

  public String getId() {
      return id;
  }
  public void setId(String id) {
      this.id = id;
  }
  public Integer getServer() {
      return server;
  }
  public void setServer(Integer server) {
      this.server = server;
  }
  public String getControl() {
      return control;
  }
  public void setControl(String control) {
      this.control = control;
  }
  public String getFunction() {
      return function;
  }
  public void setFunction(String function) {
      this.function = function;
  }
  public String getIpAddr() {
      return ipAddr;
  }
  public void setIpAddr(String ipAddr) {
      this.ipAddr = ipAddr;
  }
  public Integer getStatus() {
      return status;
  }
  public void setStatus(Integer status) {
      this.status = status;
  }
  public String getParameter() {
      return parameter;
  }
  public void setParameter(String parameter) {
      this.parameter = parameter;
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
  @Override
  protected Serializable pkVal() {
      return id;
  }
}
