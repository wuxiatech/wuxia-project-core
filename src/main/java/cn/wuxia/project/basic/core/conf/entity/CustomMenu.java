
package cn.wuxia.project.basic.core.conf.entity;

import cn.wuxia.project.basic.core.conf.enums.MenuTypeEnum;
import org.springframework.data.mongodb.core.mapping.Document;

import cn.wuxia.project.common.model.ModifyInfoMongoEntity;

@Document(collection = "custom_menu")
public class CustomMenu extends ModifyInfoMongoEntity {
    private static final long serialVersionUID = 1L;

    private String url;

    private String name;

    private String description;

    private String eventKey;

    private MenuTypeEnum type;

    private String icon;

    private String parentId;

    private Integer sortOrder;

    public CustomMenu() {
        super();
    }

    public CustomMenu(String id) {
        super();
        super.setId(id);
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

    public MenuTypeEnum getType() {
        return type;
    }

    public void setType(MenuTypeEnum type) {
        this.type = type;
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
}
