/*
* Created on :2017年3月17日
* Author     :songlin
* Change History
* Version       Date         Author           Reason
* <Ver.No>     <date>        <who modify>       <reason>
* Copyright 2014-2020 wuxia.tech All right reserved.
*/
package cn.wuxia.project.basic.api;

@Deprecated
public enum WsApiMethodEnum {


    /**
     * 尝试拉取获取用户
     */
    pullContact("pullContact", "V1"),

    releaseMenu("releaseMenu", "V1");

    private String methodName;

    private String version;

    WsApiMethodEnum(String methodName, String version) {
        this.methodName = methodName;
        this.version = version;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getVersion() {
        return version;
    }

}
