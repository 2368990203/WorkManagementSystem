package team.work.doc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
@ApiModel(value ="课程信息新增")
public class CourseCreate {
    @ApiModelProperty(value = "学分")
    private String credit;
    @ApiModelProperty(value = "课程名")
    private String name;
    @ApiModelProperty(value = "课程号")
    private String number;

    public String getCredit() { 
        return credit;
    } 

    public void setCredit(String credit) { 
        this.credit = credit;
    } 

    public String getName() { 
        return name;
    } 

    public void setName(String name) { 
        this.name = name;
    } 

    public String getNumber() { 
        return number;
    } 

    public void setNumber(String number) { 
        this.number = number;
    } 

}
