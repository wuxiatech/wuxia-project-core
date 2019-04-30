package cn.wuxia.project.basic.core.conf.service;

import cn.wuxia.project.basic.core.conf.entity.GenerateStaticPage;
import cn.wuxia.project.common.service.CommonService;

/**
 * GenerateStaticPage Service Interface.
 * 
 * @author songlin.li
 * @since 2014-04-16
 */
public interface GenerateStaticPageService extends CommonService<GenerateStaticPage, String> {
    /**
     * 生成一个静态页
     * 
     * @author songlin
     * @param staticPage
     * @throws Exception
     */
    public void generateOnePage(GenerateStaticPage staticPage) throws Exception;

}
