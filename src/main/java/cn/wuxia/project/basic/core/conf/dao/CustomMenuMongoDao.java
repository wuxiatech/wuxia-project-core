package cn.wuxia.project.basic.core.conf.dao;

import java.util.List;

import cn.wuxia.project.basic.core.common.BaseCommonMongoDao;
import cn.wuxia.project.basic.core.conf.entity.CustomMenu;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class CustomMenuMongoDao extends BaseCommonMongoDao<CustomMenu, String> {

    public List<CustomMenu> findByParentId(String parentMenuId) {
        return findBy("parentId", parentMenuId, "sortOrder", Sort.Direction.ASC);
    }

    public void deleteByParentId(String parentMenuId){
        super.delete(Query.query(Criteria.where("parentId").is(parentMenuId)));
    }
}
