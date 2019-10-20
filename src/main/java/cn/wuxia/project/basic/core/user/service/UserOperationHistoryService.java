/*
 * Created on :2015年10月12日
 * Author     :wuwenhao
 * Change History
 * Version       Date         Author           Reason
 * <Ver.No>     <date>        <who modify>       <reason>
 * Copyright 2014-2020 wuxia.tech All right reserved.
 */
package cn.wuxia.project.basic.core.user.service;

import cn.wuxia.project.basic.core.user.entity.UserOperationHistory;
import cn.wuxia.project.common.service.CommonService;

public interface UserOperationHistoryService extends CommonService<UserOperationHistory, String> {

    /**
     * 记录用户操作
     *
     * @author guwen
     */
    public void saveUserOperation(String uid, String userOperation, String remark);


}
