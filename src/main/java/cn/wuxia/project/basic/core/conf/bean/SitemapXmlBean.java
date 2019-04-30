package cn.wuxia.project.basic.core.conf.bean;

import java.io.Serializable;

public class SitemapXmlBean implements Serializable {
    private String loc;

    private String changefreq;

    private String priority;

    private String lastmod;

    public SitemapXmlBean(String loc, String changefreg, String priority, String lastmod) {
        super();
        this.loc = loc;
        this.changefreq = changefreg;
        this.priority = priority;
        this.lastmod = lastmod;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getChangefreq() {
        return changefreq;
    }

    public void setChangefreq(String changefreq) {
        this.changefreq = changefreq;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getLastmod() {
        return lastmod;
    }

    public void setLastmod(String lastmod) {
        this.lastmod = lastmod;
    }

}
