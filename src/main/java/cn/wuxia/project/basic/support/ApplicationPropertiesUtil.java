/**
 * Copyright (c) 2005-2010 springside.org.cn Licensed under the Apache License,
 * Version 2.0 (the "License"); $Id: PropertiesUtils.java 1211 2010-09-10
 * 16:20:45Z calvinxiu $
 */
package cn.wuxia.project.basic.support;

import cn.wuxia.common.util.ArrayUtil;
import cn.wuxia.common.util.PropertiesUtils;
import cn.wuxia.common.util.StringUtil;
import cn.wuxia.project.basic.core.conf.support.DTools;
import jodd.props.Props;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.Properties;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Get Properties Util Tools
 *
 * @author songlin.li
 */
@Slf4j
public class ApplicationPropertiesUtil {


    // =========================开始静态获取properties的值，修改的properties重启后生效或者clean后生效=====================

    private static final String[] propertiesPaths = new String[]{"classpath:properties/application.properties", "classpath:application.properties"};
    private static Properties initProperties;

    private static Props props = new Props();

    static {
        log.info("初始化ApplicationPropertiesUtil");
        load(propertiesPaths);
        /**
         * 执行一个定时器，5分钟执行一次
         */
        dynamicLoad(propertiesPaths);
    }

    private static void load(String[] propertiesPaths) {
        initProperties = PropertiesUtils.loadProperties(propertiesPaths);
        props.load(initProperties);
    }
    /**
     * 执行一个定时器，5分钟执行一次
     */
    private static void dynamicLoad(String[] propertiesPaths) {
        ScheduledExecutorService mService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("app-prop-scheduler-pool-%d").daemon(true).build());
        mService.scheduleAtFixedRate(() -> {
            load(propertiesPaths);
        }, 60, 300, TimeUnit.SECONDS);
    }

    /**
     * 请使用
     *
     * @return
     * @author songlin
     */

    public static Properties getProperties() {
        return initProperties;
    }

    /**
     * 也可以使用@Value（不支持宏命令）标签获取application.properties的值
     * 本方法扩展获取数据字典中的值及宏命令
     *
     * @param key
     * @return
     * @author songlin
     * @see {@link #getValue(String, String...)}
     */
    @Deprecated
    public static String getPropertiesValue(String key) {
        try {


            if (getProperties().containsKey(key)) {
                String value = getProperties().getProperty(key);
                String[] keys = StringUtil.getTemplateKey(value);
                if (ArrayUtil.isNotEmpty(keys)) {
                    for (String k : keys) {
                        value = StringUtil.replaceKeysSimple(value, k, getProperties().getProperty(k));
                    }
                }
                return value;
            } else {
                return DTools.dic(key);
            }
        } catch (Exception e) {
            System.out.println("error:" + e.getMessage());
        }
        return null;
    }

    /**
     * 也可以使用@Value（不支持宏命令）标签获取application.properties的值
     * 本方法扩展获取数据字典中的值
     * <a>https://jodd.org/props/</a>
     *
     * @param key
     * @return
     * @author songlin
     */
    public static String getValue(String key, String... profiles) {
        if (getProperties().containsKey(key)) {
            String value = props.getValue(key, profiles);
            return value;
        } else {
            return DTools.dic(key);
        }
    }

    // =========================结束静态获取properties的值，修改的properties重启后生效或者clean后生效=====================


}
