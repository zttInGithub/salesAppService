package com.hrtp.salesAppService.entity.appEntity;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

public class MerchantInfoEntity{
	
	private String joinConfirmDate;		//商户入网时间END
	private String joinConfirmDateEnd;		//商户入网时间END
	private String unno;				//机构编号
	private String mid;					//商户编号
	private String rname;				//商户名称
	private Integer rows;
    private Integer page;
	private Integer total;
	private List<JSONObject> list;
	
	public String getJoinConfirmDate() {
		return joinConfirmDate;
	}
	public void setJoinConfirmDate(String joinConfirmDate) {
		this.joinConfirmDate = joinConfirmDate;
	}
	public String getJoinConfirmDateEnd() {
		return joinConfirmDateEnd;
	}
	public void setJoinConfirmDateEnd(String joinConfirmDateEnd) {
		this.joinConfirmDateEnd = joinConfirmDateEnd;
	}
	public String getUnno() {
		return unno;
	}
	public void setUnno(String unno) {
		this.unno = unno;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getRname() {
		return rname;
	}
	public void setRname(String rname) {
		this.rname = rname;
	}
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public List<JSONObject> getList() {
		return list;
	}
	public void setList(List<JSONObject> list) {
		this.list = list;
	}
}
