package com.hrtp.salesAppService.entity.ormEntity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AGENT_BANK_CARDBIN")
public class BankCardModel implements Serializable{
    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "ISSUING_BANK_NAME")
	private String bankName;				//银行名称
	
	@Column(name = "CARD_NAME")
	private String cardName;				//卡片名称
	@Id
	@Column(name = "CARD_BIN")
	private String cardBin;					//cardBin
	
	@Column(name = "CARD_TYPE")
	private String cardType;				//类型
	
	@Column(name = "PARENT_PAYMENTLINE")
	private String payBankId;				//支付系统行号

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
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