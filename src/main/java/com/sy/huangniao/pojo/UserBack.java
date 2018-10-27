package com.sy.huangniao.pojo;

import java.io.Serializable;
import java.util.Date;

public class UserBack implements Serializable {
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * app代码
     */
    private String appCode;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 处理人
     */
    private Integer handleUser;

    /**
     * 状态
     */
    private String status;

    /**
     * 修改时间
     */
    private Date modifyDate;

    /**
     * 备注
     */
    private String remark;

    /**
     * 反馈内容
     */
    private String context;

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

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode == null ? null : appCode.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getHandleUser() {
        return handleUser;
    }

    public void setHandleUser(Integer handleUser) {
        this.handleUser = handleUser;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context == null ? null : context.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", appCode=").append(appCode);
        sb.append(", createDate=").append(createDate);
        sb.append(", handleUser=").append(handleUser);
        sb.append(", status=").append(status);
        sb.append(", modifyDate=").append(modifyDate);
        sb.append(", remark=").append(remark);
        sb.append(", context=").append(context);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}