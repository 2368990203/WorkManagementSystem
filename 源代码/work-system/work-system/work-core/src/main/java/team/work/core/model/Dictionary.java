package team.work.core.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import team.work.utils.kit.IdKit;
import java.io.Serializable;


@SuppressWarnings("serial")
@TableName(value = "dictionary")
public class Dictionary extends Model<Dictionary> {
  private String id = IdKit.getId(this.getClass());
  private String name;
  private String fieldName;
  private Integer rank;
  private String value;
  private String explain;
  private Integer status;
  private String code;
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
  public String getFieldName() {
      return fieldName;
  }
  public void setFieldName(String fieldName) {
      this.fieldName = fieldName;
  }
  public Integer getRank() {
      return rank;
  }
  public void setRank(Integer rank) {
      this.rank = rank;
  }
  public String getValue() {
      return value;
  }
  public void setValue(String value) {
      this.value = value;
  }
  public String getExplain() {
      return explain;
  }
  public void setExplain(String explain) {
      this.explain = explain;
  }
  public Integer getStatus() {
      return status;
  }
  public void setStatus(Integer status) {
      this.status = status;
  }
  public String getCode() {
      return code;
  }
  public void setCode(String code) {
      this.code = code;
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
