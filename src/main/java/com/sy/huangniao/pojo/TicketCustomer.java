package com.sy.huangniao.pojo;

import java.io.Serializable;
import java.util.Date;

public class TicketCustomer implements Serializable {
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 客户默认身份证
     */
    private String customerIdentity;

    /**
     * 身份证图片
     */
    private String identityImage;

    /**
     * 客户默认姓名
     */
    private String customerName;

    /**
     * 客户住址
     */
    private String customerAddrr;

    /**
     * 客户邮编
     */
    private String customerPost;

    /**
     * 客户12306账号
     */
    private String customerAccount;

    /**
     * 客户12306密码
     */
    private String customerPassword;

    /**
     * 客户认证状态
     */
    private String customerStatus;

    /**
     * 客户创建时间
     */
    private Date createDate;

    /**
     * 客户修改时间
     */
    private Date modifyDate;

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

    public String getCustomerIdentity() {
        return customerIdentity;
    }

    public void setCustomerIdentity(String customerIdentity) {
        this.customerIdentity = customerIdentity == null ? null : customerIdentity.trim();
    }

    public String getIdentityImage() {
        return identityImage;
    }

    public void setIdentityImage(String identityImage) {
        this.identityImage = identityImage == null ? null : identityImage.trim();
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public String getCustomerAddrr() {
        return customerAddrr;
    }

    public void setCustomerAddrr(String customerAddrr) {
        this.customerAddrr = customerAddrr == null ? null : customerAddrr.trim();
    }

    public String getCustomerPost() {
        return customerPost;
    }

    public void setCustomerPost(String customerPost) {
        this.customerPost = customerPost == null ? null : customerPost.trim();
    }

    public String getCustomerAccount() {
        return customerAccount;
    }

    public void setCustomerAccount(String customerAccount) {
        this.customerAccount = customerAccount == null ? null : customerAccount.trim();
    }

    public String getCustomerPassword() {
        return customerPassword;
    }

    public void setCustomerPassword(String customerPassword) {
        this.customerPassword = customerPassword == null ? null : customerPassword.trim();
    }

    public String getCustomerStatus() {
        return customerStatus;
    }

    public void setCustomerStatus(String customerStatus) {
        this.customerStatus = customerStatus == null ? null : customerStatus.trim();
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
        sb.append(", customerIdentity=").append(customerIdentity);
        sb.append(", identityImage=").append(identityImage);
        sb.append(", customerName=").append(customerName);
        sb.append(", customerAddrr=").append(customerAddrr);
        sb.append(", customerPost=").append(customerPost);
        sb.append(", customerAccount=").append(customerAccount);
        sb.append(", customerPassword=").append(customerPassword);
        sb.append(", customerStatus=").append(customerStatus);
        sb.append(", createDate=").append(createDate);
        sb.append(", modifyDate=").append(modifyDate);
        sb.append(", remark=").append(remark);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}