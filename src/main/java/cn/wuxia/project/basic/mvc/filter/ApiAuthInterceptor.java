package cn.wuxia.project.basic.mvc.filter;

import cn.wuxia.common.exception.AppSecurityException;
import cn.wuxia.common.spring.SpringContextHolder;
import cn.wuxia.common.util.BrowserUtils;
import cn.wuxia.common.util.NumberUtil;
import cn.wuxia.common.util.ServletUtils;
import cn.wuxia.common.util.StringUtil;
import cn.wuxia.common.web.sign.SignUtil2;
import cn.wuxia.project.basic.mvc.annotation.ApiAuthorized;
import cn.wuxia.project.basic.mvc.annotation.ApiAuthorizedType;
import cn.wuxia.project.basic.support.ApplicationPropertiesUtil;
import cn.wuxia.project.basic.support.DConstants;
import cn.wuxia.project.common.third.ip.IPUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 拦截器
 *
 * @author songlin.li
 */
public class ApiAuthInterceptor implements HandlerInterceptor {
    private static Logger logger = LoggerFactory.getLogger(ApiAuthInterceptor.class);

    private EnhancementAuthLoginService enhancementAuthLoginService;

    private CustomAuthHandler customAuthHandler;

    private WeixinAuthHandler weixinAuthHandler;

    private EnhancementAuthLoginService getAuthLoginUserService() {
        if (enhancementAuthLoginService == null) {
            enhancementAuthLoginService = SpringContextHolder.getBean(EnhancementAuthLoginService.class);
        }
        return enhancementAuthLoginService;
    }

    private CustomAuthHandler getCustomAuthHandler() {
        if (customAuthHandler == null) {
            customAuthHandler = SpringContextHolder.getBean(CustomAuthHandler.class);
        }
        return customAuthHandler;
    }

    private WeixinAuthHandler getWeixinAuthHandler() {
        if (weixinAuthHandler == null) {
            weixinAuthHandler = SpringContextHolder.getBean(WeixinAuthHandler.class);
        }
        return weixinAuthHandler;
    }

