package cn.wuxia.project.basic.core.conf.bean;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import cn.wuxia.project.common.bean.CommonDto;

import javax.validation.constraints.NotNull;


public class CurrencyDto extends CommonDto implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@NotNull(message = "币种名称不能为空")
	private String currencyName; // 币种名称 - 必填	
	@NotNull(message = "币种代码不能为空")
	private String currencyCode; // 币种代码 - 必填	
	@NotNull(message = "币种符号不能为空")
	private String currencySign; // 币种符号 - 必填	
	@NotNull(message = "人民币换成外币的汇率不能为空")
	private Double exchangeRateFromRmb; // 人民币换成外币的汇率 - 必填	
	@NotNull(message = "外币换成人民币的汇率不能为空")
	private Double exchangeRateToRmb; // 外币换成人民币的汇率 - 必填	
	private Date validFrom; // 有效期起 - 可为空	
	private Integer status; // 状态 - 可为空	

	
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id = id;
	}

	
	public String getCurrencyName(){
		return currencyName;
	}
	public void setCurrencyName(String currencyName){
		this.currencyName = currencyName;
	}

	
	public String getCurrencyCode(){
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode){
		this.currencyCode = currencyCode;
	}

	
	public String getCurrencySign(){
		return currencySign;
	}
	public void setCurrencySign(String currencySign){
		this.currencySign = currencySign;
	}

	
	public Double getExchangeRateFromRmb(){
		return exchangeRateFromRmb;
	}
	public void setExchangeRateFromRmb(Double exchangeRateFromRmb){
		this.exchangeRateFromRmb = exchangeRateFromRmb;
	}

	
	public Double getExchangeRateToRmb(){
		return exchangeRateToRmb;
	}
	public void setExchangeRateToRmb(Double exchangeRateToRmb){
		this.exchangeRateToRmb = exchangeRateToRmb;
	}

	
	public Date getValidFrom(){
		return validFrom;
	}
	public void setValidFrom(Date validFrom){
		this.validFrom = validFrom;
	}

	
	public Integer getStatus(){
		return status;
	}
	public void setStatus(Integer status){
		this.status = status;
	}



	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}