package com.hrtp.salesAppService.entity.appEntity;

import com.alibaba.fastjson.JSONObject;

/**
 * UnnoCashBackEntity
 * description 返现
 * create by lxj 2018/8/23
 **/
public class UnnoCashBackEntity {
    private String unno;
    private Double totalFanXianAmt;
    private Double updateTime;
    private Double ydayFanXianAmt;

    public String getUnno() {
        return unno;
    }

    public void setUnno(String unno) {
        this.unno = unno;
    }

    public Double getTotalFanXianAmt() {
        return totalFanXianAmt;
    }

    public void setTotalFanXianAmt(Double totalFanXianAmt) {
        this.totalFanXianAmt = totalFanXianAmt;
    }

    public Double getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Double updateTime) {
        this.updateTime = updateTime;
    }

    public Double getYdayFanXianAmt() {
        return ydayFanXianAmt;
    }

    public void setYdayFanXianAmt(Double ydayFanXianAmt) {
        this.ydayFanXianAmt = ydayFanXianAmt;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
