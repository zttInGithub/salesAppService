package com.hrtp.salesAppService.entity.appEntity;

import com.alibaba.fastjson.JSONObject;

/**
 * UnnoMerInfoEntity
 * description 商户汇总
 * create by lxj 2018/8/23
 **/
public class UnnoMerInfoEntity {
    private String unno;
    private Integer ydayActMerchCount;
    private Integer ydayAddMerchCount;
    private Integer ydayJhuoMerchCount;

    public UnnoMerInfoEntity() {
    }

    public UnnoMerInfoEntity(Integer ydayActMerchCount, Integer ydayAddMerchCount, Integer ydayJhuoMerchCount) {
        this.ydayActMerchCount = ydayActMerchCount;
        this.ydayAddMerchCount = ydayAddMerchCount;
        this.ydayJhuoMerchCount = ydayJhuoMerchCount;
    }

    public String getUnno() {
        return unno;
    }

    public void setUnno(String unno) {
        this.unno = unno;
    }

    public Integer getYdayActMerchCount() {
        return ydayActMerchCount;
    }

    public void setYdayActMerchCount(Integer ydayActMerchCount) {
        this.ydayActMerchCount = ydayActMerchCount;
    }

    public Integer getYdayAddMerchCount() {
        return ydayAddMerchCount;
    }

    public void setYdayAddMerchCount(Integer ydayAddMerchCount) {
        this.ydayAddMerchCount = ydayAddMerchCount;
    }

    public Integer getYdayJhuoMerchCount() {
        return ydayJhuoMerchCount;
    }

    public void setYdayJhuoMerchCount(Integer ydayJhuoMerchCount) {
        this.ydayJhuoMerchCount = ydayJhuoMerchCount;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
