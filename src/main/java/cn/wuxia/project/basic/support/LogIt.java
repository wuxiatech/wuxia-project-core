package cn.wuxia.project.basic.support;

import cn.wuxia.common.exception.AppDaoException;
import cn.wuxia.common.exception.AppServiceException;
import cn.wuxia.common.spring.SpringContextHolder;
import cn.wuxia.project.basic.core.conf.entity.KeyPoint;
import cn.wuxia.project.basic.core.conf.service.KeyPointService;
import cn.wuxia.project.common.support.AsyncTaskManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogIt {

    protected final static Logger logger = LoggerFactory.getLogger(LogIt.class);


    private static KeyPointService keyPointService = SpringContextHolder.getBean(KeyPointService.class);

    private static AsyncTaskManager asyncTaskManager;

    private static AsyncTaskManager getAsyncTaskManager() {
        if (asyncTaskManager == null) {
            try {
                asyncTaskManager = SpringContextHolder.getBean("asyncTaskManager");
            } catch (Exception e) {
                logger.warn("asyncTaskManager not create");
            }
        }
        return asyncTaskManager;
    }

    public static void action(String pointKey) {
        if (getAsyncTaskManager() != null) {
            asyncTaskManager.getExecutor().execute(() -> {
                try {
                    keyPointService.save(new KeyPoint(pointKey));
                } catch (AppDaoException e) {
                    e.printStackTrace();
                }
            });
        } else {
            try {
                keyPointService.save(new KeyPoint(pointKey));
            } catch (AppDaoException e) {
                throw new AppServiceException("保存失败", e);
            }
        }
    }
}
