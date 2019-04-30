package cn.wuxia.project.basic.core.conf.entity;

import cn.wuxia.common.util.ArrayUtil;
import cn.wuxia.common.util.reflection.ReflectionUtil;
import cn.wuxia.project.basic.core.conf.enums.PointActionEnum;
import cn.wuxia.project.common.model.ModifyInfoMongoEntity;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "log_key_point")
public class KeyPoint extends ModifyInfoMongoEntity {
    String action;
    String a1;
    String a2;
    String a3;
    String a4;
    String a5;
    String an;

    Integer c;

    public KeyPoint() {
        super();
    }

    public KeyPoint(PointActionEnum action) {
        this(action.name());
    }

    public KeyPoint(String action) {
        this();
        this.action = action;
        this.c = 1;
    }

    public KeyPoint(String[] body) {
        this();
        if (ArrayUtil.isNotEmpty(body) && body.length == 1) {
            this.action = body[0];
            this.c = 1;
        } else if (ArrayUtil.isNotEmpty(body) && body.length > 1) {
            this.action = body[0];
            for (int i = 1; i < body.length; i++) {
                try {
                    ReflectionUtil.invokeSetterMethod(this, "a" + i, body[i]);
                } catch (Exception e) {
                    /**
                     * FIXME
                     */
                    this.an = body[body.length - 1];
                }
            }
        }
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getA1() {
        return a1;
    }

    public void setA1(String a1) {
        this.a1 = a1;
    }

    public String getA2() {
        return a2;
    }

    public void setA2(String a2) {
        this.a2 = a2;
    }

    public String getA3() {
        return a3;
    }

    public void setA3(String a3) {
        this.a3 = a3;
    }

    public String getA4() {
        return a4;
    }

    public void setA4(String a4) {
        this.a4 = a4;
    }

    public String getA5() {
        return a5;
    }

    public void setA5(String a5) {
        this.a5 = a5;
    }

    public String getAn() {
        return an;
    }

    public void setAn(String an) {
        this.an = an;
    }

    public Integer getC() {
        return c;
    }

    public void setC(Integer c) {
        this.c = c;
    }
}
