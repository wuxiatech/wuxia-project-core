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
import java.util.Map;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.wuxia.project.basic.core.conf.bean.TagGroupCategory;
import cn.wuxia.project.basic.core.conf.dao.CustomTagGroupMongoDao;
import cn.wuxia.project.basic.core.conf.entity.CustomTagGroup;
import cn.wuxia.project.basic.core.conf.service.CustomTagCategoryService;
import cn.wuxia.project.basic.core.conf.service.CustomTagGroupService;
import cn.wuxia.project.common.dao.CommonMongoDao;
import cn.wuxia.project.common.service.impl.CommonMongoServiceImpl;
import cn.wuxia.common.util.ListUtil;

@Service
public class CustomTagGroupServiceImpl extends CommonMongoServiceImpl<CustomTagGroup, String> implements CustomTagGroupService {

    @Autowired
    private CustomTagCategoryService tagCategoryService;

    @Autowired
    private CustomTagGroupMongoDao tagGroupMongoDao;

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
    protected CommonMongoDao<CustomTagGroup, String> getCommonDao() {
        return tagGroupMongoDao;
    }

    @Override
    public List<TagGroupCategory> findByCode(String groupcode) {
        logger.info("groupcode={}", groupcode);

        CustomTagGroup tagGroup = tagGroupMongoDao.findUniqueBy("code", groupcode);
        if (tagGroup != null) {
            return tagGroup.getChildren();
        }
        return null;
    }

    @Override
    public List<TagGroupCategory> findByFilter(Map<String, String> filter) {

        List<CustomTagGroup> list = tagGroupMongoDao.findByFilter(filter);
        if (ListUtil.isNotEmpty(list)) {
            List<TagGroupCategory> result = Lists.newArrayList();
            for (CustomTagGroup customTagGroup : list) {
                result.addAll(customTagGroup.getChildren());
            }
            return result;
        }
        return null;
    }

}
