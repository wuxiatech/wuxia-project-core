package cn.wuxia.project.basic.api;

import cn.wuxia.project.basic.mvc.annotation.ApiAuthorizedType;

public abstract class ApiAuthorizeImpl implements ApiAuthorize {

    public abstract ApiAuthorizedType getAuthorizedType();
}
