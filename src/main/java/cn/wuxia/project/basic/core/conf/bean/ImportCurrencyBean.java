package cn.wuxia.project.basic.core.conf.bean;

import cn.wuxia.common.validator.ValidationEntity;
import cn.wuxia.tools.excel.annotation.ExcelColumn;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 导入汇率EXCEL的bean
 * Created by luodengxiong on 2017/02/11.
 */
public class ImportCurrencyBean extends ValidationEntity {
    private static final long serialVersionUID = -7362777596277266391L;

    @ExcelColumn(columnName = "币种", colunmIndex = 0)
    @NotNull(message = "币种名称不能为空")
    private String currencyName; // 币种名称 - 必填

    @ExcelColumn(columnName = "币种代码", colunmIndex = 1)
    @NotNull(message = "币种代码不能为空")
    private String currencyCode; // 币种代码 - 必填

    @ExcelColumn(columnName = "货币符号", colunmIndex = 2)
    @NotNull(message = "币种符号不能为空")
    private String currencySign; // 币种符号 - 必填

    @ExcelColumn(columnName = "人民币换成外币的汇率", colunmIndex = 3)
    @NotNull(message = "人民币换成外币的汇率不能为空")
    private Double exchangeRateFromRmb; // 人民币换成外币的汇率 - 必填

    @ExcelColumn(columnName = "外币换成人民币的汇率", colunmIndex = 4)
    @NotNull(message = "外币换成人民币的汇率不能为空")
    private Double exchangeRateToRmb; // 外币换成人民币的汇率 - 必填

    @ExcelColumn(columnName = "有效期起", colunmIndex = 5)
    @NotNull(message = "有效期起不能为空")
    private Date validFrom; // 有效期起 - 可为空

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencySign() {
        return currencySign;
    }

    public void setCurrencySign(String currencySign) {
        this.currencySign = currencySign;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public Double getExchangeRateFromRmb() {
        return exchangeRateFromRmb;
    }

    public void setExchangeRateFromRmb(Double exchangeRateFromRmb) {
        this.exchangeRateFromRmb = exchangeRateFromRmb;
    }

    public Double getExchangeRateToRmb() {
        return exchangeRateToRmb;
    }

    public void setExchangeRateToRmb(Double exchangeRateToRmb) {
        this.exchangeRateToRmb = exchangeRateToRmb;
    }
}
