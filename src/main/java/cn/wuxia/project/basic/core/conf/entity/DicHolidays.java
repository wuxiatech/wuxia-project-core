package cn.wuxia.project.basic.core.conf.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import cn.wuxia.project.common.model.ModifyInfoMongoEntity;

@Document(collection = "dic_holidays")
public class DicHolidays extends ModifyInfoMongoEntity {

    private static final long serialVersionUID = -8549279952984280446L;
    /**
     * 日期索引
     */
    String dateindex;

    /**
     * 是不是节假日
     */
    boolean holiday;

    /**
     * 节日描述
     */
    String remark;

    /**
     * 是不是阴历节日
     */
    boolean lunar;


    public String getDateindex() {
        return dateindex;
    }

    public void setDateindex(String dateindex) {
        this.dateindex = dateindex;
    }

    public boolean isHoliday() {
        return holiday;
    }

    public void setHoliday(boolean holiday) {
        this.holiday = holiday;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isLunar() {
        return lunar;
    }

    public void setLunar(boolean lunar) {
        this.lunar = lunar;
    }
}
