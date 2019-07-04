package team.work.doc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
@ApiModel(value = "修改作业信息")
@Data
public class PaperMark {

    @ApiModelProperty(value = "examId")
    private String examId;

    @ApiModelProperty(value = "判断题所选答案")
    private Map<Integer, Map<String, String>> trueFalseAnswer;//Integer：题号 ； Map<问题id，选择的选项id>

    @ApiModelProperty(value = "单选题所选答案")
    private Map<Integer, Map<String, String>> singleAnswer;

    @ApiModelProperty(value = "多选题所选答案")
    private Map<Integer, Map<String, String[]>> multipleAnswer;

    @ApiModelProperty(value = "填空题所填答案")
    private Map<Integer, Map<String, String[]>> gapAnswer;

    @ApiModelProperty(value = "客观题答案")
    private Map<Integer, Map<String, String>> subjectiveAnswer;

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public Map<Integer, Map<String, String>> getTrueFalseAnswer() {
        return trueFalseAnswer;
    }

    public void setTrueFalseAnswer(Map<Integer, Map<String, String>> trueFalseAnswer) {
        this.trueFalseAnswer = trueFalseAnswer;
    }

    public Map<Integer, Map<String, String>> getSingleAnswer() {
        return singleAnswer;
    }

    public void setSingleAnswer(Map<Integer, Map<String, String>> singleAnswer) {
        this.singleAnswer = singleAnswer;
    }

    public Map<Integer, Map<String, String[]>> getMultipleAnswer() {
        return multipleAnswer;
    }

    public void setMultipleAnswer(Map<Integer, Map<String, String[]>> multipleAnswer) {
        this.multipleAnswer = multipleAnswer;
    }

    public Map<Integer, Map<String, String[]>> getGapAnswer() {
        return gapAnswer;
    }

    public void setGapAnswer(Map<Integer, Map<String, String[]>> gapAnswer) {
        this.gapAnswer = gapAnswer;
    }

    public Map<Integer, Map<String, String>> getSubjectiveAnswer() {
        return subjectiveAnswer;
    }

    public void setSubjectiveAnswer(Map<Integer, Map<String, String>> subjectiveAnswer) {
        this.subjectiveAnswer = subjectiveAnswer;
    }
}
