package com.sy.huangniao.pojo;

import java.io.Serializable;
import java.util.Date;

public class RobOrder implements Serializable {
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 订单号
     */
    private String orderId;

    /**
     * 抢单状态
     */
    private String robStatus;

    /**
     * 证明截图
     */
    private String proofImage;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改时间
     */
    private Date modifyDate;

    /**
     * 备注--被谁抢了
     */
    private String remark;

    /**
     * app代码
     */
    private String appCode;

    /**
     * 删除标识
     */
    private Integer robScbs;

    /**
     * 閹剝绁婚柌鎴︻杺
     */
    private Double robAmount;

    /**
     * 抢单成功提示信息
     */
    private String robContext;

    /**
     * 订单信息
     */
    private TicketOrder ticketOrder;

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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getRobStatus() {
        return robStatus;
    }

    public void setRobStatus(String robStatus) {
        this.robStatus = robStatus == null ? null : robStatus.trim();
    }

    public String getProofImage() {
        return proofImage;
    }

    public void setProofImage(String proofImage) {
        this.proofImage = proofImage == null ? null : proofImage.trim();
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

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode == null ? null : appCode.trim();
    }

    public Integer getRobScbs() {
        return robScbs;
    }

    public void setRobScbs(Integer robScbs) {
        this.robScbs = robScbs;
    }

    public Double getRobAmount() {
        return robAmount;
    }

    public void setRobAmount(Double robAmount) {
        this.robAmount = robAmount;
    }

    public String getRobContext() {
        return robContext;
    }

    public void setRobContext(String robContext) {
        this.robContext = robContext == null ? null : robContext.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", orderId=").append(orderId);
        sb.append(", robStatus=").append(robStatus);
        sb.append(", proofImage=").append(proofImage);
        sb.append(", createDate=").append(createDate);
        sb.append(", modifyDate=").append(modifyDate);
        sb.append(", remark=").append(remark);
        sb.append(", appCode=").append(appCode);
        sb.append(", robScbs=").append(robScbs);
        sb.append(", robAmount=").append(robAmount);
        sb.append(", robContext=").append(robContext);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    public TicketOrder getTicketOrder() {
        return ticketOrder;
    }

    public void setTicketOrder(TicketOrder ticketOrder) {
        this.ticketOrder = ticketOrder;
    }
}