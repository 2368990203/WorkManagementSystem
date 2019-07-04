package team.work.doc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
@ApiModel(value = "作业新增")
@Data
public class ExamCreate {
    @ApiModelProperty(value = "作业名称")
    private String name;
    @ApiModelProperty(value = "课程号")
    private String courseNumber;
    @ApiModelProperty(value = "发布人工号")
    private String teacherNumber;
    @ApiModelProperty(value = "考试类型")
    private Integer type;
    @ApiModelProperty(value = "考试方式")
    private Integer way;
    @ApiModelProperty(value = "作业总分值")
    private String score;
    @ApiModelProperty(value = "判断题总分值")
    private String trueOrFalse;
    @ApiModelProperty(value = "单选题总分值")
    private String single;
    @ApiModelProperty(value = "多选题总分值")
    private String multiple;
    @ApiModelProperty(value = "填空题总分值")
    private Integer startTime;
    @ApiModelProperty(value = "客观题总分值")
    private Integer subjective;
    @ApiModelProperty(value = "开始时间")
    private Integer endTime;
    @ApiModelProperty(value = "结束时间")
    private String gap;
    @ApiModelProperty(value = "考试时长")
    private Integer duration;
    @ApiModelProperty(value = "布置的班级id")
    private String classId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getTeacherNumber() {
        return teacherNumber;
    }

    public void setTeacherNumber(String teacherNumber) {
        this.teacherNumber = teacherNumber;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getWay() {
        return way;
    }

    public void setWay(Integer way) {
        this.way = way;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getTrueOrFalse() {
        return trueOrFalse;
    }

    public void setTrueOrFalse(String trueOrFalse) {
        this.trueOrFalse = trueOrFalse;
    }

    public String getSingle() {
        return single;
    }

    public void setSingle(String single) {
        this.single = single;
    }

    public String getMultiple() {
        return multiple;
    }

    public void setMultiple(String multiple) {
        this.multiple = multiple;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getSubjective() {
        return subjective;
    }

    public void setSubjective(Integer subjective) {
        this.subjective = subjective;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public String getGap() {
        return gap;
    }

    public void setGap(String gap) {
        this.gap = gap;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }
}
