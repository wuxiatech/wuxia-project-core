package cn.wuxia.project.basic.mvc.filter;

import cn.wuxia.common.exception.AppSecurityException;

public interface EnhancementAuthLoginService {
    public void validationAuthLogin(String userName, String password) throws AppSecurityException;
}
