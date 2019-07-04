package team.work.core.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import team.work.utils.kit.IdKit;
import java.io.Serializable;


@SuppressWarnings("serial")
@TableName(value = "options")
public class Options extends Model<Options> {
  private String id = IdKit.getId(this.getClass());
  private String questionId;
  private String optionInfo;
  private Integer optionNumber;
  private Integer isAnswer;
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
  public String getQuestionId() {
      return questionId;
  }
  public void setQuestionId(String questionId) {
      this.questionId = questionId;
  }
  public String getOptionInfo() {
      return optionInfo;
  }
  public void setOptionInfo(String optionInfo) {
      this.optionInfo = optionInfo;
  }
  public Integer getOptionNumber() {
      return optionNumber;
  }
  public void setOptionNumber(Integer optionNumber) {
      this.optionNumber = optionNumber;
  }
  public Integer getIsAnswer() {
      return isAnswer;
  }
  public void setIsAnswer(Integer isAnswer) {
      this.isAnswer = isAnswer;
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
