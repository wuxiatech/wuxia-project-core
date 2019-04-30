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

import cn.wuxia.project.basic.core.conf.bean.MenuBean;
import cn.wuxia.project.basic.core.conf.entity.CustomMenuGroup;
import cn.wuxia.project.common.service.CommonService;

public interface CustomMenuGroupService extends CommonService<CustomMenuGroup, String> {

    public List<MenuBean> findByCode(String groupcode);

}
