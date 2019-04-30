package cn.wuxia.project.basic.core.conf.bean;

import java.io.Serializable;

import cn.wuxia.project.basic.core.conf.entity.CustomTag;

public class TagsinputBean implements Serializable {

    private static final long serialVersionUID = 1L;

    String value;

    String text;

    String continent;

    public TagsinputBean() {
    }

    public TagsinputBean(CustomTag customTag) {
        this.value = customTag.getId();
        this.continent = customTag.getCategoryName();
        this.text = customTag.getTagName();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }
}
