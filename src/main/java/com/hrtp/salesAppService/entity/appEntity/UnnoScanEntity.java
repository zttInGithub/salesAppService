package com.hrtp.salesAppService.entity.appEntity;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * UnnoScanEntity
 * description 扫码分润
 * create by lxj 2018/8/22
 **/
public class UnnoScanEntity {
    private String beginDate;
    private String endDate;
    private String unno;
    private Double bfeeFenrunTotal;
    private Double transferFenrunTotal;
    private Double txnAmtTotal;
    private Integer txnCountTotal;
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

    public Double getTransferFenrunTotal() {
        return transferFenrunTotal;
    }

    public void setTransferFenrunTotal(Double transferFenrunTotal) {
        this.transferFenrunTotal = transferFenrunTotal;
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
