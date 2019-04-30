package cn.wuxia.project.basic.api;

import cn.wuxia.project.basic.mvc.annotation.ApiAuthorizedType;

import java.util.Map;

public interface ApiAuthorize {
    public Map<String, String> getApiAuthorizedHeader();

    public ApiAuthorizedType getAuthorizedType();
}
