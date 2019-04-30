package cn.wuxia.project.basic.core.conf.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.mongodb.core.mapping.Document;

import cn.wuxia.project.common.model.ModifyInfoMongoEntity;


/**
* The persistent class for the dic_currency database table.
* @author LuoDengxiong
* @since 2017-02-12
*/
@Document(collection = "system_currency")
public class Currency extends ModifyInfoMongoEntity implements Serializable {

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

	@Length(max=64)
	@Column(name="currency_name")
	public String getCurrencyName(){
		return currencyName;
	}
	public void setCurrencyName(String currencyName){
		this.currencyName = currencyName;
	}

	@Length(max=8)
	@Column(name="currency_code")
	public String getCurrencyCode(){
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode){
		this.currencyCode = currencyCode;
	}

	@Length(max=8)
	@Column(name="currency_sign")
	public String getCurrencySign(){
		return currencySign;
	}
	public void setCurrencySign(String currencySign){
		this.currencySign = currencySign;
	}

	@Column(name="exchange_rate_from_rmb")
	public Double getExchangeRateFromRmb(){
		return exchangeRateFromRmb;
	}
	public void setExchangeRateFromRmb(Double exchangeRateFromRmb){
		this.exchangeRateFromRmb = exchangeRateFromRmb;
	}

	@Column(name="exchange_rate_to_rmb")
	public Double getExchangeRateToRmb(){
		return exchangeRateToRmb;
	}
	public void setExchangeRateToRmb(Double exchangeRateToRmb){
		this.exchangeRateToRmb = exchangeRateToRmb;
	}

	@Column(name="valid_from")
	public Date getValidFrom(){
		return validFrom;
	}
	public void setValidFrom(Date validFrom){
		this.validFrom = validFrom;
	}

	@Column(name="status")
	public Integer getStatus(){
		return status;
	}
	public void setStatus(Integer status){
		this.status = status;
	}



	public Currency() {
		super();
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	//遇到外键的处理示例，作用是保存外键的时候不需获取父对象
	/**
	private Long projectId;//项目id	
	private ParentProject project;//所属项目
	@Transient
	public Long getProjectId() {
		return this.getProject()==null?null:this.getProject().getId();
	}
	public void setProjectId(Long projectId) {
	    if (projectId==null) {
	    	project = null;
	    } else if (project == null) {
			//这里需要目标父对象有构造方法
	    	project = new ParentProject(projectId);
	    } else {
	    	project.setId(projectId);
	    }
	}
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PROJECT_ID")	
	public ParentProject getProject() {
		return project;
	}
	public void setProject(ParentProject project) {
		this.project = project;
	}	
	**/
}