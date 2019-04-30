package cn.wuxia.project.basic.core.conf.dao;

import java.util.Date;
import java.util.List;

import cn.wuxia.project.basic.core.common.BaseCommonMongoDao;
import cn.wuxia.project.basic.core.conf.entity.Currency;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

/**
 * Currency Dao.
 * @author  LuoDengxiong
 * @since 2017-02-11
 */
@Component
public class CurrencyMongoDao extends BaseCommonMongoDao<Currency, String> {

    public Currency getByCurrencyCode(String code) {
        Query query = Query.query(Criteria.where("currencyCode").is(code).and("validFrom").lte(new Date()));
        List<Currency> currencyList = super.find(query);
        if (null != currencyList && currencyList.size() > 0) {
            return currencyList.get(0);
        }
        return null;
    }
}
