package cn.wuxia.project.basic.core.conf.enums;

import cn.wuxia.common.util.StringUtil;

/**
 * 广告加载方式枚举
 * 
 * @author songlin.li
 * @since 2014-06-30
 */
public enum AdLocationLoadTypeEnum {

    async, sync;


    private AdLocationLoadTypeEnum() {
    }

    public static AdLocationLoadTypeEnum get(String type) {
        for (AdLocationLoadTypeEnum e : AdLocationLoadTypeEnum.values()) {
            if (StringUtil.equals(e.name(), type)) {
                return e;
            }
        }
        return sync;
    }


}
