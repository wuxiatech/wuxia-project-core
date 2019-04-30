/*
* Created on :2016年6月17日
* Author     :songlin
* Change History
* Version       Date         Author           Reason
* <Ver.No>     <date>        <who modify>       <reason>
* Copyright 2014-2020 www.ibmall.cn All right reserved.
*/
package cn.wuxia.project.basic.core.conf.service;

import java.util.List;
import java.util.Map;

import cn.wuxia.project.basic.core.conf.bean.TagGroupCategory;
import cn.wuxia.project.basic.core.conf.entity.CustomTagGroup;
import cn.wuxia.project.common.service.CommonService;

public interface CustomTagGroupService extends CommonService<CustomTagGroup, String> {

    public List<TagGroupCategory> findByCode(String groupcode);

    public List<TagGroupCategory> findByFilter(Map<String, String> filter);
}
