package team.work.core.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import team.work.utils.kit.IdKit;
import java.io.Serializable;


@SuppressWarnings("serial")
@TableName(value = "log_read")
public class LogRead extends Model<LogRead> {
  private String id = IdKit.getId(this.getClass());
  private String objectId;
  private Integer objectType;
  private String readerUserId;
  private Integer readTime;

  public String getId() {
      return id;
  }
  public void setId(String id) {
      this.id = id;
  }
  public String getObjectId() {
      return objectId;
  }
  public void setObjectId(String objectId) {
      this.objectId = objectId;
  }
  public Integer getObjectType() {
      return objectType;
  }
  public void setObjectType(Integer objectType) {
      this.objectType = objectType;
  }
  public String getReaderUserId() {
      return readerUserId;
  }
  public void setReaderUserId(String readerUserId) {
      this.readerUserId = readerUserId;
  }
  public Integer getReadTime() {
      return readTime;
  }
  public void setReadTime(Integer readTime) {
      this.readTime = readTime;
  }
  @Override
  protected Serializable pkVal() {
      return id;
  }
}
