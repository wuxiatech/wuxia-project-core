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

import cn.wuxia.project.basic.core.conf.support.DicBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import cn.wuxia.project.basic.core.conf.dao.SystemDictionaryMongoDao;
import cn.wuxia.project.basic.core.conf.entity.SystemDictionary;
import cn.wuxia.project.basic.core.conf.service.SystemDictionaryService;
import cn.wuxia.project.common.dao.CommonMongoDao;
import cn.wuxia.project.common.service.impl.CommonMongoServiceImpl;
import cn.wuxia.common.util.ListUtil;
import cn.wuxia.common.util.reflection.BeanUtil;

@Service
public class SystemDictionaryServiceImpl extends CommonMongoServiceImpl<SystemDictionary, String> implements SystemDictionaryService {

    @Autowired
    private SystemDictionaryMongoDao sysDicDao;

    @Override
    public List<SystemDictionary> findByParentId(String parentid) {
        return sysDicDao.findByParent(parentid);
    }

    /**
     * 查找有效期内的参数
     *
     * @param code
     * @return
     * @author songlin
     */
    public List<DicBean> findByParentCode(String code) {
        logger.info("code={}", code);
        List<SystemDictionary> cc = sysDicDao.findBy("code", code);
        if (ListUtil.isNotEmpty(cc)) {
            SystemDictionary parent = cc.get(0);
            cc = sysDicDao.findByParent(parent.getId());
            return ListUtil.copyProperties(DicBean.class, cc);
        }
        return Lists.newArrayList();
    }

    /**
     * 查找有效期内的参数
     *
     * @param code
     * @return
     * @author songlin
     */
    public DicBean findByCodeAndParentCode(String code, String parentCode) {
        logger.info("code={}，parentCode={} ", code, parentCode);
        SystemDictionary systemDictionary = sysDicDao.findOneByCodeAndParentCode(code, parentCode);
        if (systemDictionary != null) {
            DicBean dicBean = new DicBean();
            BeanUtil.copyProperties(dicBean, systemDictionary);
            return dicBean;
        }
        return null;
    }

    @Override
    public DicBean findByCode(String code) {
        logger.info("code={}", code);
        List<SystemDictionary> systemDictionaries = sysDicDao.findBy("code", code);
        if (ListUtil.isNotEmpty(systemDictionaries)) {
            DicBean dicBean = new DicBean();
            BeanUtil.copyProperties(dicBean, systemDictionaries.get(0));
            return dicBean;
        }
        return null;
    }

    @Override
    protected CommonMongoDao<SystemDictionary, String> getCommonDao() {
        return sysDicDao;
    }
}
