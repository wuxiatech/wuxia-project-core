/*
* Created on :2016年6月17日
* Author     :songlin
* Change History
* Version       Date         Author           Reason
* <Ver.No>     <date>        <who modify>       <reason>
* Copyright 2014-2020 wuxia.tech All right reserved.
*/
package cn.wuxia.project.basic.core.conf.service.impl;

import cn.wuxia.project.basic.core.conf.dao.CustomTagCategoryMongoDao;
import cn.wuxia.project.basic.core.conf.entity.CustomTagCategory;
import cn.wuxia.project.basic.core.conf.service.CustomTagCategoryService;
import cn.wuxia.project.basic.core.conf.service.CustomTagService;
import cn.wuxia.project.common.dao.CommonMongoDao;
import cn.wuxia.project.common.service.impl.CommonMongoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomTagCategoryServiceImpl extends CommonMongoServiceImpl<CustomTagCategory, String> implements CustomTagCategoryService {

    @Autowired
    private CustomTagCategoryMongoDao tagCategoryMongoDao;

    @Autowired
    private CustomTagService tagService;

    @Override
    public void delete(String id) {
        super.delete(id);
        /**
         * 删除CustomTag
         */
        /**
         * 考虑到标签还被调用，暂不删除
         * TODO
         */
        //tagService.deleteByCategory(id);
    }

    @Override
    protected CommonMongoDao<CustomTagCategory, String> getCommonDao() {
        return tagCategoryMongoDao;
    }

    @Override
    public CustomTagCategory findByCode(String code) {
        logger.info("code={}", code);
        return tagCategoryMongoDao.findUniqueBy("code", code);
    }
}
