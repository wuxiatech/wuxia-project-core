/*
* Created on :2016年6月17日
* Author     :songlin
* Change History
* Version       Date         Author           Reason
* <Ver.No>     <date>        <who modify>       <reason>
* Copyright 2014-2020 www.ibmall.cn All right reserved.
*/
package cn.wuxia.project.basic.core.conf.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.wuxia.project.basic.core.conf.dao.CustomTagMongoDao;
import cn.wuxia.project.basic.core.conf.entity.CustomTag;
import cn.wuxia.project.basic.core.conf.entity.CustomTagCategory;
import cn.wuxia.project.basic.core.conf.service.CustomTagCategoryService;
import cn.wuxia.project.basic.core.conf.service.CustomTagService;
import cn.wuxia.project.common.dao.CommonMongoDao;
import cn.wuxia.project.common.service.impl.CommonMongoServiceImpl;
import cn.wuxia.common.util.StringUtil;

@Service
public class CustomTagServiceImpl extends CommonMongoServiceImpl<CustomTag, String> implements CustomTagService {

    @Autowired
    private CustomTagMongoDao tagDao;

    @Autowired
    private CustomTagCategoryService tagCategoryService;

    @Override
    public CustomTag save(CustomTag customTag) {
        if (StringUtil.isNotBlank(customTag.getCategoryId())) {

        } else {
            CustomTagCategory customTagCategory = tagCategoryService.save(new CustomTagCategory(customTag.getCategoryName()));
            customTag.setCategoryId(customTagCategory.getId());
        }
        return super.save(customTag);
    }

    @Override
    protected CommonMongoDao<CustomTag, String> getCommonDao() {
        return tagDao;
    }

    @Override
    public List<CustomTag> findByCategory(String categoryId) {
        logger.info("categoryId={}", categoryId);
        return tagDao.findBy("categoryId", categoryId);
    }

    @Override
    public void deleteByCategory(String categoryId) {
        logger.info("categoryId={}", categoryId);
        tagDao.deleteByCategoryId(categoryId);
    }
}
