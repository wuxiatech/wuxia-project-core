package cn.wuxia.project.basic.core.conf.service.impl;

import cn.wuxia.common.util.reflection.BeanUtil;
import cn.wuxia.project.basic.core.conf.bean.ImportCurrencyBean;
import cn.wuxia.project.basic.core.conf.dao.CurrencyMongoDao;
import cn.wuxia.project.basic.core.conf.entity.Currency;
import cn.wuxia.project.basic.core.conf.service.CurrencyService;
import cn.wuxia.project.common.dao.CommonMongoDao;
import cn.wuxia.project.common.service.impl.CommonMongoServiceImpl;
import cn.wuxia.project.common.support.CacheConstants;
import cn.wuxia.tools.excel.ImportExcelUtil;
import cn.wuxia.tools.excel.exception.ExcelException;
import com.google.common.collect.Lists;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 货币汇率 Service Implement class.
 * @author LuoDengxiong
 * @since 2017-02-11
 */
@Service
public class CurrencyServiceImpl extends CommonMongoServiceImpl<Currency, String> implements CurrencyService {

    @Autowired
    CurrencyMongoDao currencyDao;

    @Override
    protected CommonMongoDao getCommonDao() {
        return currencyDao;
    }

    @Override
    public void importData(InputStream inputStream) throws ExcelException {
        List<ImportCurrencyBean> priceBeanList = Lists.newArrayList();
        try {
            priceBeanList = ImportExcelUtil.importExcel(inputStream, ImportCurrencyBean.class);
        } catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //获得excel表格的数据后存入数据库，
        // TODO: 需要判断是否有重复 or 取值时获取最新一条相同值的记录？
        for (ImportCurrencyBean bean : priceBeanList) {
            Currency dto = new Currency();
            BeanUtil.copyProperties(dto, bean);
            save(dto);
        }
    }

    @Override
    @Cacheable(key = CacheConstants.CACHED_KEY_DEFAULT + "+#code", value = CacheConstants.CACHED_VALUE_1_DAY)
    public Currency getByCurrencyCode(String code) {
        return currencyDao.getByCurrencyCode(code);
    }

}
