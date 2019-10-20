/*
* Created on :2016年5月26日
* Author     :Administrator
* Change History
* Version       Date         Author           Reason
* <Ver.No>     <date>        <who modify>       <reason>
* Copyright 2014-2020 wuxia.tech All right reserved.
*/
package cn.wuxia.project.basic.core.conf.dao;

import cn.wuxia.project.basic.core.common.BaseCommonMongoDao;
import cn.wuxia.project.basic.core.conf.entity.CustomTag;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class CustomTagMongoDao extends BaseCommonMongoDao<CustomTag, String> {
    /**
     * 根据categoryid删除标签
     *
     * @param categoryid
     */
    public void deleteByCategoryId(String categoryid) {
        Query query = Query.query(Criteria.where("categoryId").is(categoryid));
        super.getMongoTemplate().remove(query, CustomTag.class);
    }
}
