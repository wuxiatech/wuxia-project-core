package cn.wuxia.project.basic.core.common;

import cn.wuxia.project.common.dao.CommonMongoDao;
import cn.wuxia.project.common.model.CommonMongoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.Serializable;

/**
 * 基础Dao。
 * 
 * @author songlin.li
 * @since 2013-6-19
 */
public abstract class BaseCommonMongoDao<T extends CommonMongoEntity, K extends Serializable> extends CommonMongoDao<T, K> {

    @Autowired
    @Override
    public void setMongoTemplate(MongoTemplate baseMongoTemplate) {
        super.setMongoTemplate(baseMongoTemplate);
    }
}
