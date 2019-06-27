package cn.wuxia.project.basic.mvc.filter;

import cn.wuxia.common.exception.AppSecurityException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class WeixinAuthHandler {
    public abstract boolean handlerWeixinAuth(HttpServletRequest request, HttpServletResponse response) throws AppSecurityException;
}
