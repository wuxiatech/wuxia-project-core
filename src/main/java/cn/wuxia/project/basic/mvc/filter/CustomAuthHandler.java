package cn.wuxia.project.basic.mvc.filter;

import cn.wuxia.common.exception.AppSecurityException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CustomAuthHandler {
    public boolean handlerCustom(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
