package team.work.core.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import team.work.utils.kit.IdKit;

import java.io.Serializable;


@SuppressWarnings("serial")
@TableName(value = "sys_user_role")
public class SysUserRole extends Model<SysUserRole> {
    private String id = IdKit.getId(this.getClass());
    private String userId;
    private String systemId;
    private String roleId;
    private Integer status;
    private String creator;
    private Integer createTime;
    private String reviser;
    private Integer reviseTime;
    private Integer isDel;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getSystemId() {
        return systemId;
    }
    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }
    public String getRoleId() {
        return roleId;
    }
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
    public String getCreator() {
        return creator;
    }
    public void setCreator(String creator) {
        this.creator = creator;
    }
    public Integer getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }
    public String getReviser() {
        return reviser;
    }
    public void setReviser(String reviser) {
        this.reviser = reviser;
    }
    public Integer getReviseTime() {
        return reviseTime;
    }
    public void setReviseTime(Integer reviseTime) {
        this.reviseTime = reviseTime;
    }
    public Integer getIsDel() {
        return isDel;
    }
    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    protected Serializable pkVal() {
        return id;
    }
}
