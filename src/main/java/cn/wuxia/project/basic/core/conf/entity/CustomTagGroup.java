package cn.wuxia.project.basic.core.conf.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.mongodb.core.mapping.Document;

import cn.wuxia.project.basic.core.conf.bean.TagGroupCategory;
import cn.wuxia.project.common.model.ModifyInfoMongoEntity;

/**
 * The persistent class for the qa_custom_reply_tag database table.
 *
 * @author songlin.li
 * @since 2017-04-27
 */
@Document(collection = "custom_tag_group")
public class CustomTagGroup extends ModifyInfoMongoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name; // 标签分类 - 不可为空

    private String code;

    private Map<String, Object> filters;

    private List<TagGroupCategory> children;

    public CustomTagGroup() {
        super();
    }

    public CustomTagGroup(String groupName) {
        super();
        this.name = groupName;
    }

    @Length(max = 32)
    @NotBlank
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<TagGroupCategory> getChildren() {
        return children;
    }

    public void setChildren(List<TagGroupCategory> children) {
        this.children = children;
    }

    public Map<String, Object> getFilters() {
        return filters;
    }

    public void setFilters(Map<String, Object> filters) {
        this.filters = filters;
    }

}
