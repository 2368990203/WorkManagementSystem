package team.work.doc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
@ApiModel(value = "用户信息")
public class SysUserInfoUpdate {
    //trueName\niceName\mobile\mail\qq\weChat\idCard\birthday\header\address\
    @ApiModelProperty(value = "真实姓名")
    private String trueName;
    @ApiModelProperty(value = "昵称")
    private String niceName;
    @ApiModelProperty(value = "手机")
    private String mobile;
    @ApiModelProperty(value = "电子邮件")
    private String mail;
    @ApiModelProperty(value = "QQ")
    private String qq;
    @ApiModelProperty(value = "微信")
    private String weChat;
    @ApiModelProperty(value = "身份证")
    private String idCard;
    @ApiModelProperty(value = "生日")
    private String birthday;
    @ApiModelProperty(value = "头像")
    private String header;
    @ApiModelProperty(value = "地址")
    private String address;

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getNiceName() {
        return niceName;
    }

    public void setNiceName(String niceName) {
        this.niceName = niceName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWeChat() {
        return weChat;
    }

    public void setWeChat(String weChat) {
        this.weChat = weChat;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
