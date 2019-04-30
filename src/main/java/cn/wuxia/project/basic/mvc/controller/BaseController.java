package cn.wuxia.project.basic.mvc.controller;

import cn.wuxia.common.exception.AppPermissionException;
import cn.wuxia.common.util.BrowserUtils;
import cn.wuxia.common.util.DateUtil;
import cn.wuxia.common.util.ResourceBundleUtil;
import cn.wuxia.common.util.StringUtil;
import cn.wuxia.project.basic.core.conf.support.DTools;
import cn.wuxia.project.basic.support.ApplicationPropertiesUtil;
import cn.wuxia.project.basic.support.DConstants;
import cn.wuxia.project.common.security.UserContext;
import cn.wuxia.project.common.security.UserContextUtil;
import cn.wuxia.project.common.support.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaseController {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected HttpServletRequest request;

    /**
     * 需要在applicationContext-mvc.xml中引入properties才能在controller中使用@Value
     */
    //    @Value("${system.type}")
    protected String system = ApplicationPropertiesUtil.getPropertiesValue("system.type");

    /**
     * get Server HttpPath
     *
     * @return
     * @author songlin.li
     */
    public String getServerHttpPath() {
        String httpPath = request.getScheme() + "://" + request.getServerName();
        if (request.getServerPort() != 80 && request.getServerPort() != 443) {
            httpPath = httpPath + ":" + request.getServerPort();
        }
        return httpPath;
    }


    /**
     * 获取访问的ip
     *
     * @return
     * @author songlin
     */
    protected String getVisitorIp() {
        /*
         * String ip = SpringSecurityUtils.getCurrentUserIp(); if
         * (StringUtil.isBlank(ip) || StringUtil.equals(ip, "127.0.0.1") ||
         * StringUtil.equals(ip, "0:0:0:0:0:0:0:1")) {
         */
        String ip = request.getRemoteAddr();
        // }
        logger.info("************Current visitor ip:" + ip);
        return ip;
    }


    /**
     * 获得I18N对应的翻译
     *
     * @author Yangpeilin
     */
    protected String getResourceString(String key) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Locale local = request.getLocale();
        return ResourceBundleUtil.getString(key, local);
    }

    /**
     * 临时解决方法，后续数据库createdBy,modifiedBy字段可以升级为utf8mb4
     * 当前是为了兼容旧数据库
     *
     * @param source
     * @return
     * @author songlin
     */
    public String filterEmoji(String source) {
        if (source != null) {
            Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                    Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
            Matcher emojiMatcher = emoji.matcher(source);
            if (emojiMatcher.find()) {
                source = emojiMatcher.replaceAll("*");
                return source;
            }
            return source;
        }
        return source;
    }

    /**
     * 判断是否是微信浏览器
     *
     * @return
     * @author songlin
     */
    protected boolean isWeiXin() {
        return BrowserUtils.isWeiXin(request);
    }

    /**
     * 临时获取当前使用的病人用户
     *
     * @return
     */
    protected UserContext getCurrentUser() {
        return UserContextUtil.getUserContext();
    }


    protected UserContext getLoginContext() {
        UserContext userContext = UserContextUtil.getUserContext();
        if (userContext == null) {
            throw new AppPermissionException();
        }
        return userContext;
    }

    protected String getWxAppid() {
        String wxappid = request.getHeader(Constants.HEADER_APPID_NAME);
        wxappid = StringUtil.isBlank(wxappid) ? DTools.dic(getPlatform()) : wxappid;
        logger.info("appid:{}", wxappid);
        request.setAttribute("appid", wxappid);
        return wxappid;
    }

    /**
     * @return
     */
    protected String getPlatform() {
        return DConstants.getPlatform();
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DateUtil.DateFormatter.FORMAT_YYYY_MM_DD.getFormat());
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
        // binder.registerCustomEditor(String.class, new StringEscapeEditor());
    }

}
