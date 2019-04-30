package cn.wuxia.project.basic.core.conf.entity;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

import cn.wuxia.project.common.model.ModifyInfoMongoEntity;
import cn.wuxia.common.util.NumberUtil;

/**
 * 有序号码生成器
 * 
 * @author songlin.li
 * @since 2017-04-27
 */
@Document(collection = "order_no_generate")
public class OrderNoGenerate extends ModifyInfoMongoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String code;

    private Integer stepleng;

    private Long startno;

    private Long nextno;

    private String remark;
    public OrderNoGenerate() {
        super();
    }

    public OrderNoGenerate(String code, Long startno, Integer stepleng) {
        super();
        this.code = code;
        this.startno = startno;
        this.setStepleng(stepleng);
        this.setNextno(NumberUtil.toLong(startno.intValue() + stepleng));
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getStepleng() {
        return stepleng;
    }

    public void setStepleng(Integer stepleng) {
        this.stepleng = stepleng;
    }

    public Long getNextno() {
        return nextno;
    }

    public void setNextno(Long nextno) {
        this.nextno = nextno;
    }

    public Long getStartno() {
        return startno;
    }

    public void setStartno(Long startno) {
        this.startno = startno;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
