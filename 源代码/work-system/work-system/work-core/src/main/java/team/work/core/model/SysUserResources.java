package team.work.core.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import team.work.utils.kit.IdKit;

import java.io.Serializable;


@SuppressWarnings("serial")
@TableName(value = "sys_user_resources")
public class SysUserResources extends Model<SysUserResources> {
  private String id = IdKit.getId(this.getClass());
  private String systemId;
  private String userId;
  private String parentId;
  private Integer isFile;
  private String folder;
  private Integer maxFolder;
  private String path;
  private String fullPath;
  private String name;
  private Integer size;
  private String type;
  private Integer maxSize;
  private Integer maxFileSize;
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
  public String getSystemId() {
      return systemId;
  }
  public void setSystemId(String systemId) {
      this.systemId = systemId;
  }
  public String getUserId() {
      return userId;
  }
  public void setUserId(String userId) {
      this.userId = userId;
  }
  public String getParentId() {
      return parentId;
  }
  public void setParentId(String parentId) {
      this.parentId = parentId;
  }
  public Integer getIsFile() {
      return isFile;
  }
  public void setIsFile(Integer isFile) {
      this.isFile = isFile;
  }
  public String getFolder() {
      return folder;
  }
  public void setFolder(String folder) {
      this.folder = folder;
  }
  public Integer getMaxFolder() {
      return maxFolder;
  }
  public void setMaxFolder(Integer maxFolder) {
      this.maxFolder = maxFolder;
  }
  public String getPath() {
      return path;
  }
  public void setPath(String path) {
      this.path = path;
  }
  public String getFullPath() {
      return fullPath;
  }
  public void setFullPath(String fullPath) {
      this.fullPath = fullPath;
  }
  public String getName() {
      return name;
  }
  public void setName(String name) {
      this.name = name;
  }
  public Integer getSize() {
      return size;
  }
  public void setSize(Integer size) {
      this.size = size;
  }
  public String getType() {
      return type;
  }
  public void setType(String type) {
      this.type = type;
  }
  public Integer getMaxSize() {
      return maxSize;
  }
  public void setMaxSize(Integer maxSize) {
      this.maxSize = maxSize;
  }
  public Integer getMaxFileSize() {
      return maxFileSize;
  }
  public void setMaxFileSize(Integer maxFileSize) {
      this.maxFileSize = maxFileSize;
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
  @Override
  protected Serializable pkVal() {
      return id;
  }
}
