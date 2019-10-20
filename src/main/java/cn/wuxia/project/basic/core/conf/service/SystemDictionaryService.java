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

import cn.wuxia.project.basic.core.conf.entity.SystemDictionary;
import cn.wuxia.project.basic.core.conf.support.DicBean;
import cn.wuxia.project.common.service.CommonService;

public interface SystemDictionaryService extends CommonService<SystemDictionary, String> {

    /**
     * 查找有效期内的参数
     * @author songlin
     * @param key
     * @return
     */
    public List<DicBean> findByParentCode(String code);

    /**
     * 查找有效期内的参数
     *
     * @param code
     * @return
     * @author songlin
     */
    public DicBean findByCodeAndParentCode(String code, String parentCode);

    /**
     *
     * @param code
     * @return
     */
    public DicBean findByCode(String code);

    /**
     * 根据分组查询
     * @param parentid
     * @return
     */
    public List<SystemDictionary> findByParentId(String parentid);

}
