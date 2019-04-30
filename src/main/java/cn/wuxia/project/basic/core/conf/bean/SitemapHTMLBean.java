package cn.wuxia.project.basic.core.conf.bean;

/**
 * 
 * [ticket id]
 *   
 * @author wuwenhao
 * @ Version : V<Ver.No> <2014年10月17日>
 */
public class SitemapHTMLBean {
    private String title;//标题

    private String url;//内容

    public SitemapHTMLBean(String title, String url) {
        super();
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
