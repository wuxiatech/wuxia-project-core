/*
* Created on :2016年5月26日
* Author     :Administrator
* Change History
* Version       Date         Author           Reason
* <Ver.No>     <date>        <who modify>       <reason>
* Copyright 2014-2020 www.ibmall.cn All right reserved.
*/
package cn.wuxia.project.basic.core.conf.dao;

import cn.wuxia.project.basic.core.common.BaseCommonMongoDao;
import cn.wuxia.project.basic.core.conf.entity.CustomTagGroup;
import cn.wuxia.common.util.MapUtil;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class CustomTagGroupMongoDao extends BaseCommonMongoDao<CustomTagGroup, String> {

    public List<CustomTagGroup> findByFilter(Map<String, String> filter) {
        Query query = new Query();
        if (MapUtil.isNotEmpty(filter)) {
            for (Map.Entry<String, String> fit : filter.entrySet()) {
                query.addCriteria(Criteria.where("filters." + fit.getKey()).in(fit.getValue()));
            }
        }
        return find(query);
    }

}
