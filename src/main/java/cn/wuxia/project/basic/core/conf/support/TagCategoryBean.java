package cn.wuxia.project.basic.core.conf.support;

import java.io.Serializable;
import java.util.List;

import cn.wuxia.project.basic.core.conf.entity.CustomTagCategory;

public class TagCategoryBean implements Serializable {

    private static final long serialVersionUID = 1L;

    String id;

    String name;

    String checktype;

    List<TagBean> tags;

    public TagCategoryBean() {
    }

    public TagCategoryBean(CustomTagCategory tagCategory, List<TagBean> tagBeans) {
        this.id = tagCategory.getId();
        this.name = tagCategory.getName();
        this.checktype = tagCategory.getChecktype();
        this.tags = tagBeans;
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

    public String getChecktype() {
        return checktype;
    }

    public void setChecktype(String checktype) {
        this.checktype = checktype;
    }

    public List<TagBean> getTags() {
        return tags;
    }

    public void setTags(List<TagBean> tags) {
        this.tags = tags;
    }
}
