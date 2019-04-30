package cn.wuxia.project.basic.core.conf.service.impl;

import cn.wuxia.project.basic.core.conf.dao.KeyPointDao;
import cn.wuxia.project.basic.core.conf.entity.KeyPoint;
import cn.wuxia.project.basic.core.conf.service.KeyPointService;
import cn.wuxia.project.common.dao.CommonMongoDao;
import cn.wuxia.project.common.service.impl.CommonMongoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class KeyPointServiceImpl extends CommonMongoServiceImpl<KeyPoint, String> implements KeyPointService {

    @Autowired
    private KeyPointDao keyPointDao;


    @Override
    protected CommonMongoDao<KeyPoint, String> getCommonDao() {
        return keyPointDao;
    }

    @Override
    public long countAction(String action, Date begin, Date end) {
        return keyPointDao.countAction(action, begin, end);
    }






    /**
     * 统计次数
     *
     * @param action
     * @param begin
     * @param end
     * @return
     */
//    public List<CountByDate> countAction2(String action, Date begin, Date end){
//        return keyPointDao.countAction2(action, begin, end);
//    }
}
