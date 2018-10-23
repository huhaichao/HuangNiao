package com.sy.huangniao.pojo;

import java.io.Serializable;
import java.util.Date;

public class ReturnOrder implements Serializable {
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 退单号
     */
    private String returnNo;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 充值单号
     */
    private String depositNo;


    /**
     * 第三订单号
     */
    private String tradeChannelsNo;

    /**
     * 第三方退款单号
     */
    private String tradeChannelsReturnNo;

    /**
     * 退款金额
     */
    private Double returnAmount;

    /**
     * 订单金额
     */
    private Double orderAmount;

    /**
     * 退款状态
     */
    private String returnStatus;

    /**
     * 退款类型
     */
    private String returnType;

    /**
     * 退款时间
     */
    private Date createDate;

    /**
     * 修改时间
     */
    private Date modifyDate;

    /**
     * 退款完成时间
     */
    private Date retunTime;

    /**
     * 备注
     */
    private String remark;

    /**
     * app代码
     */
    private String appCode;

    /**
     * 退款资金来源
     */
    private String refundAccount;

    /**
     * 退款发起来源
     */
    private String refundRequestSource;

    /**
     * 退款入账账户
     */
    private String refundRecvAccout;

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

    public String getReturnNo() {
        return returnNo;
    }

    public void setReturnNo(String returnNo) {
        this.returnNo = returnNo == null ? null : returnNo.trim();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getTradeChannelsNo() {
        return tradeChannelsNo;
    }

    public void setTradeChannelsNo(String tradeChannelsNo) {
        this.tradeChannelsNo = tradeChannelsNo == null ? null : tradeChannelsNo.trim();
    }

    public String getTradeChannelsReturnNo() {
        return tradeChannelsReturnNo;
    }

    public void setTradeChannelsReturnNo(String tradeChannelsReturnNo) {
        this.tradeChannelsReturnNo = tradeChannelsReturnNo == null ? null : tradeChannelsReturnNo.trim();
    }

    public Double getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(Double returnAmount) {
        this.returnAmount = returnAmount;
    }

    public Double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(String returnStatus) {
        this.returnStatus = returnStatus == null ? null : returnStatus.trim();
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType == null ? null : returnType.trim();
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

    public Date getRetunTime() {
        return retunTime;
    }

    public void setRetunTime(Date retunTime) {
        this.retunTime = retunTime;
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

    public String getRefundAccount() {
        return refundAccount;
    }

    public void setRefundAccount(String refundAccount) {
        this.refundAccount = refundAccount == null ? null : refundAccount.trim();
    }

    public String getRefundRequestSource() {
        return refundRequestSource;
    }

    public void setRefundRequestSource(String refundRequestSource) {
        this.refundRequestSource = refundRequestSource == null ? null : refundRequestSource.trim();
    }

    public String getRefundRecvAccout() {
        return refundRecvAccout;
    }

    public void setRefundRecvAccout(String refundRecvAccout) {
        this.refundRecvAccout = refundRecvAccout == null ? null : refundRecvAccout.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", returnNo=").append(returnNo);
        sb.append(", orderNo=").append(orderNo);
        sb.append(", depositNo=").append(depositNo);
        sb.append(", tradeChannelsNo=").append(tradeChannelsNo);
        sb.append(", tradeChannelsReturnNo=").append(tradeChannelsReturnNo);
        sb.append(", returnAmount=").append(returnAmount);
        sb.append(", orderAmount=").append(orderAmount);
        sb.append(", returnStatus=").append(returnStatus);
        sb.append(", returnType=").append(returnType);
        sb.append(", createDate=").append(createDate);
        sb.append(", modifyDate=").append(modifyDate);
        sb.append(", retunTime=").append(retunTime);
        sb.append(", remark=").append(remark);
        sb.append(", appCode=").append(appCode);
        sb.append(", refundAccount=").append(refundAccount);
        sb.append(", refundRequestSource=").append(refundRequestSource);
        sb.append(", refundRecvAccout=").append(refundRecvAccout);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    public String getDepositNo() {
        return depositNo;
    }

    public void setDepositNo(String depositNo) {
        this.depositNo = depositNo;
    }
}