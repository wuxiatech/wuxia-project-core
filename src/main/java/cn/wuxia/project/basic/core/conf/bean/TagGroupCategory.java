package cn.wuxia.project.basic.core.conf.bean;

import java.io.Serializable;

public class TagGroupCategory implements Serializable {

    private static final long serialVersionUID = 1745315455148194114L;

    String categoryId;

    String categoryCode;

    String categoryName;

    public TagGroupCategory(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
