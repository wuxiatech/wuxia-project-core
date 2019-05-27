package cn.wuxia.project.basic.api;

import cn.wuxia.common.mapper.JacksonMapper;
import cn.wuxia.common.util.ArrayUtil;
import cn.wuxia.common.util.JsonUtil;
import cn.wuxia.common.util.MapUtil;
import cn.wuxia.common.util.StringUtil;
import cn.wuxia.common.web.httpclient.*;
import cn.wuxia.project.basic.support.DConstants;
import cn.wuxia.project.common.api.ApiRequestBean;
import cn.wuxia.project.common.api.ApiResponseBean;
import cn.wuxia.project.common.support.Constants;
import com.google.common.collect.Maps;
import org.apache.http.entity.ByteArrayEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.Map;

/**
 * 本util用户请求 api接口的参数签名
 *
 * @author songlin
 */
public class RestApiUtil {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private String appid;

    private Map<String, Object> params;

    private byte[] content;

    private String json;

    private String url;

    private HttpClientMethod method;

    private ApiAuthorize apiAuthorized;


    public RestApiUtil() {
    }

    RestApiUtil(String appid, String url, HttpClientMethod method) {
        this.appid = appid;
        this.url = url;
        this.method = method;
        this.params = Maps.newHashMap();
    }

    public static RestApiUtil create(String appid, String url, HttpClientMethod method) {
        return new RestApiUtil(appid, url, method);
    }

    public static RestApiUtil create(String url, HttpClientMethod method) {
        return new RestApiUtil(null, url, method);
    }

    public RestApiUtil authorized(ApiAuthorize authorized) {
        this.apiAuthorized = authorized;
        return this;
    }

    public RestApiUtil appid(String appid) {
        this.appid = appid;
        return this;
    }

    public RestApiUtil addParams(Map<String, Object> params) {
        if (MapUtil.isEmpty(params)) {
            return this;
        } else if (MapUtil.isEmpty(this.params)) {
            this.params = Maps.newHashMap();
        }
        this.params.putAll(params);
        return this;
    }

    public RestApiUtil addParam(String key, Object value) {
        if (MapUtil.isEmpty(params)) {
            params = Maps.newHashMap();
        }
        params.put(key, value);
        return this;
    }

    public RestApiUtil content(byte[] content) {
        this.content = content;
        return this;
    }

    public RestApiUtil toJson(Object object) {
        this.json = JsonUtil.toJson(object);
        return this;
    }

    /**
     * 使用全局的appkey， secret
     *
     * @return
     * @author songlin
     */
    public ApiResponseBean send() throws IOException, HttpClientException {

        Assert.notNull(appid, "appid不能为空");
        Assert.notNull(url, "url不能为空");
        Assert.notNull(method, "method不能为空");

        Map<String, String> headers = Maps.newHashMap();
        headers.put(Constants.HEADER_APPID_NAME, appid);
        if (apiAuthorized != null) {
            headers.putAll(apiAuthorized.getApiAuthorizedHeader());
        }
        logger.info("发送消息到 url:{}", url);
        HttpClientRequest request = HttpClientRequest.create().setMethod(this.method).setUrl(url).setHeader(headers).setParam(params).addParam(DConstants.AUTHENTICATION_TIME_PARAMETER_NAME, "" + System.currentTimeMillis());
        HttpClientResponse response = null;
        if (content != null) {
            logger.debug("参数为byte[]");
            response = HttpClientUtil.execute(request, new ByteArrayEntity(content));
        } else if (StringUtil.isNotBlank(json)) {
            logger.debug("参数为{}", json);
            response = HttpClientUtil.postJson(request, json);
        } else {
            logger.debug("参数为{}", params);
            response = HttpClientUtil.execute(request);
        }
        byte[] callback = response.getByteResult();
        if (ArrayUtil.isNotEmpty(callback)) {
            ApiResponseBean callbackBean = JacksonMapper.nonDefaultMapper().getMapper().readValue(callback, ApiResponseBean.class);
            logger.debug("callback={}", callbackBean);
            return callbackBean;
        } else {
            return ApiRequestBean.ok();
        }
    }

}
