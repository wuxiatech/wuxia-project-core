/*
* Created on :2016年5月26日
* Author     :Administrator
* Change History
* Version       Date         Author           Reason
* <Ver.No>     <date>        <who modify>       <reason>
* Copyright 2014-2020 wuxia.tech All right reserved.
*/
package cn.wuxia.project.basic.core.conf.dao;

import java.util.List;

import cn.wuxia.project.basic.core.common.BaseCommonMongoDao;
import cn.wuxia.project.basic.core.conf.entity.CustomTagCategory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class CustomTagCategoryMongoDao extends BaseCommonMongoDao<CustomTagCategory, String> {

    public List<CustomTagCategory> findRootCategory() {
        Criteria criteria = new Criteria();
        criteria.orOperator(Criteria.where("parentId").is(null), Criteria.where("parentId").is(""));
        Query query = Query.query(criteria);
        return find(query);
    }
}
