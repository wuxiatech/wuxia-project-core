package cn.wuxia.project.basic.core.conf.service;

import cn.wuxia.project.basic.core.conf.entity.Currency;
import cn.wuxia.project.common.service.CommonService;
import cn.wuxia.tools.excel.exception.ExcelException;

import java.io.InputStream;

/**
 * 货币汇率 Service Interface.
 * @author LuoDengxiong
 * @since 2017-02-11
 */
public interface CurrencyService extends  CommonService<Currency, String>{

    void importData(InputStream inputStream) throws ExcelException;

    Currency getByCurrencyCode(String code);
}
