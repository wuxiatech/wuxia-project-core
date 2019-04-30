/*
* Created on :2015年10月30日
* Author     :songlin
* Change History
* Version       Date         Author           Reason
* <Ver.No>     <date>        <who modify>       <reason>
* Copyright 2014-2020 www.ibmall.cn All right reserved.
*/
package cn.wuxia.project.basic.log.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 
 * [ticket id]
 * <pre>
 * like:@OperationLog(operationUserId = "#user.getId()", operationType = UserOperationEnum.ZILIAOXIUGAI)
 * </pre>
 * @author songlin
 * @ Version : V<Ver.No> <2017年9月21日>
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface OperationLog {

    public String operationUserId() default "";

    public String operationDatetime() default "";

    public String operationIp() default "";
    
    public String serialNo() default "";
    
    public String remark() default "";
}
