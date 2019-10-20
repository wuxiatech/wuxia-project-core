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
import cn.wuxia.project.basic.core.conf.entity.SystemDictionary;
import cn.wuxia.common.util.ListUtil;
import cn.wuxia.common.util.StringUtil;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SystemDictionaryMongoDao extends BaseCommonMongoDao<SystemDictionary, String> {

    /**
     * 按照分组查询
     *
     * @param parentid
     * @return
     * @author songlin
     */
    public List<SystemDictionary> findByParent(String parentid) {

        if (StringUtil.isBlank(parentid)) {
            Query query = new Query(Criteria.where("parentid").is(null));
            return find(query);
        }
        Query query = new Query(Criteria.where("parentid").is(parentid));
        return find(query);
    }

    /**
     * 父code 及 字code查唯一
     *
     * @param code
     * @param  parentCode
     * @return
     * @author songlin
     */
    public SystemDictionary findOneByCodeAndParentCode(String code, String parentCode) {

        Query query = new Query(Criteria.where("code").is(parentCode));
        List<SystemDictionary> parents = find(query);
        Criteria criteria = Criteria.where("code").is(code);
        if (ListUtil.isNotEmpty(parents) && parents.size() == 1) {
            String parentid = parents.get(0).getId();
            if (StringUtil.isNotBlank(parentid)) {
                criteria = criteria.and("parentid").is(parentid);
            }
        }
        Query query2 = new Query(criteria);
        List<SystemDictionary> dictionaries = find(query2);
        if (ListUtil.isNotEmpty(dictionaries)) {
            return dictionaries.get(0);
        } else {
            return null;
        }
    }
}
