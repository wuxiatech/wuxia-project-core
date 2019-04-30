/*
* Created on :2016年6月17日
* Author     :songlin
* Change History
* Version       Date         Author           Reason
* <Ver.No>     <date>        <who modify>       <reason>
* Copyright 2014-2020 www.ibmall.cn All right reserved.
*/
package cn.wuxia.project.basic.core.conf.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import cn.wuxia.project.basic.core.conf.dao.HolidaysMongoDao;
import cn.wuxia.project.basic.core.conf.entity.DicHolidays;
import cn.wuxia.project.basic.core.conf.service.HolidaysService;
import cn.wuxia.project.common.dao.CommonMongoDao;
import cn.wuxia.project.common.service.impl.CommonMongoServiceImpl;
import cn.wuxia.common.util.DateUtil;
import cn.wuxia.common.util.ListUtil;
import cn.wuxia.common.util.LunarDateUtil;
import cn.wuxia.common.util.StringUtil;

@Service
public class HolidaysServiceImpl extends CommonMongoServiceImpl<DicHolidays, String> implements HolidaysService {

    @Autowired
    HolidaysMongoDao holidaysMongoDao;

    @Override
    protected CommonMongoDao<DicHolidays, String> getCommonDao() {
        return holidaysMongoDao;
    }

    @Override
    public List<DicHolidays> findByDate(Date date) {
        String dateindex = DateUtil.dateToString(date, "MMdd");
        List<DicHolidays> date1 = holidaysMongoDao.findByDate(dateindex, false);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String lunardateindex = new LunarDateUtil(calendar).simple();
        lunardateindex = StringUtil.substring(lunardateindex, 4, lunardateindex.length());
        List<DicHolidays> date2 = holidaysMongoDao.findByDate(lunardateindex, true);
        List<DicHolidays> returnDate = Lists.newArrayList();
        if (ListUtil.isNotEmpty(date1))
            returnDate.addAll(date1);
        if (ListUtil.isNotEmpty(date2))
            returnDate.addAll(date2);
        return returnDate;
    }

    @Override
    public List<DicHolidays> findByDate() {
        return findByDate(new Date());
    }
}
