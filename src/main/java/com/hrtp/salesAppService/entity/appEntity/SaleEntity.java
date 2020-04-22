package com.hrtp.salesAppService.entity.appEntity;

import java.io.Serializable;

/**
 * 业务员信息
 * yq 2020/2
 */
public class SaleEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String busId;//销售表主键
	private String busId1;//组长选中查询busId
	private String flag;//0-注册查询，1-激活查询
	private String isAll;//是否汇总：不是汇总-false，总汇总-0，一组汇总-1，二组汇总-2
	private String startDate;
	private String endDate;
	private Integer page;//分页
	private String unno;
	private String unName;
	private String rebateType;
	private String txnType;//1-Mpos、2-PLUS、3-收银台

	public String getUnno() {
		return unno;
	}
	public void setUnno(String unno) {
		this.unno = unno;
	}
	public String getUnName() {
		return unName;
	}
	public void setUnName(String unName) {
		this.unName = unName;
	}
	public String getRebateType() {
		return rebateType;
	}
	public void setRebateType(String rebateType) {
		this.rebateType = rebateType;
	}
	public String getTxnType() {
		return txnType;
	}
	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getIsAll() {
		return isAll;
	}
	public void setIsAll(String isAll) {
		this.isAll = isAll;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getBusId() {
		return busId;
	}
	public void setBusId(String busId) {
		this.busId = busId;
	}
	public String getBusId1() {
		return busId1;
	}
	public void setBusId1(String busId1) {
		this.busId1 = busId1;
	}
	
	
	
	
}
