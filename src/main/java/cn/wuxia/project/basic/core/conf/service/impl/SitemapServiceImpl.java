package cn.wuxia.project.basic.core.conf.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.ServletContext;

import cn.wuxia.project.basic.support.ApplicationPropertiesUtil;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.wuxia.project.basic.core.conf.bean.SitemapXmlBean;
import cn.wuxia.project.basic.core.conf.entity.GenerateStaticPage;
import cn.wuxia.project.basic.core.conf.service.GenerateStaticPageService;
import cn.wuxia.common.spring.SpringContextHolder;
import cn.wuxia.common.util.ClassLoaderUtil;
import cn.wuxia.common.util.FileUtil;
import cn.wuxia.common.util.StringUtil;

@Service
@Transactional
public class SitemapServiceImpl {
    protected final Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private GenerateStaticPageService generateStaticPageService;

    /**
     * <pre>
     * http://share.fddlife.com/article/ckbgfa
     * </pre>
     */
    private final String article = "/article/";

    /**
     * 存放xml的路径为根路径
     */
    private String xmlPath;

    private String ctx = ApplicationPropertiesUtil.getPropertiesValue("sitemap.domain");


    public void genSitemapXml(String target) {

        try {
            genArticleXml(target);
        } catch (Exception e) {
            logger.error("", e);
        }

    }

    public void genSitemapHtml(String target) {

        try {
            createHtmlFile(target, this.article);
        } catch (Exception e) {
            logger.error("", e);
        }

    }

    /**
     * 生成一个sitemapxml
     *
     * @param staticPage
     * @throws Exception
     * @author songlin
     */
    private void createXMLFile(List<SitemapXmlBean> smsbList, String desc, String fileName) throws Exception {
        //建立document对象  
        try {
            Document document = DocumentHelper.createDocument();
            Element urlset = document.addElement("urlset", "http://www.sitemaps.org/schemas/sitemap/0.9");
            //urlset.addNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
            //urlset.addAttribute("xsi:schemaLocation",
            //        "http://www.sitemaps.org/schemas/sitemap/0.9 http://www.sitemaps.org/schemas/sitemap/0.9/sitemap.xsd");

            for (int i = 0; i < smsbList.size(); i++) {
                SitemapXmlBean site = smsbList.get(i);
                Element request = urlset.addElement("url"); //添加root的子节点  
                Element loc = request.addElement("loc");
                loc.addText(site.getLoc());

                Element ld = request.addElement("lastmod");
                ld.addText(site.getLastmod());

                Element cd = request.addElement("changefreq");
                cd.addText(site.getChangefreq());

                Element priority = request.addElement("priority");
                priority.addText(site.getPriority());
            }
            //输出全部原始数据，在编译器中显示  
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("utf-8");//根据需要设置编码  
            // 输出全部原始数据，并用它生成新的我们需要的XML文件
            String target = StringUtil.isBlank(desc) ? xmlPath : desc;
            FileUtil.forceMkdir(new File(target));
            fileName = target + File.separator + fileName;
            XMLWriter writer = new XMLWriter(new FileWriter(new File(fileName)), format);
            writer.write(document); //输出到文件  
            writer.close();
        } catch (UnsupportedEncodingException e) {
            logger.error("", e);
        } catch (IOException e) {
            logger.error("", e);
        }
    }

    /**
     * 获取产品明细对应的Code转变成url
     * 请求的路径分类，如：
     * <pre>
     * http://share.fddlife.com/article/eszvtqhtml
     * </pre>
     *
     * @param staticPage
     * @throws Exception
     * @author songlin
     */
    public void genArticleXml(String target) throws Exception {
        genArticleXmlByPage(target, 1);
    }

    /**
     * 每份xml最多有50000条url
     *
     * @param target
     * @param pageNo
     * @author songlin
     */
    private void genArticleXmlByPage(String target, int pageNo) {
//        Pages<WxNews> page = new Pages(pageNo, 50000);
//        page = articleService.findAll(page);
//        List<SitemapXmlBean> serviceList = Lists.newArrayList();
//        for (WxNews s : page.getResult()) {
//            String lastd = DateUtil.dateToString(s.getModifiedOn(), DateUtil.DateFormatter.FORMAT_YYYY_MM_DD);
//            serviceList.add(new SitemapXmlBean(ctx + this.article + s.getId(), "daily", "1.00", lastd));
//        }
//        try {
//            createXMLFile(serviceList, target, "sitemap_article_" + pageNo + ".xml");
//        } catch (Exception e) {
//            logger.error("", e);
//        }
//        if (page.isHasNext())
//            genArticleXmlByPage(target, page.getNextPage());
    }

    private void createHtmlFile(String desc, String path) {
        path = StringUtil.replace(path, "/", "");
        GenerateStaticPage staticPage = new GenerateStaticPage();
        staticPage.setSourceUrl(ctx + "/system/gen/sitemap/html?path=" + path);
        staticPage.setToDistLocation(desc);
        staticPage.setFileName("sitemap_" + path + ".html");
        try {
            generateStaticPageService.generateOnePage(staticPage);
        } catch (Exception e) {
            logger.error("", e);
        }
    }


    public String getXmlPath() throws Exception {
        if(StringUtil.isBlank(xmlPath)){
        ServletContext sc = SpringContextHolder.getServletContext();
        if (sc != null) {
            xmlPath = sc.getRealPath("/") + "/sitemap/";
        } else {
            String currentPath = ClassLoaderUtil.getAbsolutePathOfClassLoaderClassPath();
            xmlPath = StringUtil.replace(currentPath, "file:", "").replace("/WEB-INF/classes/", "/sitemap/");
        }
        }
        return xmlPath;
    }
}
