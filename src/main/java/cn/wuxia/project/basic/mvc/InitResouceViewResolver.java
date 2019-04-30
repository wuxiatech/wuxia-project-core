package cn.wuxia.project.basic.mvc;

import cn.wuxia.project.basic.core.conf.support.DTools;
import cn.wuxia.project.basic.core.conf.support.DicBean;
import cn.wuxia.project.basic.support.ApplicationPropertiesUtil;
import cn.wuxia.project.basic.support.DConstants;
import cn.wuxia.common.util.ClassLoaderUtil;
import cn.wuxia.common.util.ListUtil;
import cn.wuxia.common.util.StringUtil;
import com.google.common.collect.Maps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class InitResouceViewResolver extends InternalResourceViewResolver {
    protected final  Logger logger = LoggerFactory.getLogger(InitResouceViewResolver.class);

    public String dicCode;

    public InitResouceViewResolver() {
        super("/WEB-INF/views/", ".jsp");
        String classPath = ClassLoaderUtil.getAbsolutePathOfClassLoaderClassPath();
        String webInfoPath = StringUtil.substringBeforeLast(classPath, File.separator);
        if (StringUtil.endsWith(webInfoPath, "classes")) {
            webInfoPath = StringUtil.substringBeforeLast(webInfoPath, File.separator);
        }
        if (StringUtil.startsWith(webInfoPath, "file:")) {
            webInfoPath = StringUtil.substringAfter(webInfoPath, "file:");
        }
        String views = webInfoPath + File.separator + "views";
        File viewsFolder = new File(views);
        if (!viewsFolder.exists()) {
            viewsFolder.mkdir();
            logger.info("mkdir folder:" + views);
        }

        dicCode = StringUtil.isBlank(dicCode) ? DConstants.SPRINGMVC_INITPARAMS : dicCode;
        List<DicBean> dicBeanList = DTools.dicByParentCode(dicCode);
        Map<String, Object> map = Maps.newHashMap();
        if (ListUtil.isNotEmpty(dicBeanList)) {
            for (DicBean dicBean : dicBeanList) {
                map.put(dicBean.getCode(), dicBean.getValue());
            }
        }

        Properties properties = ApplicationPropertiesUtil.getProperties();
        for (Map.Entry<Object, Object> appProp : properties.entrySet()) {
            if (StringUtil.startsWithIgnoreCase((String) appProp.getKey(), dicCode)) {
                String key = StringUtil.substringAfter((String) appProp.getKey(), dicCode + ".");
                map.put(key, appProp.getValue());
            }
        }
        logger.info("设置页面静态调用参数：" + map.toString());
        super.setViewClass(JstlView.class);
        super.setAttributesMap(map);
        super.setOrder(1);

    }

    public String getDicCode() {
        return dicCode;
    }

    public void setDicCode(String dicCode) {
        this.dicCode = dicCode;
    }
}
