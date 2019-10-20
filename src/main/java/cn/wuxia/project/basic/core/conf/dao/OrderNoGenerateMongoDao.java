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
import cn.wuxia.project.basic.core.conf.entity.OrderNoGenerate;
import org.springframework.stereotype.Repository;

@Repository
public class OrderNoGenerateMongoDao extends BaseCommonMongoDao<OrderNoGenerate, String> {

    /**
     * 按照code查询
     * @author songlin
     * @param parentid
     * @return
     */
    public OrderNoGenerate findByCode(String code) {
        return findUniqueBy("code", code);
    }

}
