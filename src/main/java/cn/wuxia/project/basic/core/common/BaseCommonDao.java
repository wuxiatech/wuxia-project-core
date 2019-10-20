package cn.wuxia.project.basic.core.common;

import cn.wuxia.project.common.dao.CommonDao;
import cn.wuxia.project.common.model.BaseUuidEntity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * 基础Dao。
 * 
 * @author songlin.li
 * @since 2013-6-19
 */
public abstract class BaseCommonDao<T extends BaseUuidEntity, PK extends Serializable> extends CommonDao<T, PK> {

    @Override
    @Autowired
    public void setSessionFactory(final SessionFactory baseSessionFactory) {
        this.sessionFactory = baseSessionFactory;
    }
}
