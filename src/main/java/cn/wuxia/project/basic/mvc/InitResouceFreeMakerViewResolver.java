package cn.wuxia.project.basic.mvc;

import cn.wuxia.common.util.ClassLoaderUtil;
import cn.wuxia.common.util.ListUtil;
import cn.wuxia.common.util.StringUtil;
import cn.wuxia.project.basic.core.conf.support.DTools;
import cn.wuxia.project.basic.core.conf.support.DicBean;
import cn.wuxia.project.basic.support.ApplicationPropertiesUtil;
import cn.wuxia.project.basic.support.DConstants;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class InitResouceFreeMakerViewResolver extends FreeMarkerViewResolver {
    protected final Logger logger = LoggerFactory.getLogger(InitResouceFreeMakerViewResolver.class);

    public String dicCode;

    public InitResouceFreeMakerViewResolver() {
        super();
        String classPath = ClassLoaderUtil.getAbsolutePathOfClassLoaderClassPath();
        String webInfoPath = StringUtil.substringBeforeLast(classPath, File.separator);
        if (StringUtil.endsWith(webInfoPath, "classes")) {
            webInfoPath = StringUtil.substringBeforeLast(webInfoPath, File.separator);
        }
        if (StringUtil.startsWith(webInfoPath, "file:")) {
            webInfoPath = StringUtil.substringAfter(webInfoPath, "file:");
        }
        String freemarker = webInfoPath + File.separator + "freemarker";
        File freemarkerFolder = new File(freemarker);
        if (!freemarkerFolder.exists()) {
            freemarkerFolder.mkdir();
            logger.info("mkdir folder:" + freemarker);
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
        super.setAttributesMap(map);
        super.setSuffix(".ftl");
        super.setContentType("text/html;charset=utf-8");
        super.setExposeRequestAttributes(true);
        super.setExposeSessionAttributes(true);
        super.setExposeSpringMacroHelpers(true);
        super.setRequestContextAttribute("request");
        super.setOrder(0);
    }

    public String getDicCode() {
        return dicCode;
    }

    public void setDicCode(String dicCode) {
        this.dicCode = dicCode;
    }
}
