package cn.wuxia.project.basic.core.conf.bean;

import java.beans.Transient;
import java.util.List;

import cn.wuxia.project.basic.core.conf.entity.CustomMenu;
import cn.wuxia.project.basic.core.conf.enums.MenuTypeEnum;
import cn.wuxia.common.util.StringUtil;
import cn.wuxia.common.util.reflection.BeanUtil;

/**
 * The persistent class for the wx_menu database table.
 */
public class MenuBean {

    private String id;

    private String menuId;

    private String description;

    private String url;

    private String name;

    private String eventKey;

    private String type;

    private Integer sortOrder;

    private String icon;

    private String parentId;

    private List<MenuBean> subMenu;

    public MenuBean() {
    }

    public MenuBean(CustomMenu menu) {
        BeanUtil.copyProperties(this, menu);
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setType(MenuTypeEnum type) {
        this.type = type.name();
    }

    /**
     * type的枚举类型
     *
     * @return
     */
    public MenuTypeEnum getEnumType() {
        if (StringUtil.isBlank(this.type)) {
            return null;
        }
        return MenuTypeEnum.valueOf(this.type);
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    @Transient
    public List<MenuBean> getSubMenu() {
        return subMenu;
    }

    public void setSubMenu(List<MenuBean> subMenu) {
        this.subMenu = subMenu;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public void setId(String id) {
        this.id = id;
        this.menuId = id;
    }

}
