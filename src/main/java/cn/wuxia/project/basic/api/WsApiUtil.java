package cn.wuxia.project.basic.api;

import cn.wuxia.common.util.StringUtil;
import cn.wuxia.project.common.api.ApiResponseBean;
import cn.wuxia.project.common.api.RequestBean;
import cn.wuxia.project.common.open.AppApiException;
import cn.wuxia.project.common.open.api.ApiUtil;
import cn.wuxia.project.common.support.Constants;
import com.google.common.collect.Maps;
import jodd.props.Props;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.Map;

/**
 * 本util用户请求 api接口的参数签名
 *
 * @author songlin
 */
@Deprecated
public class WsApiUtil extends ApiUtil {
    protected static Props props = new Props();

    public String APP_KEY;

    public String APP_SECRET;

    public String apiGateway;

    static {
        Resource resources = new PathMatchingResourcePatternResolver().getResource("/api.gateway.properties");
        try {
            props.load(resources.getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static WsApiUtil createDefault() {
        String APP_KEY = props.getValue("api.appkey");
        String APP_SECRET = props.getValue("api.appsecret");
        String apiGateway = props.getValue("api.gateway");
        return new WsApiUtil(APP_KEY, APP_SECRET, apiGateway);
    }

    public static WsApiUtil create(String profile) {
        logger.debug(profile);
        String APP_KEY = props.getValue("api.appkey", profile);
        String APP_SECRET = props.getValue("api.appsecret", profile);
        String apiGateway = props.getValue("api.gateway", profile);
        APP_KEY = StringUtil.isBlank(APP_KEY) ? profile : APP_KEY;
        return new WsApiUtil(APP_KEY, APP_SECRET, apiGateway);
    }

    public WsApiUtil key(String appkey) {
        logger.debug(appkey);
        this.APP_KEY = appkey;
        return this;
    }

    public WsApiUtil secret(String appsecret) {
        logger.debug(appsecret);
        this.APP_SECRET = appsecret;
        return this;
    }

    public WsApiUtil gateway(String gateway) {
        logger.debug(gateway);
        this.apiGateway = gateway;
        return this;
    }

    public WsApiUtil(String appKey, String appSecret, String apiGateway) {
        this.APP_KEY = appKey;
        this.APP_SECRET = appSecret;
        this.apiGateway = apiGateway;
        logger.debug("APP_KEY={};APP_SECRET={};apiGateway={}", APP_KEY, APP_SECRET, apiGateway);
    }

    /**
     * 使用全局的appkey， secret
     * 注意，本方法传递的参数param将被转为json
     *
     * @param param
     * @param method
     * @return
     * @author songlin
     */
    public ApiResponseBean post2Gateway(Object param, WsApiMethodEnum method) throws AppApiException {
        Assert.notNull(APP_KEY, "APP_KEY不能为空，请先调用key方法");
        String servicename = apiGateway;
        /**
         * 支持使用数据字典值
         */
        if (!StringUtil.startsWithIgnoreCase(apiGateway, "http")) {
            //servicename = DTools.dic(apiGateway);
        }
        Map<String, String> headers = Maps.newHashMap();
        headers.put(Constants.HEADER_APPID_NAME, APP_KEY);
        ApiResponseBean callbackBean = post2Gateway(new RequestBean(method.getMethodName(), param, method.getVersion()), servicename, headers, APP_KEY,
                APP_SECRET);
        logger.debug("return type : {}", callbackBean.getResultType());
        return callbackBean;
    }

    public ApiResponseBean post2Gateway(Object param, String method) throws AppApiException {
        Assert.notNull(APP_KEY, "APP_KEY不能为空");
        String servicename = apiGateway;
        /**
         * 支持使用数据字典值
         */
        if (!StringUtil.startsWithIgnoreCase(apiGateway, "http")) {
            //servicename = DTools.dic(apiGateway);
        }

        Map<String, String> headers = Maps.newHashMap();
        headers.put(Constants.HEADER_APPID_NAME, APP_KEY);
        ApiResponseBean callbackBean = post2Gateway(new RequestBean(method, param, ""), servicename, headers, APP_KEY, APP_SECRET);
        logger.debug("return type : {}", callbackBean.getResultType());
        return callbackBean;
    }

    /**
     * 使用全局的appkey， secret
     * 注意，本方法传递的参数param将被转为json
     *
     * @param method
     * @return
     * @author songlin
     */
    public ApiResponseBean post2Gateway(WsApiMethodEnum method) throws AppApiException {
        return post2Gateway(null, method);
    }

    public ApiResponseBean post2Gateway(String method) throws AppApiException {
        return post2Gateway(null, method);
    }
}
