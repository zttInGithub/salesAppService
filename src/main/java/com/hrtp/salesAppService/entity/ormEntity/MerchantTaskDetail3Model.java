package com.hrtp.salesAppService.entity.ormEntity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "BILL_MERCHANTTASKDETAIL3")
public class MerchantTaskDetail3Model  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "S_BILL_MERCHANTTASKDETAIL31", sequenceName = "S_BILL_MERCHANTTASKDETAIL3",allocationSize = 1)
	@GeneratedValue(generator = "S_BILL_MERCHANTTASKDETAIL31",strategy = GenerationType.SEQUENCE)
    @Column(name = "BT3ID")
	private Integer bt3id;							// 修改商户Banck信息唯一主键
	
	@Column(name = "BMTKID")
	private Integer bmtkid;							//所关联“待审核”主键
	
	@Column(name = "BANKFEERATE")
	private Double bankFeeRate;					//外卡费率
	
	@Column(name = "CREDITBANKRATE")
	private Double creditBankRate;					//贷记卡费率
	
	@Column(name = "SCANRATE")
	private Double scanRate;					//微信扫码费率

	@Column(name = "SCANRATE1")
	private Double scanRate1;					//银联扫码费率

	@Column(name = "SCANRATE2")
	private Double scanRate2;					//支付宝扫码费率
	
	@Column(name = "FEEAMT")
	private Double feeAmt;							//封顶手续费
	
	@Column(name = "DEALAMT")
	private Double dealAmt;                         //封顶值=封顶值费率/银行卡费率
	
	@Column(name = "ISFOREIGN")
	private Integer isForeign;						//是否开通外卡
	
	@Column(name = "FEERATE_V")
	private Double feeRateV;						//外卡费率——visa
	
	@Column(name = "FEERATE_M")
	private Double feeRateM;						//外卡费率——master
	
	@Column(name = "FEERATE_J")
	private Double feeRateJ;						//外卡费率——jcb
	
	@Column(name = "FEERATE_A")
	private Double feeRateA;						//外卡费率——美运
	
	@Column(name = "FEERATE_D")
	private Double feeRateD;						//外卡费率——大莱
	
	@Column(name = "FEEUPLOAD")
	private String feeUpLoad;						//变更申请上传文件名
	
	@Column(name = "SECONDRATE")
	private Double secondRate;						//秒到手续费


	public Double getScanRate1() {
		return scanRate1;
	}
	public void setScanRate1(Double scanRate1) {
		this.scanRate1 = scanRate1;
	}
	public Double getScanRate2() {
		return scanRate2;
	}
	public void setScanRate2(Double scanRate2) {
		this.scanRate2 = scanRate2;
	}

	public Double getSecondRate() {
		return secondRate;
	}

	public void setSecondRate(Double secondRate) {
		this.secondRate = secondRate;
	}

	public Double getScanRate() {
		return scanRate;
	}

	public void setScanRate(Double scanRate) {
		this.scanRate = scanRate;
	}

	public Double getCreditBankRate() {
		return creditBankRate;
	}

	public void setCreditBankRate(Double creditBankRate) {
		this.creditBankRate = creditBankRate;
	}

	public Integer getBt3id() {
		return bt3id;
	}

	public void setBt3id(Integer bt3id) {
		this.bt3id = bt3id;
	}

	public Integer getBmtkid() {
		return bmtkid;
	}

	public void setBmtkid(Integer bmtkid) {
		this.bmtkid = bmtkid;
	}
 
	public Double getBankFeeRate() {
		return bankFeeRate;
	}

	public void setBankFeeRate(Double bankFeeRate) {
		this.bankFeeRate = bankFeeRate;
	}

	public Double getFeeAmt() {
		return feeAmt;
	}

	public void setFeeAmt(Double feeAmt) {
		this.feeAmt = feeAmt; 
	}

	
	public Integer getIsForeign() {
		return isForeign;
	}

	public void setIsForeign(Integer isForeign) {
		this.isForeign = isForeign;
	}

	public Double getFeeRateV() {
		return feeRateV;
	}

	public void setFeeRateV(Double feeRateV) {
		this.feeRateV = feeRateV;
	}

	public Double getFeeRateM() {
		return feeRateM;
	}

	public void setFeeRateM(Double feeRateM) {
		this.feeRateM = feeRateM;
	}

	public Double getFeeRateJ() {
		return feeRateJ;
	}

	public void setFeeRateJ(Double feeRateJ) {
		this.feeRateJ = feeRateJ;
	}

	public Double getFeeRateA() {
		return feeRateA;
	}

	public void setFeeRateA(Double feeRateA) {
		this.feeRateA = feeRateA;
	}

	public Double getFeeRateD() {
		return feeRateD;
	}

	public void setFeeRateD(Double feeRateD) {
		this.feeRateD = feeRateD;
	}

	public String getFeeUpLoad() {
		return feeUpLoad;
	}

	public void setFeeUpLoad(String feeUpLoad) {
		this.feeUpLoad = feeUpLoad;
	}

	public Double getDealAmt() {
		return dealAmt;
	}

	public void setDealAmt(Double dealAmt) {
		this.dealAmt = dealAmt;
	}

	public MerchantTaskDetail3Model() {
		super(); 
	}
}
