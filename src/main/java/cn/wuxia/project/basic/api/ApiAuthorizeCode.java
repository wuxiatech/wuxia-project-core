package cn.wuxia.project.basic.api;

import cn.wuxia.project.basic.mvc.annotation.ApiAuthorizedType;
import cn.wuxia.project.basic.support.DConstants;
import com.google.common.collect.Maps;

import java.util.Map;

public class ApiAuthorizeCode extends ApiAuthorizeImpl {
    String authorizedCode;

    @Override
    public ApiAuthorizedType getAuthorizedType() {
        return ApiAuthorizedType.AUTHENTICATION_CODE;
    }

    public ApiAuthorizeCode(String authorizedCode) {
        this.authorizedCode = authorizedCode;
    }

    @Override
    public Map<String, String> getApiAuthorizedHeader() {
        Map<String, String> headers = Maps.newHashMap();
        headers.put(DConstants.AUTHENTICATION_HEADER_NAME, this.authorizedCode);
        return headers;
    }
}
