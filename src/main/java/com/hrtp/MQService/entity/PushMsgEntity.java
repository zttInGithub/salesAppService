package com.hrtp.MQService.entity;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * PushMsgEntity
 * description
 * create class by lxj 2019/1/11
 **/
@ApiModel("消息数据封装")
public class PushMsgEntity {
    @ApiModelProperty(value="产品编号",example="10006",required=true,notes = "10005-会员宝收银台，10006-展业宝")
    private String agentId;
    @ApiModelProperty(value = "极光注册id",example = "[121000,122000]",required = true,notes = "展业宝为机构号")
    private String[] registId;
    private String msgType;
    @ApiModelProperty(value = "消息内容",example = "卡号末尾为******的持卡人对在商户",required = true)
    private String content;
    @ApiModelProperty(value = "消息标题",example = "调单回复",required = true)
    private String title;
    @ApiModelProperty(value = "机构级别",example = "2",required = true)
    private String unLvl;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getUnLvl() {
        return unLvl;
    }

    public void setUnLvl(String unLvl) {
        this.unLvl = unLvl;
    }

    public String[] getRegistId() {
        return registId;
    }

    public void setRegistId(String[] registId) {
        this.registId = registId;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
