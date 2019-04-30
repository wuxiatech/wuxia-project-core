/*
 * Created on :2015年10月30日
 * Author     :songlin
 * Change History
 * Version       Date         Author           Reason
 * <Ver.No>     <date>        <who modify>       <reason>
 * Copyright 2014-2020 www.ibmall.cn All right reserved.
 */
package cn.wuxia.project.basic.mvc.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
//@Inherited
@Documented
public @interface ApiAuthorized {

    /**
     * 默认开放式访问
     * @return
     */
    public ApiAuthorizedType type() default ApiAuthorizedType.OPEN_TYPE;

    public String remark() default "";

    public String conditions() default "";

    public String unless() default "";
}
