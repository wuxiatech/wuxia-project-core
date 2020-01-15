package cn.wuxia.project.basic.core.conf.entity;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;
import org.springframework.data.mongodb.core.mapping.Document;

import cn.wuxia.project.common.model.ModifyInfoMongoEntity;

/**
* The persistent class for the qa_custom_reply_tag database table.
* @author songlin.li
* @since 2017-04-27
*/
@Document(collection = "custom_tag")
public class CustomTag extends ModifyInfoMongoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String categoryId; // 标签分类 - 可为空

    private String categoryName; // 标签分类 - 可为空  

    private String tagName;

    public CustomTag() {
        super();
    }

    public CustomTag(String tagName, String categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.tagName = tagName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @Length(max = 32)
    @NotBlank
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
