package com.hrtp.salesAppService.entity.appEntity;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RespEntity {
    // "00"——成功，"99"——失败
    private String returnCode;
    private String returnMsg;
    private Object returnBody;

    public RespEntity() {
        super();
    }

    /**
     * @param returnCode
     * @param returnMsg
     */
    public RespEntity(String returnCode, String returnMsg) {
        this.returnCode = returnCode;
        this.returnMsg = returnMsg;
    }

    public RespEntity(String returnCode,String returnMsg,Object returnBody){
        this.returnCode = returnCode;
        this.returnMsg = returnMsg;
        this.returnBody = returnBody;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public Object getReturnBody() {
        return returnBody;
    }

    public void setReturnBody(Object returnBody) {
        this.returnBody = returnBody;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
