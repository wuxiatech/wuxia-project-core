/*
 * Created on :2017年10月12日
 * Author     :songlin
 * Change History
 * Version       Date         Author           Reason
 * <Ver.No>     <date>        <who modify>       <reason>
 * Copyright 2014-2020 wuxia.gd.cn All right reserved.
 */
package cn.wuxia.project.basic.support;

import cn.wuxia.common.util.StringUtil;
import cn.wuxia.project.basic.core.conf.support.DTools;
import cn.wuxia.project.common.support.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class DConstants extends Constants {

    protected final static Logger logger = LoggerFactory.getLogger(Functions.class);

    public final static String ADMIN_SERVER_NAME = "e_website_admin_service";

    public final static String API_SERVER_NAME = "e_website_api_service";

    public final static String SPRINGMVC_INITPARAMS = "springmvc.ctx";

    public final static String AUTHENTICATION_HEADER_NAME = "API-Authentication";

    public final static String AUTHENTICATION_TIME_PARAMETER_NAME = "API-Authentication-Time";

    public final static String BING_MP_WITHAPPLY = "绑定提现";

    public final static String KEFU_MP = "人工客服";
    /**
     * 日志级别字典值
     */
    public final static String LOGGER_LEVEL = "logger.level";

    public final static String WECHAT_LINE = "\r\n";

    public final static String WECHAT_2LINE = "\r\n\r\n";
    @Deprecated
    public final static String CTX_ADMIN_SERVICE_NAME = ApplicationPropertiesUtil.getPropertiesValue(ADMIN_SERVER_NAME);


    private static String getServerName(String key, String platform) {
        String serviceName = DTools.dic(key, platform);
        if (StringUtil.isBlank(serviceName)) {
            return DTools.dic(key);
        }
        return serviceName;
    }

    /**
     * @return
     */
    public static String getPlatform() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String currentPlatform = request.getHeader(Constants.HEADER_PLATFORM_NAME);
        logger.info("platform:{}", currentPlatform);
        return currentPlatform;
    }

    public static String adminServerName(String platform) {
        return getServerName(ADMIN_SERVER_NAME, platform);
    }


}
