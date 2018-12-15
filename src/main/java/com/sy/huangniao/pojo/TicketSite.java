package com.sy.huangniao.pojo;

import java.io.Serializable;
import java.util.Date;

public class TicketSite implements Serializable {
    private Integer id;

    /**
     * 站点中文
     */
    private String siteZw;

    /**
     * 站点代号
     */
    private String siteDh;

    /**
     * 站点拼音
     */
    private String sitePy;

    private String siteJx;

    /**
     * 创建时间
     */
    private Date siteCreatedate;

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

    public String getSiteZw() {
        return siteZw;
    }

    public void setSiteZw(String siteZw) {
        this.siteZw = siteZw == null ? null : siteZw.trim();
    }

    public String getSiteDh() {
        return siteDh;
    }

    public void setSiteDh(String siteDh) {
        this.siteDh = siteDh == null ? null : siteDh.trim();
    }

    public String getSitePy() {
        return sitePy;
    }

    public void setSitePy(String sitePy) {
        this.sitePy = sitePy == null ? null : sitePy.trim();
    }

    public String getSiteJx() {
        return siteJx;
    }

    public void setSiteJx(String siteJx) {
        this.siteJx = siteJx == null ? null : siteJx.trim();
    }

    public Date getSiteCreatedate() {
        return siteCreatedate;
    }

    public void setSiteCreatedate(Date siteCreatedate) {
        this.siteCreatedate = siteCreatedate;
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
        sb.append(", siteZw=").append(siteZw);
        sb.append(", siteDh=").append(siteDh);
        sb.append(", sitePy=").append(sitePy);
        sb.append(", siteJx=").append(siteJx);
        sb.append(", siteCreatedate=").append(siteCreatedate);
        sb.append(", remark=").append(remark);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}