package team.work.doc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
@ApiModel(value = "随机组卷")
public class ExamCustom {
    @ApiModelProperty(value = "作业id")
    private String examId;
    @ApiModelProperty(value = "判断题Id")
    private ArrayList<String> trueFalseIds;
    @ApiModelProperty(value = "单选题Id")
    private ArrayList<String> singleIds;
    @ApiModelProperty(value = "多选题Id")
    private ArrayList<String> multipleIds;
    @ApiModelProperty(value = "填空题Id")
    private ArrayList<String> gapIds;
    @ApiModelProperty(value = "客观题Id")
    private ArrayList<String> subjectiveIds;

    @ApiModelProperty(value = "判断题分值")
    private ArrayList<String> trueFalseScores;
    @ApiModelProperty(value = "单选题分值")
    private ArrayList<String> singleScores;
    @ApiModelProperty(value = "多选题分值")
    private ArrayList<String> multipleScores;
    @ApiModelProperty(value = "填空题分值")
    private ArrayList<String> gapScores;
    @ApiModelProperty(value = "客观题分值")
    private ArrayList<String> subjectiveScores;

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public ArrayList<String> getTrueFalseIds() {
        return trueFalseIds;
    }

    public void setTrueFalseIds(ArrayList<String> trueFalseIds) {
        this.trueFalseIds = trueFalseIds;
    }

    public ArrayList<String> getSingleIds() {
        return singleIds;
    }

    public void setSingleIds(ArrayList<String> singleIds) {
        this.singleIds = singleIds;
    }

    public ArrayList<String> getMultipleIds() {
        return multipleIds;
    }

    public void setMultipleIds(ArrayList<String> multipleIds) {
        this.multipleIds = multipleIds;
    }

    public ArrayList<String> getGapIds() {
        return gapIds;
    }

    public void setGapIds(ArrayList<String> gapIds) {
        this.gapIds = gapIds;
    }

    public ArrayList<String> getSubjectiveIds() {
        return subjectiveIds;
    }

    public void setSubjectiveIds(ArrayList<String> subjectiveIds) {
        this.subjectiveIds = subjectiveIds;
    }

    public ArrayList<String> getTrueFalseScores() {
        return trueFalseScores;
    }

    public void setTrueFalseScores(ArrayList<String> trueFalseScores) {
        this.trueFalseScores = trueFalseScores;
    }

    public ArrayList<String> getSingleScores() {
        return singleScores;
    }

    public void setSingleScores(ArrayList<String> singleScores) {
        this.singleScores = singleScores;
    }

    public ArrayList<String> getMultipleScores() {
        return multipleScores;
    }

    public void setMultipleScores(ArrayList<String> multipleScores) {
        this.multipleScores = multipleScores;
    }

    public ArrayList<String> getGapScores() {
        return gapScores;
    }

    public void setGapScores(ArrayList<String> gapScores) {
        this.gapScores = gapScores;
    }

    public ArrayList<String> getSubjectiveScores() {
        return subjectiveScores;
    }

    public void setSubjectiveScores(ArrayList<String> subjectiveScores) {
        this.subjectiveScores = subjectiveScores;
    }
}
