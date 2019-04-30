package cn.wuxia.project.basic.core.conf.enums;

/**
 * 自定义菜单动作类型
 */
public enum MenuTypeEnum {
	TEXT("文字"), URL("网址"), PICTURE("图片"), GRAPHIC("图文"),OTHER("其它");

	private String displayName;

	private MenuTypeEnum(String displayName) {

		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}

}
