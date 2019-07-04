package team.work.core.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.models.auth.In;
import lombok.Data;
import team.work.utils.kit.IdKit;
import java.io.Serializable;


@SuppressWarnings("serial")
@TableName(value = "exam_candidate")
@Data
public class ExamCandidate extends Model<ExamCandidate> {
  private String id = IdKit.getId(this.getClass());
  private String number;
  private String examId;
  private Double score;
  private Integer status;
  private Integer markStatus;
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

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }

  public String getExamId() {
    return examId;
  }

  public void setExamId(String examId) {
    this.examId = examId;
  }

  public Double getScore() {
    return score;
  }

  public void setScore(Double score) {
    this.score = score;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Integer getMarkStatus() {
    return markStatus;
  }

  public void setMarkStatus(Integer markStatus) {
    this.markStatus = markStatus;
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
