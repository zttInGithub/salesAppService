package com.hrtp.salesAppService.entity.appEntity;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * TxnFenRunEntity
 * description TODO
 * create by lxj 2018/8/24
 **/
public class TxnFenRunEntity {
    private String beginDate;
    private String endDate;
    private String unno;
    private Double txnAmtTotal;
    private Integer txnCountTotal;
    private List<JSONObject> actMerList;

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

    public String getUnno() {
        return unno;
    }

    public void setUnno(String unno) {
        this.unno = unno;
    }

    public Double getTxnAmtTotal() {
        return txnAmtTotal;
    }

    public void setTxnAmtTotal(Double txnAmtTotal) {
        this.txnAmtTotal = txnAmtTotal;
    }

    public Integer getTxnCountTotal() {
        return txnCountTotal;
    }

    public void setTxnCountTotal(Integer txnCountTotal) {
        this.txnCountTotal = txnCountTotal;
    }

    public List<JSONObject> getActMerList() {
        return actMerList;
    }

    public void setActMerList(List<JSONObject> actMerList) {
        this.actMerList = actMerList;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
