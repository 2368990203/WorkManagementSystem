package team.work.doc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
@ApiModel(value = "考试答题记录新增")
@Data
public class ExamRecordCreate {
    @ApiModelProperty(value = "examId")
    private String examId;
    @ApiModelProperty(value = "questionId")
    private String questionId;
    @ApiModelProperty(value = "学号/工号")
    private String number;
    @ApiModelProperty(value = "答对情况")
    private Integer situation;
    @ApiModelProperty(value = "该题得分")
    private Double scores;
    @ApiModelProperty(value = "选择项/填空的内容")
    private String answerContent;

}
