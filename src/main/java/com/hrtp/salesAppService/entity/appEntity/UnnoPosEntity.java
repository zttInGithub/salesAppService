package com.hrtp.salesAppService.entity.appEntity;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * UnnoPosEntity
 * description TODO
 * create by lxj 2018/8/22
 **/
public class UnnoPosEntity {
    private String beginDate;
    private String endDate;
    private Integer txnType;
    private String unno;
    private Double fenrunTotal;
    private Double txnAmtTotal;
    private Integer txnCountTotal;
    private Double bfeeTotal;
    private List<JSONObject> txnList;

    public Double getBfeeTotal() {
        return bfeeTotal;
    }

    public void setBfeeTotal(Double bfeeTotal) {
        this.bfeeTotal = bfeeTotal;
    }

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

    public Integer getTxnType() {
        return txnType;
    }

    public void setTxnType(Integer txnType) {
        this.txnType = txnType;
    }

    public String getUnno() {
        return unno;
    }

    public void setUnno(String unno) {
        this.unno = unno;
    }

    public Double getFenrunTotal() {
        return fenrunTotal;
    }

    public void setFenrunTotal(Double fenrunTotal) {
        this.fenrunTotal = fenrunTotal;
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

    public List<JSONObject> getTxnList() {
        return txnList;
    }

    public void setTxnList(List<JSONObject> txnList) {
        this.txnList = txnList;
    }
}
