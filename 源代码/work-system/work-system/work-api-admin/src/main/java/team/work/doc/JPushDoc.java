package team.work.doc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author Taller
 * @create 2017-12-05 14:53
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
@ApiModel(value = "推送")
public class JPushDoc {
    @ApiModelProperty(value = "推送到的用户列表")
    private List<String> pushUsers;
    @ApiModelProperty(value = "推送标题")
    private String title;
    @ApiModelProperty(value = "推送内容")
    private String pushContent;
    @ApiModelProperty(value = "推送类型 ")
    private int pushType = 3; //3
    @ApiModelProperty(value = "推送数据")
    private String pushData;

    public List<String> getPushUsers() {
        return pushUsers;
    }

    public void setPushUsers(List<String> pushUsers) {
        this.pushUsers = pushUsers;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPushContent() {
        return pushContent;
    }

    public void setPushContent(String pushContent) {
        this.pushContent = pushContent;
    }

    public int getPushType() {
        return pushType;
    }

    public void setPushType(int pushType) {
        this.pushType = pushType;
    }

    public String getPushData() {
        return pushData;
    }

    public void setPushData(String pushData) {
        this.pushData = pushData;
    }
}
