package com.sy.huangniao.pojo;

import java.io.Serializable;
import java.util.Date;

public class TicketOrder implements Serializable {
    private Integer id;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 车票身份证号
     */
    private String ticketIdentity;

    /**
     * 车票姓名
     */
    private String ticketName;

    /**
     * 出发地
     */
    private String fromSite;

    /**
     * 目的地
     */
    private String toSite;

    /**
     * 出发时间
     */
    private Date departureDate;

    /**
     * 车次
     */
    private String trainNum;

    /**
     * 席位
     */
    private String seatType;

    /**
     * 订单创建时间
     */
    private Date createDate;

    /**
     * 订单状态
     */
    private String orderStatus;

    /**
     * 订单修改时间
     */
    private Date modifyDate;

    /**
     * 订单金额
     */
    private Double orderAmount;

    /**
     * 抢单人数
     */
    private Integer robCount;

    /**
     * 备注
     */
    private String remark;

    /**
     * app代码
     */
    private String appCode;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTicketIdentity() {
        return ticketIdentity;
    }

    public void setTicketIdentity(String ticketIdentity) {
        this.ticketIdentity = ticketIdentity == null ? null : ticketIdentity.trim();
    }

    public String getTicketName() {
        return ticketName;
    }

    public void setTicketName(String ticketName) {
        this.ticketName = ticketName == null ? null : ticketName.trim();
    }

    public String getFromSite() {
        return fromSite;
    }

    public void setFromSite(String fromSite) {
        this.fromSite = fromSite == null ? null : fromSite.trim();
    }

    public String getToSite() {
        return toSite;
    }

    public void setToSite(String toSite) {
        this.toSite = toSite == null ? null : toSite.trim();
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public String getTrainNum() {
        return trainNum;
    }

    public void setTrainNum(String trainNum) {
        this.trainNum = trainNum == null ? null : trainNum.trim();
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType == null ? null : seatType.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus == null ? null : orderStatus.trim();
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Integer getRobCount() {
        return robCount;
    }

    public void setRobCount(Integer robCount) {
        this.robCount = robCount;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", orderNo=").append(orderNo);
        sb.append(", userId=").append(userId);
        sb.append(", ticketIdentity=").append(ticketIdentity);
        sb.append(", ticketName=").append(ticketName);
        sb.append(", from=").append(fromSite);
        sb.append(", to=").append(toSite);
        sb.append(", departureDate=").append(departureDate);
        sb.append(", trainNum=").append(trainNum);
        sb.append(", seatType=").append(seatType);
        sb.append(", createDate=").append(createDate);
        sb.append(", orderStatus=").append(orderStatus);
        sb.append(", modifyDate=").append(modifyDate);
        sb.append(", orderAmount=").append(orderAmount);
        sb.append(", robCount=").append(robCount);
        sb.append(", remark=").append(remark);
        sb.append(", appCode=").append(appCode);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}