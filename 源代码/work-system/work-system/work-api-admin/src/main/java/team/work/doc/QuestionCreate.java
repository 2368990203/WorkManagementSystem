package team.work.doc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
@ApiModel(value = "题库新增")
public class QuestionCreate {
    @ApiModelProperty(value = "题目名称")
    private String questionName;
    @ApiModelProperty(value = "类型")
    private Integer type;
    @ApiModelProperty(value = "答案解析")
    private String answerKeys;

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

}
