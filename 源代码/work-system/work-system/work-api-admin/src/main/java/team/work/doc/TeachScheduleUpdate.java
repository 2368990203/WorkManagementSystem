package team.work.doc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
@ApiModel(value ="教师授课修改")
public class TeachScheduleUpdate {
    @ApiModelProperty(value = "")
    private String id;
    @ApiModelProperty(value = "课程号")
    private String courseNumber;
    @ApiModelProperty(value = "授课班级id")
    private String classId;
    @ApiModelProperty(value = "教师工号")
    private String teacherNumber;

    public String getId() { 
        return id;
    } 

    public void setId(String id) { 
        this.id = id;
    } 

    public String getCourseNumber() { 
        return courseNumber;
    } 

    public void setCourseNumber(String courseNumber) { 
        this.courseNumber = courseNumber;
    } 

    public String getClassId() { 
        return classId;
    } 

    public void setClassId(String classId) { 
        this.classId = classId;
    } 

    public String getTeacherNumber() { 
        return teacherNumber;
    } 

    public void setTeacherNumber(String teacherNumber) { 
        this.teacherNumber = teacherNumber;
    } 

}
