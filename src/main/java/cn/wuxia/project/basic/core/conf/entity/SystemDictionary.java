package cn.wuxia.project.basic.core.conf.entity;

import java.util.Date;
import java.util.HashMap;

import org.springframework.data.mongodb.core.mapping.Document;

import com.google.common.collect.Maps;

import cn.wuxia.project.common.model.ModifyInfoMongoEntity;
import cn.wuxia.common.util.StringUtil;

@Document(collection = "system_dictionary")
public class SystemDictionary extends ModifyInfoMongoEntity {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    private String code;

    private String name;

    private String value;

    private String type;

    private String parentid;

    private Date starttime;

    private Date expirytime;

    public SystemDictionary() {
    }

    public SystemDictionary(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getExpirytime() {
        return expirytime;
    }

    public void setExpirytime(Date expirytime) {
        this.expirytime = expirytime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, String> hashMap() {
        HashMap<String, String> m = Maps.newHashMap();
        if (StringUtil.isNotBlank(code) && StringUtil.isNotBlank(value)) {
            m.put(code, value);
        }
        return m;
    }
}
