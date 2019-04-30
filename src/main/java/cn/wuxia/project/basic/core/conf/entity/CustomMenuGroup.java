
package cn.wuxia.project.basic.core.conf.entity;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import cn.wuxia.project.common.model.ModifyInfoMongoEntity;

@Document(collection = "custom_menu_group")
public class CustomMenuGroup extends ModifyInfoMongoEntity {
    private static final long serialVersionUID = 1L;

    private String groupName;

    private String groupCode;

    private List<CustomMenu> menus;

    public CustomMenuGroup() {
        super();
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public List<CustomMenu> getMenus() {
        return menus;
    }

    public void setMenus(List<CustomMenu> menus) {
        this.menus = menus;
    }
}
