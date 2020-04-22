package com.hrtp.salesAppService.entity.appEntity;

import com.alibaba.fastjson.JSONObject;

/**
 * UnnoTxnFenRunEntity
 * description 交易分润
 * create by lxj 2018/8/23
 **/
public class UnnoTxnFenRunEntity {
    private String unno;
    private Double totalFenrun;
    private Double ydayFenrun;
    private Double ydayTotalAmt;
    private Integer ydayTotalCount;

    public String getUnno() {
        return unno;
    }

    public void setUnno(String unno) {
        this.unno = unno;
    }

    public Double getTotalFenrun() {
        return totalFenrun;
    }

    public void setTotalFenrun(Double totalFenrun) {
        this.totalFenrun = totalFenrun;
    }

    public Double getYdayFenrun() {
        return ydayFenrun;
    }

    public void setYdayFenrun(Double ydayFenrun) {
        this.ydayFenrun = ydayFenrun;
    }

    public Double getYdayTotalAmt() {
        return ydayTotalAmt;
    }

    public void setYdayTotalAmt(Double ydayTotalAmt) {
        this.ydayTotalAmt = ydayTotalAmt;
    }

    public Integer getYdayTotalCount() {
        return ydayTotalCount;
    }

    public void setYdayTotalCount(Integer ydayTotalCount) {
        this.ydayTotalCount = ydayTotalCount;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
