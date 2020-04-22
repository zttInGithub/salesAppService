package com.hrtp.salesAppService.entity.appEntity;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * UnnoRepayEntity
 * description 信用卡代还
 * create by lxj 2018/8/22
 **/
public class UnnoRepayEntity extends ReqEntity {
    private String beginDate;
    private String endDate;
    private Double totalFenrun;
    private String unno;
    private Double bfeeFenrunTotal;
    private Double enterAmtTotal;
    private Double enterCountTotal;
    private List<JSONObject> txnList;

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Double getTotalFenrun() {
        return totalFenrun;
    }

    public void setTotalFenrun(Double totalFenrun) {
        this.totalFenrun = totalFenrun;
    }

    public String getUnno() {
        return unno;
    }

    public void setUnno(String unno) {
        this.unno = unno;
    }

    public Double getBfeeFenrunTotal() {
        return bfeeFenrunTotal;
    }

    public void setBfeeFenrunTotal(Double bfeeFenrunTotal) {
        this.bfeeFenrunTotal = bfeeFenrunTotal;
    }

    public Double getEnterAmtTotal() {
        return enterAmtTotal;
    }

    public void setEnterAmtTotal(Double enterAmtTotal) {
        this.enterAmtTotal = enterAmtTotal;
    }

    public Double getEnterCountTotal() {
        return enterCountTotal;
    }

    public void setEnterCountTotal(Double enterCountTotal) {
        this.enterCountTotal = enterCountTotal;
    }

    public List<JSONObject> getTxnList() {
        return txnList;
    }

    public void setTxnList(List<JSONObject> txnList) {
        this.txnList = txnList;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
