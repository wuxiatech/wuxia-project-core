package cn.wuxia.project.basic.core.conf.service;

import java.util.List;

import cn.wuxia.project.basic.core.conf.entity.CustomMenu;
import cn.wuxia.project.common.service.CommonService;

public interface CustomMenuService extends CommonService<CustomMenu, String> {

    public List<CustomMenu> findByParentId(String parentMenuId);
}
