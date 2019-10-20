/*
* Created on :2016年6月17日
* Author     :songlin
* Change History
* Version       Date         Author           Reason
* <Ver.No>     <date>        <who modify>       <reason>
* Copyright 2014-2020 wuxia.tech All right reserved.
*/
package cn.wuxia.project.basic.core.conf.service;

import java.util.List;

import cn.wuxia.project.basic.core.conf.entity.CustomTag;
import cn.wuxia.project.common.service.CommonService;

public interface CustomTagService extends CommonService<CustomTag, String> {

    List<CustomTag> findByCategory(String categoryId);

    void deleteByCategory(String categoryId);

}
