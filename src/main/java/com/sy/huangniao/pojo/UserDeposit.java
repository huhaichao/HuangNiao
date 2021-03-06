package com.sy.huangniao.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class UserDeposit implements Serializable {
    private Integer id;

    /**
     * 充值流水号
     */
    private String depositNo;

    /**
     * 第三方渠道订单号--微信订单号
     */
    private String tradeChannelsNo;

    /**
     * 充值用户
     */
    private Integer userId;

    /**
     * 充值金额
     */
    private Double amount;

    /**
     * 充值时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    /**
     * 充值状态
     */
    private String status;

    /**
     * 修改时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyDate;

    /**
     * app代号
     */
    private String appCode;

    /**
     * 订单号--下单时候充值
     */
    private String orderNo;

    /**
     * 资金来源账号
     */
    private String fromAccount;

    /**
     * 用户IP
     */
    private String ip;

    /**
     * 支付类型
     */
    private String tradeType;

    /**
     * 预支付id
     */
    private String prepayId;

    /**
     * 支付完成时间
     */
    private String timeEnd;

    /**
     * 付款银行
     */
    private String bankType;

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

    public String getDepositNo() {
        return depositNo;
    }

    public void setDepositNo(String depositNo) {
        this.depositNo = depositNo == null ? null : depositNo.trim();
    }

    public String getTradeChannelsNo() {
        return tradeChannelsNo;
    }

    public void setTradeChannelsNo(String tradeChannelsNo) {
        this.tradeChannelsNo = tradeChannelsNo;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode == null ? null : appCode.trim();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount == null ? null : fromAccount.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType == null ? null : tradeType.trim();
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId == null ? null : prepayId.trim();
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd == null ? null : timeEnd.trim();
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType == null ? null : bankType.trim();
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
        sb.append(", depositNo=").append(depositNo);
        sb.append(", tradeChannelsNo=").append(tradeChannelsNo);
        sb.append(", userId=").append(userId);
        sb.append(", amount=").append(amount);
        sb.append(", createDate=").append(createDate);
        sb.append(", status=").append(status);
        sb.append(", modifyDate=").append(modifyDate);
        sb.append(", appCode=").append(appCode);
        sb.append(", orderNo=").append(orderNo);
        sb.append(", from_account=").append(fromAccount);
        sb.append(", ip=").append(ip);
        sb.append(", tradeType=").append(tradeType);
        sb.append(", prepayId=").append(prepayId);
        sb.append(", timeEnd=").append(timeEnd);
        sb.append(", bankType=").append(bankType);
        sb.append(", remark=").append(remark);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}