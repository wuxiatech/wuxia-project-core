/*
 * Created on :2016年6月17日
 * Author     :songlin
 * Change History
 * Version       Date         Author           Reason
 * <Ver.No>     <date>        <who modify>       <reason>
 * Copyright 2014-2020 wuxia.tech All right reserved.
 */
package cn.wuxia.project.basic.core.conf.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import cn.wuxia.project.basic.core.conf.bean.MenuBean;
import cn.wuxia.project.basic.core.conf.dao.CustomMenuGroupMongoDao;
import cn.wuxia.project.basic.core.conf.dao.CustomMenuMongoDao;
import cn.wuxia.project.basic.core.conf.entity.CustomMenu;
import cn.wuxia.project.basic.core.conf.entity.CustomMenuGroup;
import cn.wuxia.project.basic.core.conf.service.CustomMenuGroupService;
import cn.wuxia.project.common.dao.CommonMongoDao;
import cn.wuxia.project.common.service.impl.CommonMongoServiceImpl;
import cn.wuxia.common.util.ListUtil;

@Service
public class CustomMenuGroupServiceImpl extends CommonMongoServiceImpl<CustomMenuGroup, String> implements CustomMenuGroupService {

    @Autowired
    private CustomMenuGroupMongoDao menuGroupMongoDao;

    @Autowired
    private CustomMenuMongoDao menuMongoDao;

    @Override
    public void delete(String id) {
        super.delete(id);
        /**
         * 删除CustomMenuGroup
         */
    }

    @Override
    protected CommonMongoDao<CustomMenuGroup, String> getCommonDao() {
        return menuGroupMongoDao;
    }

    @Override
    public List<MenuBean> findByCode(String groupcode) {
        logger.info("groupcode={}", groupcode);

        CustomMenuGroup menuGroup = menuGroupMongoDao.findUniqueBy("groupCode", groupcode);
        if (menuGroup != null) {
            List<CustomMenu> menus = menuGroup.getMenus();
            if (ListUtil.isNotEmpty(menus)) {
                List<MenuBean> menuBeans = Lists.newArrayList();
                for (CustomMenu menu : menus) {
                    menu = menuMongoDao.findById(menu.getId());
                    List<CustomMenu> subMenus = menuMongoDao.findByParentId(menu.getId());
                    MenuBean menuBean = new MenuBean(menu);
                    if (ListUtil.isNotEmpty(subMenus)) {
                        menuBean.setSubMenu(ListUtil.copyProperties(MenuBean.class, subMenus));
                    }
                    menuBeans.add(menuBean);
                }
                return menuBeans;
            }
        }
        return null;
    }

}
