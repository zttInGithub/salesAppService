package com.hrtp.salesAppService.entity.appEntity;

import com.alibaba.fastjson.JSONObject;

/**
 * UnnoNoticeEntity
 * description 公告
 * create by lxj 2018/8/24
 **/
public class UnnoNoticeEntity {
    private String unno;
    private String title;
    private String txt;
    private String createDate;

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUnno() {
        return unno;
    }

    public void setUnno(String unno) {
        this.unno = unno;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
