package com.hrtp.salesAppService.entity.appEntity;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * MessageEntity
 * description 消息列表封装
 * create by lxj 2018/8/30
 **/
public class MessageEntity {
	private String Id;
    private String unno;
    private Integer rows;
    private Integer page;
    private Integer countTotal;
    private List<JSONObject> noticeList;

    public Integer getCountTotal() {
        return countTotal;
    }

    public void setCountTotal(Integer countTotal) {
        this.countTotal = countTotal;
    }

    public List<JSONObject> getNoticeList() {
        return noticeList;
    }

    public void setNoticeList(List<JSONObject> noticeList) {
        this.noticeList = noticeList;
    }

    public String getUnno() {
        return unno;
    }

    public void setUnno(String unno) {
        this.unno = unno;
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

    public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	@Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
