package com.hrtp.salesAppService.entity.appEntity;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * StockEntity
 * description 库存管理
 * sl
 **/
public class StockEntity {
    private String unno;			//机构号
    private Integer type;			//类型：2Mpos;1小蓝牙
    private String terminalTotal;	//总库存
    private String useTer;			//已使用数量
    private String noUseTer;		//未使用
    private String activaTer;	    //已激活
    private String noActivaTer;		//未激活
    private String backTer;			//已返现
    private String noBackTer;		//为返现
	public String getUnno() {
		return unno;
	}
	public void setUnno(String unno) {
		this.unno = unno;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getTerminalTotal() {
		return terminalTotal;
	}
	public void setTerminalTotal(String terminalTotal) {
		this.terminalTotal = terminalTotal;
	}
	public String getUseTer() {
		return useTer;
	}
	public void setUseTer(String useTer) {
		this.useTer = useTer;
	}
	public String getNoUseTer() {
		return noUseTer;
	}
	public void setNoUseTer(String noUseTer) {
		this.noUseTer = noUseTer;
	}
	public String getActivaTer() {
		return activaTer;
	}
	public void setActivaTer(String activaTer) {
		this.activaTer = activaTer;
	}
	public String getNoActivaTer() {
		return noActivaTer;
	}
	public void setNoActivaTer(String noActivaTer) {
		this.noActivaTer = noActivaTer;
	}
	public String getBackTer() {
		return backTer;
	}
	public void setBackTer(String backTer) {
		this.backTer = backTer;
	}
	public String getNoBackTer() {
		return noBackTer;
	}
	public void setNoBackTer(String noBackTer) {
		this.noBackTer = noBackTer;
	}
	
	@Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
    
}
