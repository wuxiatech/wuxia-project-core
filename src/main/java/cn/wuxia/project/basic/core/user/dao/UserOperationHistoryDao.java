package cn.wuxia.project.basic.core.user.dao;

import cn.wuxia.project.basic.core.user.entity.UserOperationHistory;
import cn.wuxia.project.common.dao.CommonDao;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class UserOperationHistoryDao extends CommonDao<UserOperationHistory, String> {

    /**
     * 查找用户是否第一次登录
     *
     * @author guwen
     */
    public Boolean findUserOperation(String uid) {
        List<Object> values = new ArrayList<>();
        String sql = "SELECT id FROM u_user_operation_history where operation_User_Id = ? ";
        values.add(uid);
        Long res = super.countSQLResult(sql.toString(), values.toArray());
        if (res <= 0) {
            return true;
        } else {
            return false;
        }
    }

}
