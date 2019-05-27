package cn.wuxia.project.basic.core.user.enums;

public enum UserOperationEnum {
    DEFAULT(""),  MASTER_CREATE("微信新增"),BINGREN_ZHUCE("病人注册"),
    //
    YISHENG_ZHUCE("医生注册"), DENGLU("登录系统"),
    //
    TUICHU("退出系统"), XIUGAIMIMA("修改密码"), ZILIAOXIUGAI("修改资料");

    private String displayValue;

    private UserOperationEnum(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

}
