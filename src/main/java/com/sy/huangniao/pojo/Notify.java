package com.sy.huangniao.pojo;

import java.io.Serializable;
import java.util.Date;

public class Notify implements Serializable {
    private Integer id;

    /**
     * 发送方
     */
    private String from;

    /**
     * 接收方 -- 为空通知所有
     */
    private String to;

    /**
     * 主题
     */
    private String title;

    /**
     * 通知状态
     */
    private Integer notifyStatus;

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

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from == null ? null : from.trim();
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to == null ? null : to.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getNotifyStatus() {
        return notifyStatus;
    }

    public void setNotifyStatus(Integer notifyStatus) {
        this.notifyStatus = notifyStatus;
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
        sb.append(", from=").append(from);
        sb.append(", to=").append(to);
        sb.append(", title=").append(title);
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