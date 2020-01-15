package cn.wuxia.project.basic.core.conf.entity;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;
import org.springframework.data.mongodb.core.mapping.Document;

import cn.wuxia.project.common.model.ModifyInfoMongoEntity;

/**
 * The persistent class for the qa_custom_reply_tag database table.
 *
 * @author songlin.li
 * @since 2017-04-27
 */
@Document(collection = "custom_tag_category")
public class CustomTagCategory extends ModifyInfoMongoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name; // 标签分类 - 不可为空

    private String code;

    private String checktype;

    private TagCategoryType type;

    /**
     * 开启允许查询
     */
    private Boolean openquery;

    public enum TagCategoryType {
        custom, dic
    }

    public CustomTagCategory() {
        super();
    }

    public CustomTagCategory(String id) {
        super();
        this.id = id;
    }

    public CustomTagCategory(String code, String name) {
        super();
        this.code = code;
        this.name = name;
        this.openquery = true;
        this.type = TagCategoryType.dic;
    }

    public CustomTagCategory(String code, String name, TagCategoryType tagCategoryType) {
        super();
        this.code = code;
        this.name = name;
        this.openquery = true;
        this.type = tagCategoryType;
    }

    @Length(max = 32)
    @NotBlank
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotBlank
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getChecktype() {
        return checktype;
    }

    public void setChecktype(String checktype) {
        this.checktype = checktype;
    }

    public Boolean getOpenquery() {
        return openquery;
    }

    public void setOpenquery(Boolean openquery) {
        this.openquery = openquery;
    }

    @NotNull
    public TagCategoryType getType() {
        return type;
    }

    public void setType(TagCategoryType type) {
        this.type = type;
    }
}
