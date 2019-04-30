package cn.wuxia.project.basic.mvc;

import cn.wuxia.project.basic.core.conf.support.DTools;
import cn.wuxia.project.basic.core.conf.support.DicBean;
import cn.wuxia.project.basic.support.ApplicationPropertiesUtil;
import cn.wuxia.project.basic.support.DConstants;
import cn.wuxia.common.util.ListUtil;
import cn.wuxia.common.util.StringUtil;
import com.google.common.collect.Maps;

import freemarker.template.utility.XmlEscape;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.List;
import java.util.Map;
import java.util.Properties;

public class InitResouceFreeMarkerConfigurer extends FreeMarkerConfigurer {
    protected final Logger logger = LoggerFactory.getLogger(InitResouceFreeMarkerConfigurer.class);

    public String dicCode;

    public InitResouceFreeMarkerConfigurer() {
        this.setTemplateLoaderPath("/WEB-INF/freemarker/");
        List<DicBean> dicBeanList = DTools.dicByParentCode(StringUtil.isBlank(dicCode) ? DConstants.SPRINGMVC_INITPARAMS : dicCode);
        Properties prop = new Properties();
        //        <!--刷新模板的周期，上线设置为较大数字, 单位为秒 -->
        prop.put("template_update_delay", "300");
        //        <!--模板的编码格式 -->
        prop.put("default_encoding", "UTF-8");
        //        <!-- 本地化设置 -->
        prop.put("locale", "UTF-8");
        prop.put("datetime_format", "yyyy-MM-dd HH:mm:ss");
        prop.put("time_format", "HH:mm:ss");
        prop.put("number_format", "0.####");
        prop.put("boolean_format", "true,false");
        prop.put("whitespace_stripping", "true");
        prop.put("tag_syntax", "auto_detect");
        prop.put("url_escaping_charset", "UTF-8");
        this.setFreemarkerSettings(prop);

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
        map.put("xml_escape", new XmlEscape());
        logger.info("设置页面静态调用参数：" + map.toString());
        this.setFreemarkerVariables(map);
    }

    public String getDicCode() {
        return dicCode;
    }

    public void setDicCode(String dicCode) {
        this.dicCode = dicCode;
    }
}
