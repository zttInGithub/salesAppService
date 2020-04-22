package com.hrtp.salesAppService.entity.ormEntity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "SYS_TODO")
public class TodoModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name = "S_SYS_TODO1", sequenceName = "S_SYS_TODO",allocationSize = 1)
	@GeneratedValue(generator = "S_SYS_TODO1",strategy = GenerationType.SEQUENCE)
    @Column(name = "TODOID")
	private Integer todoID;			//代办编号
	
	@Column(name = "UNNO")
    private String unNo;			//接收机构ID
	
	@Column(name = "BATCHJOBNO")
    private String batchJobNo;		//业务关键字
	
	@Column(name = "MSGSENDUNIT")
    private String msgSendUnit;		//发送机构ID
	
	@Column(name = "MSGSENDER")
    private Integer msgSender;		//发送用户ID
	
	@Column(name = "MSGTOPIC")
    private String msgTopic;		//发送主题
	
	@Column(name = "MSGSENDTIME")
    private Date msgSendTime;		//发送时间
	
	@Column(name = "TRANCODE")
    private String tranCode;		//菜单代码
	
	@Column(name = "BIZTYPE")
    private String bizType;			//业务类别
	
	@Column(name = "STATUS")
    private Integer status;			//代办状态
    
	public Integer getTodoID() {
		return todoID;
	}
	public void setTodoID(Integer todoID) {
		this.todoID = todoID;
	}
	public String getUnNo() {
		return unNo;
	}
	public void setUnNo(String unNo) {
		this.unNo = unNo;
	}
	public String getBatchJobNo() {
		return batchJobNo;
	}
	public void setBatchJobNo(String batchJobNo) {
		this.batchJobNo = batchJobNo;
	}
	public String getMsgSendUnit() {
		return msgSendUnit;
	}
	public void setMsgSendUnit(String msgSendUnit) {
		this.msgSendUnit = msgSendUnit;
	}
	public Integer getMsgSender() {
		return msgSender;
	}
	public void setMsgSender(Integer msgSender) {
		this.msgSender = msgSender;
	}
	public String getMsgTopic() {
		return msgTopic;
	}
	public void setMsgTopic(String msgTopic) {
		this.msgTopic = msgTopic;
	}
	public Date getMsgSendTime() {
		return msgSendTime;
	}
	public void setMsgSendTime(Date msgSendTime) {
		this.msgSendTime = msgSendTime;
	}
	public String getTranCode() {
		return tranCode;
	}
	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}
	public String getBizType() {
		return bizType;
	}
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
    
}
