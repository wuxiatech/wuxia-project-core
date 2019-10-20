package cn.wuxia.project.basic.core.conf.bean;

import cn.wuxia.project.common.bean.CommonDto;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;


/**
 * @author songlin
 */
@Getter
@Setter
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





	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}