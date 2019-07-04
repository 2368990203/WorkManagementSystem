package team.work.core.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import team.work.utils.kit.IdKit;
import java.io.Serializable;


@SuppressWarnings("serial")
@TableName(value = "question")
@Data
public class Question extends Model<Question> {
  private String id = IdKit.getId(this.getClass());
  private String questionName;
  private Integer type;
  private String answerKeys;
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

  public String getQuestionName() {
    return questionName;
  }

  public void setQuestionName(String questionName) {
    this.questionName = questionName;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public String getAnswerKeys() {
    return answerKeys;
  }

  public void setAnswerKeys(String answerKeys) {
    this.answerKeys = answerKeys;
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
