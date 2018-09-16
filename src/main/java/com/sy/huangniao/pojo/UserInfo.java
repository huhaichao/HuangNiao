package com.sy.huangniao.pojo;

import java.io.Serializable;
import java.util.Date;

public class UserInfo implements Serializable {
    private Integer id;

    /**
     * 用户手机号
     */
    private String userPhoneno;

    /**
     * 性别
     */
    private String userSex;

    /**
     * 用户生日
     */
    private Date userBirthday;

    /**
     * 用户头像
     */
    private String userImage;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户微信号
     */
    private String userWxno;

    /**
     * 用户角色
     */
    private String userRole;

    /**
     * 用户创建时间
     */
    private Date createDate;

    /**
     * 用户状态
     */
    private String userStatus;

    /**
     * 用户修改时间
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

    public String getUserPhoneno() {
        return userPhoneno;
    }

    public void setUserPhoneno(String userPhoneno) {
        this.userPhoneno = userPhoneno == null ? null : userPhoneno.trim();
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex == null ? null : userSex.trim();
    }

    public Date getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(Date userBirthday) {
        this.userBirthday = userBirthday;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage == null ? null : userImage.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getUserWxno() {
        return userWxno;
    }

    public void setUserWxno(String userWxno) {
        this.userWxno = userWxno == null ? null : userWxno.trim();
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole == null ? null : userRole.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus == null ? null : userStatus.trim();
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
        sb.append(", userPhoneno=").append(userPhoneno);
        sb.append(", userSex=").append(userSex);
        sb.append(", userBirthday=").append(userBirthday);
        sb.append(", userImage=").append(userImage);
        sb.append(", userName=").append(userName);
        sb.append(", password=").append(password);
        sb.append(", userWxno=").append(userWxno);
        sb.append(", userRole=").append(userRole);
        sb.append(", createDate=").append(createDate);
        sb.append(", userStatus=").append(userStatus);
        sb.append(", modifyDate=").append(modifyDate);
        sb.append(", remark=").append(remark);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}