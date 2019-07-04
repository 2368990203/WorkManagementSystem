package team.work.core.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import team.work.utils.kit.IdKit;
import java.io.Serializable;

@SuppressWarnings("serial")
@TableName(value = "course")
public class Course extends Model<Course> {
  private Integer createTime;
  private String creator;
  private String credit;
  private String id = IdKit.getId(this.getClass());
  private Integer isDel;
  private String name;
  private String number;
  private String reviser;
  private Integer reviseTime;

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
  public String getCredit() { 
      return credit;
  } 
  public void setCredit(String credit) { 
      this.credit = credit;
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
  public String getName() { 
      return name;
  } 
  public void setName(String name) { 
      this.name = name;
  } 
  public String getNumber() { 
      return number;
  } 
  public void setNumber(String number) { 
      this.number = number;
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
