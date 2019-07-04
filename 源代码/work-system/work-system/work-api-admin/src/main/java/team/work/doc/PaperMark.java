package team.work.doc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
@ApiModel(value = "批改客观题信息")
@Data
public class PaperMark {

    @ApiModelProperty(value = "examId")
    private String examId;

    @ApiModelProperty(value = "number")
    private String number;

    @ApiModelProperty(value = "type")
    private Integer type;//1-暂存,2-确认批改

    @ApiModelProperty(value = "客观题批改内容")
    private Map<String, Map<String, String>> subjectiveMark;

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Map<String, Map<String, String>> getSubjectiveMark() {
        return subjectiveMark;
    }

    public void setSubjectiveMark(Map<String, Map<String, String>> subjectiveMark) {
        this.subjectiveMark = subjectiveMark;
    }
}
