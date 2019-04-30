package cn.wuxia.project.basic.core.conf.support;

import java.io.Serializable;

import cn.wuxia.project.basic.core.conf.entity.CustomTag;

public class TagBean implements Serializable {

    private static final long serialVersionUID = 1L;

    String id;

    String name;

    String category;

    public TagBean() {
    }

    public TagBean(CustomTag customTag) {
        this.id = customTag.getId();
        this.category = customTag.getCategoryName();
        this.name = customTag.getTagName();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
