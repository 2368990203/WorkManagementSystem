package team.work.doc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
@ApiModel(value ="教师信息修改")
public class TeacherUpdate {
    @ApiModelProperty(value = "")
    private String id;
    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "工号")
    private String number;
    @ApiModelProperty(value = "所属学院id")
    private String academyId;
    @ApiModelProperty(value = "性别")
    private Integer sex;

    public String getId() { 
        return id;
    } 

    public void setId(String id) { 
        this.id = id;
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

    public String getAcademyId() { 
        return academyId;
    } 

    public void setAcademyId(String academyId) { 
        this.academyId = academyId;
    } 

    public Integer getSex() { 
        return sex;
    } 

    public void setSex(Integer sex) { 
        this.sex = sex;
    } 

}
