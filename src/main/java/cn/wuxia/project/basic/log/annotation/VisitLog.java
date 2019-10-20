/*
* Created on :2015年10月30日
* Author     :songlin
* Change History
* Version       Date         Author           Reason
* <Ver.No>     <date>        <who modify>       <reason>
* Copyright 2014-2020 wuxia.tech All right reserved.
*/
package cn.wuxia.project.basic.log.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.wuxia.project.basic.log.enums.OperationLogTypeEnum;

@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface VisitLog {
    public OperationLogTypeEnum type() default OperationLogTypeEnum.DEFAULT;

    public String value() default "";

    public String remark() default "";

    public String conditions() default "";

    public String unless() default "";
}
