package cn.wuxia.project.basic.core.conf.dao;

import java.util.List;

import cn.wuxia.project.basic.core.common.BaseCommonDao;
import cn.wuxia.project.basic.core.conf.entity.GenerateStaticPage;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

/**
 * GenerateStaticPage DAO Interface.
 * 
 * @author songlin.li
 * @since 2014-04-16
 */
@Component
public class GenerateStaticPageDao extends BaseCommonDao<GenerateStaticPage, String> {

    public List<GenerateStaticPage> findLikeSourceUrl(String keyword) {
        return find(Restrictions.like("sourceUrl", keyword), Restrictions.isNull("isObsoleteDate"));
    }
}
