package com.hrtp.MQService.entity.model;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "AGENT_PUSH_MASSAGE")
public class PushMessage implements Serializable{
    private static final long version = 1L;

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    private String id;
    @Column(name = "AGENT_ID")
    private String agentId;
    @Column(name = "TITLE")
    private String title;
    @Column(name = "MESSAGE")
    private String content;
    @Column(name = "MSG_TYPE")
    private String msgType;
    @Column(name = "CREATE_DATE")
    private Date time;
    @Column(name = "STATUS")
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
