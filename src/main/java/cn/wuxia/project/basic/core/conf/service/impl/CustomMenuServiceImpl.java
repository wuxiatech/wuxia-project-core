package cn.wuxia.project.basic.core.conf.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.wuxia.project.basic.core.conf.dao.CustomMenuGroupMongoDao;
import cn.wuxia.project.basic.core.conf.dao.CustomMenuMongoDao;
import cn.wuxia.project.basic.core.conf.entity.CustomMenu;
import cn.wuxia.project.basic.core.conf.service.CustomMenuService;
import cn.wuxia.project.common.dao.CommonMongoDao;
import cn.wuxia.project.common.service.impl.CommonMongoServiceImpl;
import cn.wuxia.common.exception.AppServiceException;
import cn.wuxia.common.orm.query.Conditions;
import cn.wuxia.common.orm.query.MatchType;
import cn.wuxia.common.orm.query.Sort;
import cn.wuxia.common.util.StringUtil;

@Service
@Transactional
public class CustomMenuServiceImpl extends CommonMongoServiceImpl<CustomMenu, String> implements CustomMenuService {

    @Autowired
    private CustomMenuMongoDao menuDao;

    @Autowired
    private CustomMenuGroupMongoDao menuRefDao;

    @Override
    protected CommonMongoDao<CustomMenu, String> getCommonDao() {
        return menuDao;
    }

    @Override
    public void delete(String id) {
        if (StringUtil.isNotBlank(id)) {
            try {
                menuDao.deleteById(id);
                menuDao.deleteByParentId(id);
            } catch (Exception e) {
                throw new AppServiceException("节点id无效", e);
            }
        } else {
            throw new AppServiceException("节点id不能为空");
        }
    }

    /**
     * 为了递归，如有需要
     * @param parentid
     * @return
     */
    @Override
    public List<CustomMenu> findByParentId(String parentid) {
        if (StringUtil.isBlank(parentid))
            return findAll();
        return menuDao.findByParentId(parentid);
    }

    @Override
    public List<CustomMenu> findAll() {
        return find(new Sort("sortOrder"), new Conditions("parentId", MatchType.ISN, null));
    }

}
