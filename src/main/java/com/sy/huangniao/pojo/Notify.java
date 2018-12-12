package com.sy.huangniao.pojo;

import java.io.Serializable;
import java.util.Date;

public class Notify implements Serializable {
    private Integer id;

    /**
     * 发送方
     */
    private String fromNo;

    /**
     * 接收方 -- 为空通知所有
     */
    private String toNo;

    /**
     * 主题
     */
    private String title;

    /**
     * 通知次数
     */
    private Integer notifyCount;

    /**
     * 消息类型
     */
    private String msgType;

    /**
     * 通知状态
     */
    private String notifyStatus;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 指定通知时间
     */
    private Date notifyDate;

    /**
     * 修改时间
     */
    private Date modifyDate;

    private String context;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFromNo() {
        return fromNo;
    }

    public void setFromNo(String fromNo) {
        this.fromNo = fromNo == null ? null : fromNo.trim();
    }

    public String getToNo() {
        return toNo;
    }

    public void setToNo(String toNo) {
        this.toNo = toNo == null ? null : toNo.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getNotifyCount() {
        return notifyCount;
    }

    public void setNotifyCount(Integer notifyCount) {
        this.notifyCount = notifyCount;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType == null ? null : msgType.trim();
    }

    public String getNotifyStatus() {
        return notifyStatus;
    }

    public void setNotifyStatus(String notifyStatus) {
        this.notifyStatus = notifyStatus == null ? null : notifyStatus.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getNotifyDate() {
        return notifyDate;
    }

    public void setNotifyDate(Date notifyDate) {
        this.notifyDate = notifyDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
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
        sb.append(", fromNo=").append(fromNo);
        sb.append(", toNo=").append(toNo);
        sb.append(", title=").append(title);
        sb.append(", notifyCount=").append(notifyCount);
        sb.append(", msgType=").append(msgType);
        sb.append(", notifyStatus=").append(notifyStatus);
        sb.append(", createDate=").append(createDate);
        sb.append(", notifyDate=").append(notifyDate);
        sb.append(", modifyDate=").append(modifyDate);
        sb.append(", context=").append(context);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}