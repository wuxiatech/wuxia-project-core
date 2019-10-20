/*
 * Created on :2016年6月17日
 * Author     :songlin
 * Change History
 * Version       Date         Author           Reason
 * <Ver.No>     <date>        <who modify>       <reason>
 * Copyright 2014-2020 wuxia.tech All right reserved.
 */
package cn.wuxia.project.basic.core.conf.service.impl;

import cn.wuxia.common.exception.ValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import cn.wuxia.project.basic.core.conf.dao.OrderNoGenerateMongoDao;
import cn.wuxia.project.basic.core.conf.entity.OrderNoGenerate;
import cn.wuxia.project.basic.core.conf.service.OrderNoGenerateService;
import cn.wuxia.project.common.dao.CommonMongoDao;
import cn.wuxia.project.common.service.impl.CommonMongoServiceImpl;
import cn.wuxia.common.exception.AppServiceException;

@Service
public class OrderNoGenerateServiceImpl extends CommonMongoServiceImpl<OrderNoGenerate, String> implements OrderNoGenerateService {

    @Autowired
    OrderNoGenerateMongoDao orderNoGenerateMongoDao;

    @Override
    protected CommonMongoDao<OrderNoGenerate, String> getCommonDao() {
        return orderNoGenerateMongoDao;
    }

    @Override
    public long init(String code, long start, int step) {
        logger.info("code={}, start={}, step={}", code, start, step);
        OrderNoGenerate orderNoGenerate = orderNoGenerateMongoDao.findByCode(code);
        if (orderNoGenerate == null) {
            orderNoGenerate = new OrderNoGenerate(code, start, step);
            try {
                orderNoGenerateMongoDao.save(orderNoGenerate);
            } catch (ValidateException e) {
                throw new AppServiceException(e.getMessage());
            }
        }
        return orderNoGenerate.getNextno();
    }

    @Override
    public long init(String code, long start) {
        return init(code, start, 1);
    }

    @Override
    public long next(String code) {
        logger.info("code={}", code);
        OrderNoGenerate orderNoGenerate = orderNoGenerateMongoDao.findByCode(code);
        if (orderNoGenerate == null) {
            throw new AppServiceException("找不到code=" + code + "的数值，请先调用init初始化数值");
        }
        Query query = Query.query(Criteria.where("code").is(code));
        Update update = Update.update("nextno", (orderNoGenerate.getNextno().intValue() + orderNoGenerate.getStepleng()));
        orderNoGenerateMongoDao.update(query, update);
        return orderNoGenerate.getNextno();
    }

}
