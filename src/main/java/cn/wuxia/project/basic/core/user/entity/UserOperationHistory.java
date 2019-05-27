package cn.wuxia.project.basic.core.user.entity;

import cn.wuxia.project.common.model.ModifyInfoEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * The persistent class for the u_user_operation_history database table.
 * 
 */
@Entity
@Table(name = "u_user_operation_history")
public class UserOperationHistory extends ModifyInfoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Timestamp operationDatetime;

    private String operationIp;

    private String operationType;

    private String operationUserId;

    private String remark;

    private String serialNo;

    public UserOperationHistory() {
        super();
    }

    @Column(name = "operation_datetime")
    public Timestamp getOperationDatetime() {
        return this.operationDatetime;
    }

    public void setOperationDatetime(Timestamp operationDatetime) {
        this.operationDatetime = operationDatetime;
    }

    @Column(name = "operation_ip")
    public String getOperationIp() {
        return this.operationIp;
    }

    public void setOperationIp(String operationIp) {
        this.operationIp = operationIp;
    }

    @Column(name = "operation_type")
    public String getOperationType() {
        return this.operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    @Column(name = "operation_user_id")
    public String getOperationUserId() {
        return this.operationUserId;
    }

    public void setOperationUserId(String operationUserId) {
        this.operationUserId = operationUserId;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "serial_no")
    public String getSerialNo() {
        return this.serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

}
