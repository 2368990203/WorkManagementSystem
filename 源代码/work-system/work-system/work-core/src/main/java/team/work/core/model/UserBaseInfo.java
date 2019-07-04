package team.work.core.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import team.work.utils.kit.IdKit;
import java.io.Serializable;


@SuppressWarnings("serial")
@TableName(value = "user_base_info")
public class UserBaseInfo extends Model<UserBaseInfo> {
  private String id = IdKit.getId(this.getClass());
  private String name;
  private String oldName;
  private String number;
  private Integer sex;
  private String nationCode;
  private Integer birthday;
  private String graduate;
  private String degree;
  private Integer nationality;
  private String originPlace;
  private String birthPlace;
  private String presentPesidence;
    private Integer type;
    private String phone;
  private String email;
  private String specialty;
  private String idCard;
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
  public String getName() {
      return name;
  }
  public void setName(String name) {
      this.name = name;
  }
  public String getOldName() {
      return oldName;
  }
  public void setOldName(String oldName) {
      this.oldName = oldName;
  }
  public String getNumber() {
      return number;
  }
  public void setNumber(String number) {
      this.number = number;
  }
  public Integer getSex() {
      return sex;
  }
  public void setSex(Integer sex) {
      this.sex = sex;
  }
  public String getNationCode() {
      return nationCode;
  }
  public void setNationCode(String nationCode) {
      this.nationCode = nationCode;
  }
  public Integer getBirthday() {
      return birthday;
  }
  public void setBirthday(Integer birthday) {
      this.birthday = birthday;
  }
  public String getGraduate() {
      return graduate;
  }
  public void setGraduate(String graduate) {
      this.graduate = graduate;
  }
  public String getDegree() {
      return degree;
  }
  public void setDegree(String degree) {
      this.degree = degree;
  }
  public Integer getNationality() {
      return nationality;
  }
  public void setNationality(Integer nationality) {
      this.nationality = nationality;
  }
  public String getOriginPlace() {
      return originPlace;
  }
  public void setOriginPlace(String originPlace) {
      this.originPlace = originPlace;
  }
  public String getBirthPlace() {
      return birthPlace;
  }
  public void setBirthPlace(String birthPlace) {
      this.birthPlace = birthPlace;
  }
  public String getPresentPesidence() {
      return presentPesidence;
  }
  public void setPresentPesidence(String presentPesidence) {
      this.presentPesidence = presentPesidence;
  }
  public String getPhone() {
      return phone;
  }
  public void setPhone(String phone) {
      this.phone = phone;
  }
  public String getEmail() {
      return email;
  }
  public void setEmail(String email) {
      this.email = email;
  }
  public String getSpecialty() {
      return specialty;
  }
  public void setSpecialty(String specialty) {
      this.specialty = specialty;
  }
  public String getIdCard() {
      return idCard;
  }
  public void setIdCard(String idCard) {
      this.idCard = idCard;
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
    public Integer getType() {
        return type;
    }
    public void setType(Integer type) {
        this.type = type;
    }
  @Override
  protected Serializable pkVal() {
      return id;
  }
}
