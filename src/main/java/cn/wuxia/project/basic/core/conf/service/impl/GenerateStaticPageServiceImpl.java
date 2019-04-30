/*
 * Copyright 2011-2020 wuxia.gd.cn All right reserved.
 */
package cn.wuxia.project.basic.core.conf.service.impl;

import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.wuxia.project.basic.core.conf.dao.GenerateStaticPageDao;
import cn.wuxia.project.basic.core.conf.entity.GenerateStaticPage;
import cn.wuxia.project.basic.core.conf.service.GenerateStaticPageService;
import cn.wuxia.project.common.dao.CommonDao;
import cn.wuxia.project.common.service.impl.CommonServiceImpl;
import cn.wuxia.common.util.FileUtil;
import cn.wuxia.common.util.StringUtil;

/**
 * GenerateStaticPage Service Implement.
 * 
 * @author songlin.li
 * @since 2014-04-16
 */
@Service
@Transactional
public class GenerateStaticPageServiceImpl extends CommonServiceImpl<GenerateStaticPage, String> implements GenerateStaticPageService {
    @Autowired
    private GenerateStaticPageDao generateStaticPageDao;

    @Override
    protected CommonDao<GenerateStaticPage, String> getCommonDao() {
        return generateStaticPageDao;
    }

    @Override
    public void generateOnePage(GenerateStaticPage staticPage) throws Exception {
        // 待请求参数数组
        String sourceUrl = staticPage.getSourceUrl();
        if (StringUtil.isNotBlank(staticPage.getParameter())) {
            sourceUrl += "?" + staticPage.getParameter();
        }

        FileUtil fileUtil = new FileUtil(staticPage.getToDistLocation(), staticPage.getFileName());

        // 建立链接
        URL url = new URL(sourceUrl);
        HttpURLConnection httpUrl = (HttpURLConnection) url.openConnection();
        // 连接指定的资源
        httpUrl.connect();

        // 建立文件
        fileUtil.createFile();
        if (StringUtil.endsWithIgnoreCase(staticPage.getFileName(), ".jsp")) {
            String contentType = "<%@ page pageEncoding=\"" + fileUtil.getCharset() + "\"%>";
            fileUtil.write(contentType);
        }
        // 获取网络输入流
        String content = fileUtil.read(httpUrl.getInputStream());
        fileUtil.write(content);
        httpUrl.disconnect();
        fileUtil.close();
        logger.debug("destUrl：[" + sourceUrl + "]， save to ：[" + fileUtil.getPath() + fileUtil.getFileName() + "]");

        /**
         * 修改生成的文件内容
         */
        //        FileUtil fileUtil = new FileUtil(path, staticPage.getFileName());
        //        String s = fileUtil.read();
        //        String httpPath = clientUrl + "/";
        //        s = s.toString().replaceAll("href='/", "href='" + httpPath).replaceAll("href=\"/", "href=\"" + httpPath);
        //        s = s.replaceAll("src='/", "src='" + httpPath).replaceAll("src=\"/", "src=\"" + httpPath);
        //        fileUtil.write(s);
        //        fileUtil.close();
        //        logger.debug("生成成功:" + path);
    }

}
