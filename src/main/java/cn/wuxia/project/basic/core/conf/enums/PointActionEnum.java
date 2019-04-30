package cn.wuxia.project.basic.core.conf.enums;

public enum PointActionEnum {
    open_invitation_register_url("打开邀请链接"),
    oauth_invitation_register_url("授权登录邀请链接"),
    open_invitation_register_page("打开邀请注册页"),
    submit_invitation_register("提交邀请注册");


    String displayName;

    PointActionEnum(String displayName) {
        this.displayName = displayName;
    }


    public String getDisplayName() {
        return displayName;
    }
}
