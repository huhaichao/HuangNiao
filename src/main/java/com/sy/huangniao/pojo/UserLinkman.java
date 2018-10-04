package com.sy.huangniao.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;

import java.io.Serializable;
import java.util.Date;

public class UserLinkman implements Serializable {
    @JsonIgnore
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 身份证
     */
    private String indentity;

    /**
     * 名称
     */
    private String name;

    /**
     * 创建时间
     */
    @JsonIgnore
    private Date createDate;

    /**
     * app代码
     */
    @JsonIgnore
    private String appCode;

    /**
     * 状态
     */
    private String status;

    /**
     * 修改时间
     */
    @JsonIgnore
    private Date modifyDate;

    /**
     * 籍贯
     */
    @JsonIgnore
    private String area;

    /**
     * 省份
     */
    @JsonIgnore
    private String province;

    /**
     * 城市
     */
    @JsonIgnore
    private String city;

    /**
     * 县
     */
    @JsonIgnore
    private String prefecture;

    /**
     * 生日
     */
    @JsonIgnore
    private String birthday;

    /**
     * 地址编码
     */
    @JsonIgnore
    private String addrcode;

    /**
     * 备注
     */
    @JsonIgnore
    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getIndentity() {
        return indentity;
    }

    public void setIndentity(String indentity) {
        this.indentity = indentity == null ? null : indentity.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode == null ? null : appCode.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getPrefecture() {
        return prefecture;
    }

    public void setPrefecture(String prefecture) {
        this.prefecture = prefecture == null ? null : prefecture.trim();
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday == null ? null : birthday.trim();
    }

    public String getAddrcode() {
        return addrcode;
    }

    public void setAddrcode(String addrcode) {
        this.addrcode = addrcode == null ? null : addrcode.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", indentity=").append(indentity);
        sb.append(", name=").append(name);
        sb.append(", createDate=").append(createDate);
        sb.append(", appCode=").append(appCode);
        sb.append(", status=").append(status);
        sb.append(", modifyDate=").append(modifyDate);
        sb.append(", area=").append(area);
        sb.append(", province=").append(province);
        sb.append(", city=").append(city);
        sb.append(", prefecture=").append(prefecture);
        sb.append(", birthday=").append(birthday);
        sb.append(", addrcode=").append(addrcode);
        sb.append(", remark=").append(remark);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}