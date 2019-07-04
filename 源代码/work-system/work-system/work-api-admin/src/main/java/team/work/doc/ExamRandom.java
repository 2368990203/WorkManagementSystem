package team.work.doc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
@ApiModel(value = "随机组卷")
@Data
public class ExamRandom {
    @ApiModelProperty(value = "作业id")
    private String examId;
    @ApiModelProperty(value = "判断题题数")
    private Integer trueOrFalseNumber;
    @ApiModelProperty(value = "单选题题数")
    private Integer singleNumber;
    @ApiModelProperty(value = "多选题题数")
    private Integer multipleNumber;
    @ApiModelProperty(value = "填空题题数")
    private Integer gapNumber;
    @ApiModelProperty(value = "客观题题数")
    private Integer subjectiveNumber;

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public Integer getTrueOrFalseNumber() {
        return trueOrFalseNumber;
    }

    public void setTrueOrFalseNumber(Integer trueOrFalseNumber) {
        this.trueOrFalseNumber = trueOrFalseNumber;
    }

    public Integer getSingleNumber() {
        return singleNumber;
    }

    public void setSingleNumber(Integer singleNumber) {
        this.singleNumber = singleNumber;
    }

    public Integer getMultipleNumber() {
        return multipleNumber;
    }

    public void setMultipleNumber(Integer multipleNumber) {
        this.multipleNumber = multipleNumber;
    }

    public Integer getGapNumber() {
        return gapNumber;
    }

    public void setGapNumber(Integer gapNumber) {
        this.gapNumber = gapNumber;
    }

}
