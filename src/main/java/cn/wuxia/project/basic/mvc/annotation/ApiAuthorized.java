/*
 * Created on :2015年10月30日
 * Author     :songlin
 * Change History
 * Version       Date         Author           Reason
 * <Ver.No>     <date>        <who modify>       <reason>
 * Copyright 2014-2020 wuxia.tech All right reserved.
 */
package cn.wuxia.project.basic.mvc.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
//@Inherited
@Documented
public @interface ApiAuthorized {
    @AliasFor("type")
    ApiAuthorizedType value() default ApiAuthorizedType.OPEN_TYPE;

    /**
     * 默认开放式访问
     *
     * @return
     */
    @AliasFor("value")
    ApiAuthorizedType type() default ApiAuthorizedType.OPEN_TYPE;

    String remark() default "";

    String conditions() default "";

    String unless() default "";


}
