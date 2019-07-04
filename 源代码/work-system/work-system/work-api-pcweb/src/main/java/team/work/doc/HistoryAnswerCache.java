package team.work.doc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
@ApiModel(value = "保存答题记录")
public class HistoryAnswerCache {

    @ApiModelProperty(value = "examId")
    private String examId;

    @ApiModelProperty(value = "判断题、单选题所选答案")
    private Map<String, String> singleChoice;//json格式：{question:optionId}

    @ApiModelProperty(value = "多选题所选答案")
    private Map<String, String[]> multiChoice;//json格式：{question:[optionIds]}

    @ApiModelProperty(value = "填空题所选答案")//json格式：{questionId:{optionId:所填内容}}
    private Map<String, Map<String, String>> gapChoice;

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public Map<String, String> getSingleChoice() {
        return singleChoice;
    }

    public void setSingleChoice(Map<String, String> singleChoice) {
        this.singleChoice = singleChoice;
    }

    public Map<String, String[]> getMultiChoice() {
        return multiChoice;
    }

    public void setMultiChoice(Map<String, String[]> multiChoice) {
        this.multiChoice = multiChoice;
    }

    public Map<String, Map<String, String>> getGapChoice() {
        return gapChoice;
    }

    public void setGapChoice(Map<String, Map<String, String>> gapChoice) {
        this.gapChoice = gapChoice;
    }
}
