/*
 * Created on :2015年10月30日
 * Author     :songlin
 * Change History
 * Version       Date         Author           Reason
 * <Ver.No>     <date>        <who modify>       <reason>
 * Copyright 2014-2020 wuxia.tech All right reserved.
 */
package cn.wuxia.project.basic.log;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import cn.wuxia.project.basic.log.annotation.OperationLog;
import cn.wuxia.project.common.security.UserContext;
import cn.wuxia.project.common.security.UserContextUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.wuxia.common.spring.el.OperationBean;
import cn.wuxia.common.spring.el.OperationContext;
import cn.wuxia.common.spring.support.LogAspect;
import cn.wuxia.common.util.StringUtil;

/**
 * 切点类
 *
 * @author songlin.li
 * @version 1.0
 * @since 2015-10-30
 */
@Aspect
@Component
public class OperationLogAspect extends LogAspect {
    //注入Service用于把日志保存数据库  
    // @Resource
    //private UserOperationHistoryDao uohDao;

    //本地异常日志记录对象  
    private static final Logger logger = LoggerFactory.getLogger(OperationLogAspect.class);

    //Service层切点  
    @Pointcut("@annotation(cn.wuxia.project.basic.log.annotation.OperationLog)")
    public void serviceAspect() {
        logger.info(("用户操作日志，执行serviceAspect[" + new Date()) + "]");
    }

    //spring中Before通知
    @Before("serviceAspect()")
    public void doBefore() {
        logger.info(("用户操作日志，执行doBefore[" + new Date()) + "]");
    }

    @After("serviceAspect()") //spring中After通知
    public void doAfter() {
        logger.info("用户操作日志，执行doAfter[" + new Date() + "]");
    }

    @AfterReturning("serviceAspect()")
    public void doAfterReturning() {
        logger.info("用户操作日志，执行doAfterReturning[" + new Date() + "]");
    }

    @Around("serviceAspect()") //spring中Around通知
    public Object doAround(ProceedingJoinPoint joinPoint) {
        Object result = null;
        try {
            logger.info("用户操作日志，执行doAround开始[" + new Date() + "]");
            result = joinPoint.proceed(joinPoint.getArgs());
            logger.info("用户操作日志，执行doAround结束[" + new Date() + "]");
            logger.info("用户操作日志，在doAround执行doit开始[" + new Date() + "]");
            //doit(joinPoint);
            logger.info("用户操作日志，在doAround执行doit结束[" + new Date() + "]");
        } catch (Throwable e) {
            logger.error("用户操作日志，执行doAround有异常：" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 异常通知 用于拦截service层记录异常日志
     *
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(pointcut = "serviceAspect()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        logger.info("用户操作日志，执行doAfterThrowing[" + new Date() + "]");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        //读取session中的用户  
        //获取用户请求方法的参数并序列化为JSON格式字符串
        String params = "";
        if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
            logger.info(StringUtil.join(joinPoint.getArgs()));
        }
        UserContext userContext = UserContextUtil.getUserContext();
        try {
            /*========控制台输出=========*/
            logger.error("=====用户操作日志异常通知开始=====");
            logger.error("用户操作日志异常代码:" + e.getClass().getName());
            logger.error("用户操作日志异常信息:" + e.getMessage());
            logger.error("用户操作日志异常方法:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
            logger.error("用户操作日志请求人:" + userContext == null ? "" : userContext.getName());
            logger.error("用户操作日志请求IP:" + getIp());
            logger.error("用户操作日志请求参数:" + params);
            /*==========数据库日志=========*/

            //保存数据库  

            logger.info("=====用户操作日志异常通知结束=====");
        } catch (Exception ex) {
            //记录本地异常日志  
            logger.error("==用户操作日志异常通知异常==");
            logger.error("用户操作日志异常信息:{}", ex.getMessage());
        }
        /*==========记录本地异常日志==========*/
        logger.error("用户操作日志，异常方法：{}异常代码：{}异常信息：{}参数：{}", joinPoint.getTarget().getClass().getName() + joinPoint.getSignature().getName(),
                e.getClass().getName(), e.getMessage(), params);

    }

    /**
     * 获取注解中的参数值
     *
     * @param joinPoint 切点
     * @return 方法描述
     * @throws Exception
     */
    public void doit(JoinPoint joinPoint) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        Object value = "";
        OperationLog log = null;
        // UserOperationHistory uoh = null;
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    log = method.getAnnotation(OperationLog.class);
                    //uoh = genUserOperationHistory(log);
                    Class<?> targetClasss = AopProxyUtils.ultimateTargetClass(arguments[0]);
                    if (targetClasss == null && arguments[0] != null) {
                        targetClasss = arguments[0].getClass();
                    }
                    //注解代码开始
                    /*
                     * 以下代码为注解，eg: @OperationLog(operationUserId = "#userId")
                     * 则需要把对应的值设置到OperationBean的value，则可以通过getValue获取具体的值
                     */
                    OperationBean ob = new OperationBean();
                    ob.setValue(log.operationUserId());
                    OperationContext context = new OperationContext(ob, method, arguments, arguments[0], targetClasss);
                    value = context.getValue();
                    // uoh.setOperationUserId(value==null?null:value.toString());
                    //注解代码结束
                    break;
                }
            }
        }
        //此处可使用异步线程调用
        //saveLog(uoh);
    }

    /**
     * 把参数转为用户操作日志实体
     * @param log
     * @return 用户操作日志实体
     * @author Wind.Zhao
     * @date 2015/11/04
     */
    /*private UserOperationHistory genUserOperationHistory(OperationLog log) {
    	UserOperationHistory uoh = new UserOperationHistory();
    	UserOperationEnum uoEnum = log.operationType();
    	BeanUtil.copyProperties(uoh, log);
    	uoh.setRemark(uoEnum.getDisplayValue());
    	uoh.setOperationType(uoEnum.getType()==null?null:uoEnum.getType().toString());
        return uoh;
    }*/

    /**
     * 保存用户操作日志
     *
     * @param wxoh
     */
   /* private void saveLog(UserOperationHistory uoh) {
        MyUserDetails user = SpringSecurityUtils.getCurrentUser();
        if(null != user){
        	String mobile = user.getUsername();
        	uoh.setCreatedBy(mobile);
        	uoh.setModifiedBy(mobile);
        }
        uoh.setOperationIp(getIp());
        Timestamp now = new Timestamp((new Date()).getTime());
        uoh.setOperationDatetime(now);
        uoh.setCreatedOn(now);
        uoh.setModifiedOn(now);
        logger.info("用户操作日志信息：" + uoh.toString());
        //UserOperationHistoryDao userOperationHistoryDao = SpringContextHolder.getBean(UserOperationHistoryDao.class);
        uohDao.saveEntity(uoh);
    }*/
    @Override
    public String getIp() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (request != null) {
            return request.getRemoteAddr();
        }
        return "";
    }
}