    /**
     * preHandle方法是进行处理器拦截用的，顾名思义，该方法将在Controller处理之前进行调用，SpringMVC中的Interceptor拦截器是链式的，可以同时存在
     * 多个Interceptor，然后SpringMVC会根据声明的前后顺序一个接一个的执行，而且所有的Interceptor中的preHandle方法都会在
     * Controller方法调用之前调用。SpringMVC的这种Interceptor链式结构也是可以进行中断的，这种中断方式是令preHandle的返
     * 回值为false，当preHandle的返回值为false的时候整个请求就结束了。
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            synchronized (this) {
                String uri = request.getRequestURI();
                String url = request.getRequestURI() + (StringUtil.isBlank(request.getQueryString()) ? "" : "?" + request.getQueryString());
                HandlerMethod method = (HandlerMethod) handler;
                RequestMapping mapping = method.getMethodAnnotation(RequestMapping.class);
                ApiAuthorized annotationOnMethod = method.getMethodAnnotation(ApiAuthorized.class);

                if (annotationOnMethod != null) {
                    logger.info("请求来源：{}  请求地址：{}", BrowserUtils.checkBrowse(request), url);
                    ApiAuthorizedType authorizedType = annotationOnMethod.type();
                    switch (authorizedType) {

                        case NORMAL_LOGIN:
                            return handleLogin(request);
                        case AUTHENTICATION_CODE:
                            return handleAuthenticationCode(request);
                        case BLACK_WHITE_IP:
                            return handleBlackWhiteIp(request);
                        case WEIXIN_AUTH:
                            return handlerWeixinAuth(request, response);
                        case CUSTOM:
                            return handleCustom(request, response);
                        default:
                            break;
                    }
                } else if (StringUtil.startsWith(uri, "/api/")) {
                    /**
                     * 如果请求为API，则需要验证header授权
                     */
                    check(request, response);
                }
            }
        }
        return true;
    }


    /**
     * 这个方法只会在当前这个Interceptor的preHandle方法返回值为true的时候才会执行。postHandle是进行处理器拦截用的，它的执行时间是在处理器进行处理之
     * 后，也就是在Controller的方法调用之后执行，但是它会在DispatcherServlet进行视图的渲染之前执行，也就是说在这个方法中你可以对ModelAndView进行操
     * 作。这个方法的链式结构跟正常访问的方向是相反的，也就是说先声明的Interceptor拦截器该方法反而会后调用，这跟Struts2里面的拦截器的执行过程有点像，
     * 只是Struts2里面的intercept方法中要手动的调用ActionInvocation的invoke方法，Struts2中调用ActionInvocation的invoke方法就是调用下一个Interceptor
     * 或者是调用action，然后要在Interceptor之前调用的内容都写在调用invoke之前，要在Interceptor之后调用的内容都写在调用invoke方法之后。
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }


    public final String AUTHENTICATION_LOGIN_NAME = "API_AUTH_LOGIN";

    /**
     * 访问api的黑名单
     */
    public final String BLACKLIST_NAME = "api_blacklist";

    /**
     * 访问api的白名单
     */
    public final String WHITELIST_NAME = "api_whitelist";

    /**
     * 授权登录
     *
     * @return
     * @throws IOException
     * @author songlin.li
     */
    public void check(HttpServletRequest request, HttpServletResponse response) throws IOException {

        boolean handleResule = handleBlackWhiteIp(request);
        if (handleResule) return;

        handleResule = handleAuthenticationCode(request);
        if (handleResule) return;

        handleResule = handleLogin(request);
        if (handleResule) return;
    }


    private boolean handleBlackWhiteIp(HttpServletRequest request) throws AppSecurityException {
        String[] requestIps = StringUtil.split(ServletUtils.getRemoteIP(request), ",");
        String requestIp = requestIps[0];
        logger.info("请求api需要验证身份信息，IP：{}，Content-Type：{}，Orign:{}, Referer：{}", ServletUtils.getRemoteIP(request), request.getHeader("Content-Type"),
                request.getHeader("Origin"), request.getHeader("Referer"));

        String requestDomain = StringUtil.substringBetween(request.getHeader("Referer"), "//", "/");
        /**
         * 获取访问黑名单
         */
        String blacklist = ApplicationPropertiesUtil.getPropertiesValue(BLACKLIST_NAME);
        if (StringUtil.isNotBlank(blacklist)) {
            String[] blacklists = blacklist.split(",|\\s+|;");
            for (String blackip : blacklists) {
                if (StringUtil.indexOf(blackip, "-") > 0) {
                    boolean ipInBlackRand = IPUtil.existsInRange(requestIp, blackip);
                    if (ipInBlackRand) {
                        throw new AppSecurityException("黑名单ip[{}]无权访问", requestIp);
                    }
                } else if (StringUtil.equals(requestIp, blackip)) {
                    throw new AppSecurityException("黑名单ip[{}]无权访问", requestIp);
                }

                if (StringUtil.indexOf(requestDomain, blackip) >= 0) {
                    throw new AppSecurityException("黑名单域名[{}]无权访问", requestDomain);
                }

            }
        }
        /**
         * 获取访问白名单
         */
        String whitelist = ApplicationPropertiesUtil.getPropertiesValue(WHITELIST_NAME);
        if (StringUtil.isNotBlank(whitelist)) {
            String[] whitelists = whitelist.split(",|\\s+|;");
            for (String whiteip : whitelists) {
                if (StringUtil.indexOf(whiteip, "-") > 0) {
                    boolean ipInWhiteRand = IPUtil.existsInRange(requestIp, whiteip);
                    if (ipInWhiteRand) {
                        logger.info("白名单ip[{}]直接放行", requestIp);
                        return true;
                    }
                } else if (ArrayUtils.contains(whitelists, StringUtil.trim(requestIp))) {
                    logger.info("白名单ip[{}]直接放行", requestIp);
                    return true;
                }
                if (StringUtil.indexOf(requestDomain, whiteip) >= 0) {
                    logger.info("白名单域名[{}]直接放行", requestDomain);
                    return true;
                }
            }
        }
        return false;
    }


    private boolean handleLogin(HttpServletRequest request) throws AppSecurityException {
        String validAuthentication = request.getHeader(AUTHENTICATION_LOGIN_NAME);
        byte[] authcode = Base64.decodeBase64(validAuthentication);
        String userinfo = new String(authcode);
        String[] userinfos = StringUtil.split(userinfo, "|");


        /**
         * TODO
         * 简单的对比，先不做登录验证
         */
        if (ArrayUtils.isNotEmpty(userinfos) && StringUtil.isNotBlank(userinfos[0]) && StringUtil.isNotBlank(userinfos[1])) {
            logger.debug("request paramter:{}", request.getQueryString());
            getAuthLoginUserService().validationAuthLogin(userinfos[0], userinfos[1]);
            return true;
        } else {
            throw new AppSecurityException("无效登录，请重新登录");
        }
    }

    private boolean handleAuthenticationCode(HttpServletRequest request) throws AppSecurityException {
        String origin = request.getHeader("Origin");
        String ajaxRequest = request.getHeader("X-Requested-With");
        /**
         * 如果为ajax的本网站api请求，则放行
         */
        if (StringUtil.isNotBlank(origin) && StringUtil.equalsIgnoreCase(ajaxRequest, "XMLHttpRequest")) {
            if (StringUtil.startsWith(request.getHeader("Referer"), origin)) {
                return true;
            }
        }

        String authentication = request.getHeader(DConstants.AUTHENTICATION_HEADER_NAME);
        String authenticationTime = request.getParameter(DConstants.AUTHENTICATION_TIME_PARAMETER_NAME);
        logger.warn(DConstants.AUTHENTICATION_HEADER_NAME + "={}," + DConstants.AUTHENTICATION_TIME_PARAMETER_NAME + "={}", authentication, authenticationTime);
        /**
         * FIXME APPKEY需要存储数据看, 不对外开放
         */
        SortedMap<String, Object> requestParams = new TreeMap<String, Object>(ServletUtils.getParametersMap(request));
        logger.info("{}", requestParams);
        /**
         * FIXME 暂没有处理
         */
        if (StringUtil.isBlank(authentication) || StringUtil.isBlank(authenticationTime)) {
            throw new AppSecurityException("授权码错误");
        }
        /**
         * TODO 授权码处理
         */
        String appkey = ApplicationPropertiesUtil.getValue(authentication);
        if (StringUtil.isNotBlank(appkey)) {
            long sendtime = NumberUtil.toLong(authenticationTime);
            long nowtime = System.currentTimeMillis();
            logger.info("sendtime:{}, nowtime:{}", sendtime, nowtime);
            if (nowtime - sendtime > 5000) {
                throw new AppSecurityException("过期请求");
            }


            String serverSign = SignUtil2.createSign(appkey, requestParams);
            String clientSign = MapUtils.getString(requestParams, "sign");
            boolean validsign = StringUtil.equals(serverSign, clientSign);
            if (!validsign) {
                throw new AppSecurityException("非法参数");
            } else {
                return true;
            }
        } else {
            System.out.println(ApplicationPropertiesUtil.getProperties());
            throw new AppSecurityException("非法请求");
        }
    }

    private boolean handlerWeixinAuth(HttpServletRequest request, HttpServletResponse response) throws AppSecurityException {
        return getWeixinAuthHandler().handlerWeixinAuth(request, response);
    }

    private boolean handleCustom(HttpServletRequest request, HttpServletResponse response) throws AppSecurityException {
        try {
            return getCustomAuthHandler().handlerCustom(request, response);
        } catch (Exception e) {
            throw new AppSecurityException("非法请求", e.getCause());
        }
    }

}
