package com.hrtp.salesAppService.entity.appEntity;

import com.alibaba.fastjson.JSONObject;

public class MessageDetaliEntity {

	private String id;
	private Integer status;
	//plus新增
	private String unno;
	private Integer flag;
	private String merType;//商户状态
	private Integer sOrU;//交易：1-全部,2-自营   分润：3-全部，4-自营  
	private Integer page;//页数
	
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getsOrU() {
		return sOrU;
	}
	public void setsOrU(Integer sOrU) {
		this.sOrU = sOrU;
	}
	public String getMerType() {
		return merType;
	}
	public void setMerType(String merType) {
		this.merType = merType;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public String getUnno() {
		return unno;
	}
	public void setUnno(String unno) {
		this.unno = unno;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
