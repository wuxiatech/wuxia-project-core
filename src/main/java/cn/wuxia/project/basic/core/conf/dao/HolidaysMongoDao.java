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
import cn.wuxia.project.basic.core.conf.entity.DicHolidays;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HolidaysMongoDao extends BaseCommonMongoDao<DicHolidays, String> {

    /**
     * 按照date 格式（0101）查询
     * @author songlin
     * @param date
     * @return
     */
    public List<DicHolidays> findByDate(String date) {
        return findBy("dateindex", date, "dateindex", Sort.Direction.ASC);
    }

    /**
     * 按照date 格式（0101）查询
     * @author songlin
     * @param date
     * @return
     */
    public List<DicHolidays> findByDate(String date, boolean isLunar) {
        return find(Query.query(Criteria.where("dateindex").is(date).and("lunar").is(isLunar)));
    }
}
