package cn.wuxia.project.basic.core.conf.service;

import cn.wuxia.project.basic.core.conf.entity.KeyPoint;
import cn.wuxia.project.common.service.CommonService;

import java.util.Date;
import java.util.List;

public interface KeyPointService extends CommonService<KeyPoint, String> {
    /**
     * 统计次数
     *
     * @param action
     * @param begin
     * @param end
     * @return
     */
    public long countAction(String action, Date begin, Date end);
    /**
     * 统计次数
     *
     * @param action
     * @param begin
     * @param end
     * @return
     */
//    public List<CountByDate> countAction2(String action, Date begin, Date end);
}
