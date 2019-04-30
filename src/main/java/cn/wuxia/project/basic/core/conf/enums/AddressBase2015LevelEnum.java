/*
 */
package cn.wuxia.project.basic.core.conf.enums;

/**
 * level枚举
 * 
 * @author songlin.li
 * @since 2015-10-08
 */
public enum AddressBase2015LevelEnum {

    //"国家"
    NATION(0),
    // "省份"
    PROVINCE(1),
    //"地级市"
    CITY(2),
    //"区县"
    COUNTRY_DISTRICT(3);

    private int level;

    private AddressBase2015LevelEnum(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public static AddressBase2015LevelEnum get(int level) {
        for (AddressBase2015LevelEnum e : AddressBase2015LevelEnum.values()) {
            if (e.getLevel() == level) {
                return e;
            }
        }
        return null;
    }
}
