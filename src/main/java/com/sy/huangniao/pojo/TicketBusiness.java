package com.sy.huangniao.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class TicketBusiness implements Serializable {
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 商户身份证
     */
    private String businessIdentity;

    /**
     * 商户姓名
     */
    private String businessName;

    /**
     * 商户住址
     */
    private String businessAddrr;

    /**
     * 商户邮编
     */
    private String businessPost;

    /**
     * 商户认证状态
     */
    private String businessStatus;

    /**
     * 身份证图片
     */
    private String identityImage;

    /**
     * 商户创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    /**
     * 商户修改时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyDate;

    /**
     * 商户分成比率
     */
    private Integer benefitRate;

    /**
     * 备注
     */
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

    public String getBusinessIdentity() {
        return businessIdentity;
    }

    public void setBusinessIdentity(String businessIdentity) {
        this.businessIdentity = businessIdentity == null ? null : businessIdentity.trim();
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName == null ? null : businessName.trim();
    }

    public String getBusinessAddrr() {
        return businessAddrr;
    }

    public void setBusinessAddrr(String businessAddrr) {
        this.businessAddrr = businessAddrr == null ? null : businessAddrr.trim();
    }

    public String getBusinessPost() {
        return businessPost;
    }

    public void setBusinessPost(String businessPost) {
        this.businessPost = businessPost == null ? null : businessPost.trim();
    }

    public String getBusinessStatus() {
        return businessStatus;
    }

    public void setBusinessStatus(String businessStatus) {
        this.businessStatus = businessStatus == null ? null : businessStatus.trim();
    }

    public String getIdentityImage() {
        return identityImage;
    }

    public void setIdentityImage(String identityImage) {
        this.identityImage = identityImage == null ? null : identityImage.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Integer getBenefitRate() {
        return benefitRate;
    }

    public void setBenefitRate(Integer benefitRate) {
        this.benefitRate = benefitRate;
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
        sb.append(", businessIdentity=").append(businessIdentity);
        sb.append(", businessName=").append(businessName);
        sb.append(", businessAddrr=").append(businessAddrr);
        sb.append(", businessPost=").append(businessPost);
        sb.append(", businessStatus=").append(businessStatus);
        sb.append(", identityImage=").append(identityImage);
        sb.append(", createDate=").append(createDate);
        sb.append(", modifyDate=").append(modifyDate);
        sb.append(", benefitRate=").append(benefitRate);
        sb.append(", remark=").append(remark);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}