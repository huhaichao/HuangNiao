package com.sy.huangniao.pojo;

import java.io.Serializable;
import java.util.Date;

public class UserWithdraw implements Serializable {
    private Integer id;

    /**
     * 提现单号
     */
    private String withdrawNo;

    /**
     * 提现金额
     */
    private Double amount;

    /**
     * 提现账号
     */
    private String toAccount;

    /**
     * 提现类型
     */
    private String tradeType;

    /**
     * 提现状态
     */
    private String status;

    /**
     * 提现第三方订单号
     */
    private String tradeChannelsNo;

    /**
     * app代码
     */
    private String appCode;

    /**
     * 提现时间
     */
    private Date createDate;

    /**
     * 修改时间
     */
    private Date modifyDate;

    /**
     * 备注
     */
    private Integer remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWithdrawNo() {
        return withdrawNo;
    }

    public void setWithdrawNo(String withdrawNo) {
        this.withdrawNo = withdrawNo == null ? null : withdrawNo.trim();
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount == null ? null : toAccount.trim();
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType == null ? null : tradeType.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getTradeChannelsNo() {
        return tradeChannelsNo;
    }

    public void setTradeChannelsNo(String tradeChannelsNo) {
        this.tradeChannelsNo = tradeChannelsNo == null ? null : tradeChannelsNo.trim();
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

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Integer getRemark() {
        return remark;
    }

    public void setRemark(Integer remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", withdrawNo=").append(withdrawNo);
        sb.append(", amount=").append(amount);
        sb.append(", to=").append(toAccount);
        sb.append(", tradeType=").append(tradeType);
        sb.append(", status=").append(status);
        sb.append(", tradeChannelsNo=").append(tradeChannelsNo);
        sb.append(", appCode=").append(appCode);
        sb.append(", createDate=").append(createDate);
        sb.append(", modifyDate=").append(modifyDate);
        sb.append(", remark=").append(remark);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}