package com.hrtp.salesAppService.entity.appEntity;


import org.springframework.web.multipart.MultipartFile;
import com.alibaba.fastjson.JSONObject;

/**
 * MistakeEntity
 * description 差错管理----查询/调单
 * sl
 **/
public class MistakeEntity {
	private Integer page;		    //页数
	private Integer rows;			//行数
	private Integer dpId;			//id标识
    private String unno;			//机构号
    private Integer orderType;	    //调单类型：3查询；2调单
    private Integer type;			//回复类型：0未回复，1已回复/已过期
    private String rname;			//注册店名
    private String raddr;			//经营地址
    private String contactPhone;	//电话
    private String contactPerson;	//联系人
    private String agRemarks;		//备注
    private String orderUpload;		//上传单据
    private MultipartFile orderUploadFile; 	//上传单据

	public Integer getPage() {
		return page;
	}


	public void setPage(Integer page) {
		this.page = page;
	}


	public Integer getRows() {
		return rows;
	}


	public void setRows(Integer rows) {
		this.rows = rows;
	}


	public Integer getDpId() {
		return dpId;
	}


	public void setDpId(Integer dpId) {
		this.dpId = dpId;
	}


	public String getUnno() {
		return unno;
	}


	public void setUnno(String unno) {
		this.unno = unno;
	}


	public Integer getOrderType() {
		return orderType;
	}


	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}


	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}


	public String getRname() {
		return rname;
	}


	public void setRname(String rname) {
		this.rname = rname;
	}


	public String getRaddr() {
		return raddr;
	}


	public void setRaddr(String raddr) {
		this.raddr = raddr;
	}


	public String getContactPhone() {
		return contactPhone;
	}


	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}


	public String getContactPerson() {
		return contactPerson;
	}


	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}


	public String getAgRemarks() {
		return agRemarks;
	}


	public void setAgRemarks(String agRemarks) {
		this.agRemarks = agRemarks;
	}


	public String getOrderUpload() {
		return orderUpload;
	}


	public void setOrderUpload(String orderUpload) {
		this.orderUpload = orderUpload;
	}


	public MultipartFile getOrderUploadFile() {
		return orderUploadFile;
	}


	public void setOrderUploadFile(MultipartFile orderUploadFile) {
		this.orderUploadFile = orderUploadFile;
	}


	@Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
    
}
