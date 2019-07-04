package team.work.doc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
@ApiModel(value = "后端登记用户")
public class SysUserReg {
    @ApiModelProperty(value = "登录名")
    private String loginName;
    @ApiModelProperty(value = "登录密码")
    private String password;
    @ApiModelProperty(value = "学号/工号")
    private String number;
    @ApiModelProperty(value = "用户属性")
    private Attr attr;
    @ApiModelProperty(value = "权限id")
    private String roleId;
    @ApiModelProperty(value = "用户类型")
    private Integer type;

    public class Attr {
        @ApiModelProperty(value = "真实姓名")
        private String trueName;
        @ApiModelProperty(value = "手机")
        private String mobile;
        @ApiModelProperty(value = "身份证")
        private String idCard;
        @ApiModelProperty(value = "学院代码")
        private String academyCode;
        @ApiModelProperty(value = "党支部代码")
        private String departCode;

        public String getTrueName() {
            return trueName;
        }

        public void setTrueName(String trueName) {
            this.trueName = trueName;
        }


        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }


        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public String getAcademyCode() {
            return academyCode;
        }

        public void setAcademyCode(String academyCode) {
            this.academyCode = academyCode;
        }

        public String getDepartCode() {
            return departCode;
        }

        public void setDepartCode(String departCode) {
            this.departCode = departCode;
        }

    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Attr getAttr() {
        return attr;
    }

    public void setAttr(Attr attr) {
        this.attr = attr;
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
}
