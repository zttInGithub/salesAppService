package com.hrtp.salesAppService.entity.appEntity.v2;

import com.hrtp.salesAppService.entity.appEntity.LoginRespEntity;

/**
 * CommonReqEntity
 * <p>
 * This is description
 *
 * @author xuegangliu 2019/07/09
 * @since 1.8
 **/
public class CommonReqEntity extends LoginRespEntity {

	private Integer page;
	private Integer size;

	//20200212 刷卡费率变更新增极速版费率    
	private String jisuCash;
	// 代理商名称
	private String agentName;
	// 开始终端号
	private String startTermid;
	// 结束终端号
	private String endTermid;
	// 终端总数
	private Integer termidCount;
	// 子代理机构号
	private String subUnno;
	// 父代理机构号
	private String upperUnno;
	private Integer type;
	// 下发/回拨 我的/上下级
	private Integer subType;
	// 终端下发/回拨id
	private Integer btcId;
	// 成本信息
	private PlusCost costList;
	// 活动类型
	private Integer rebateType;
	//被选中的sn集合
	private String snList;

	// 子代理buid
	private Integer subBuid;

	// 产品 10000:秒到 10002:联刷 10005:收银台 10006:PLUS
	private String agentId;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public Integer getSubBuid() {
		return subBuid;
	}

	public void setSubBuid(Integer subBuid) {
		this.subBuid = subBuid;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getStartTermid() {
		return startTermid;
	}

	public void setStartTermid(String startTermid) {
		this.startTermid = startTermid;
	}

	public String getEndTermid() {
		return endTermid;
	}

	public void setEndTermid(String endTermid) {
		this.endTermid = endTermid;
	}

	public Integer getTermidCount() {
		return termidCount;
	}

	public void setTermidCount(Integer termidCount) {
		this.termidCount = termidCount;
	}

	public String getSubUnno() {
		return subUnno;
	}

	public void setSubUnno(String subUnno) {
		this.subUnno = subUnno;
	}

	public String getUpperUnno() {
		return upperUnno;
	}

	public void setUpperUnno(String upperUnno) {
		this.upperUnno = upperUnno;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getSubType() {
		return subType;
	}

	public void setSubType(Integer subType) {
		this.subType = subType;
	}

	public Integer getBtcId() {
		return btcId;
	}

	public void setBtcId(Integer btcId) {
		this.btcId = btcId;
	}

	public PlusCost getCostList() {
		return costList;
	}

	public void setCostList(PlusCost costList) {
		this.costList = costList;
	}

	public Integer getRebateType() {
		return rebateType;
	}

	public void setRebateType(Integer rebateType) {
		this.rebateType = rebateType;
	}

	public String getSnList() {
		return snList;
	}

	public void setSnList(String snList) {
		this.snList = snList;
	}

	public String getJisuCash() {
		return jisuCash;
	}

	public void setJisuCash(String jisuCash) {
		this.jisuCash = jisuCash;
	}
}
