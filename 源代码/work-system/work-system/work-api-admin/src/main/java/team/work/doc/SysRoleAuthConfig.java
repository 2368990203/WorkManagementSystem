package team.work.doc;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
@ApiModel(value = "权限菜单")
public class SysRoleAuthConfig {
    @ApiModelProperty(value = "角色Id")
    private String roleId;
    @ApiModelProperty(value = "菜单对象")
    private List<JSONObject> menu;
    @ApiModelProperty(value = "权限对象")
    private List<JSONObject> btns;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public List<JSONObject> getMenu() {
        return menu;
    }

    public void setMenu(List<JSONObject> menu) {
        this.menu = menu;
    }

    public List<JSONObject> getBtns() {
        return btns;
    }

    public void setBtns(List<JSONObject> btns) {
        this.btns = btns;
    }
}
