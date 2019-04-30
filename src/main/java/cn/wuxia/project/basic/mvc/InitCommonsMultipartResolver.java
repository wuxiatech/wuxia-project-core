package cn.wuxia.project.basic.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

public class InitCommonsMultipartResolver extends CommonsMultipartResolver {
    protected final  Logger logger = LoggerFactory.getLogger(InitCommonsMultipartResolver.class);


    public String dicCode;


    public InitCommonsMultipartResolver() {
        super();
//        List<DicBean> dicBeanList = DicConfigUtil.getByParentCode(StringUtil.isBlank(dicCode) ? DEFAULT_MVC_ATTR : dicCode);
//        Map<String, Object> map = Maps.newHashMap();
//        if (ListUtil.isNotEmpty(dicBeanList)) {
//            for (DicBean dicBean : dicBeanList) {
//                map.put(dicBean.getCode(), dicBean.getValue());
//            }
//        }
        setDefaultEncoding("UTF-8");
        /**
         * 上传文件大小限制为31M，31*1024*1024
         */
        setMaxUploadSize(32505856);
    }

    public String getDicCode() {
        return dicCode;
    }

    public void setDicCode(String dicCode) {
        this.dicCode = dicCode;
    }
}
