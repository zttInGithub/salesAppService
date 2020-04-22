package com.hrtp.salesAppService.entity.appEntity;

import com.alibaba.fastjson.JSONObject;

/**
 * ReqTxnDetailDataEntity
 * description
 * create by lxj 2018/8/24
 **/
public class ReqTxnDetailDataEntity {
    private String beginDate;
    private String endDate;
    private String queryType;
    private String queryValue;
    private Integer rows;
    private Integer page;
    private String unno;

    public String getUnno() {
        return unno;
    }

    public void setUnno(String unno) {
        this.unno = unno;
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

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public String getQueryValue() {
        return queryValue;
    }

    public void setQueryValue(String queryValue) {
        this.queryValue = queryValue;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
