package com.hrtp.salesAppService.entity.appEntity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BankCardEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Integer bankName;				//支付系统行号
	private String cardName;				//卡片名称
	private String cardBin;					//cardBin
	private String cardType;				//类型
	private String payBankId;				//支付系统行号
	private String bankAccNo;					//卡号
	private String bankAccName;				//持卡人姓名
	@JsonProperty("IdCard")  
	private String IdCard;					//持卡人身份证号
	private String unno;
	@JsonProperty("bName") 
	private String bName;					//银行名称
	private String url;						//银行log地址

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUnno() {
		return unno;
	}
	public void setUnno(String unno) {
		this.unno = unno;
	}
	@JsonProperty("IdCard")  
	public String getIdCard() {
		return IdCard;
	}
	@JsonProperty("IdCard")  
	public void setIdCard(String IdCard) {
		this.IdCard = IdCard;
	}
	public String getBankAccNo() {
		return bankAccNo;
	}
	public void setBankAccNo(String bankAccNo) {
		this.bankAccNo = bankAccNo;
	}
	@JsonProperty("bName") 
	public String getbName() {
		return bName;
	}
	@JsonProperty("bName") 
	public void setbName(String bName) {
		this.bName = bName;
	}
	public String getBankAccName() {
		return bankAccName;
	}
	public void setBankAccName(String bankAccName) {
		this.bankAccName = bankAccName;
	}
	public Integer getBankName() {
		return bankName;
	}
	public void setBankName(Integer bankName) {
		this.bankName = bankName;
	}
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	public String getCardBin() {
		return cardBin;
	}
	public void setCardBin(String cardBin) {
		this.cardBin = cardBin;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getPayBankId() {
		return payBankId;
	}
	public void setPayBankId(String payBankId) {
		this.payBankId = payBankId;
	}
}