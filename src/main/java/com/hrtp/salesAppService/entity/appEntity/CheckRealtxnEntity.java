package com.hrtp.salesAppService.entity.appEntity;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

public class CheckRealtxnEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String unno;				//机构编号
	private String mid;					//商户编号
	private String rname;				//商户名称
	private List<JSONObject> list;
	private Integer page;
	private Integer rows;
	private Integer total;
	
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
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
	public List<JSONObject> getList() {
		return list;
	}
	public void setList(List<JSONObject> list) {
		this.list = list;
	}
}
