/*
* Created on :2016年6月17日
* Author     :songlin
* Change History
* Version       Date         Author           Reason
* <Ver.No>     <date>        <who modify>       <reason>
* Copyright 2014-2020 www.ibmall.cn All right reserved.
*/
package cn.wuxia.project.basic.core.conf.service;

import java.util.Date;
import java.util.List;

import cn.wuxia.project.basic.core.conf.entity.DicHolidays;
import cn.wuxia.project.common.service.CommonService;

public interface HolidaysService extends CommonService<DicHolidays, String> {

    /**
     * 按照date查询
     * @author songlin
     * @param date
     * @return
     */
    public List<DicHolidays> findByDate(Date date);


    /**
     * 按照当天是什么节日
     * @author songlin
     * @return
     */
    public List<DicHolidays> findByDate();
}
