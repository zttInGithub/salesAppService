package com.hrtp.salesAppService.entity.appEntity;


import org.springframework.web.multipart.MultipartFile;
import com.alibaba.fastjson.JSONObject;

/**
 * BackOrderEntity
 * description 差错管理----退单
 * sl
 **/
public class BackOrderEntity {
	private Integer page;		    //页数
	private Integer rows;			//行数
    private String unno;			//机构号
    private Integer type;			//查询类型：0未回复，1已回复/过期
    private Integer roId;			//id标识
    private String commentContext;	//备注
    private String processContext;	//描述
    private MultipartFile reorDeruploadFile; //签购单
    private MultipartFile reorDerupload1File; //交易流水明细
    private MultipartFile reorDerupload2File; //身份证正面
    private MultipartFile reorDerupload3File; //身份证反面
    private MultipartFile reorDerupload4File; //交易正面
    private MultipartFile reorDerupload5File; //持卡人撤销退单声明
    
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


	public String getUnno() {
		return unno;
	}


	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}


	public void setUnno(String unno) {
		this.unno = unno;
	}


	public Integer getRoId() {
		return roId;
	}


	public void setRoId(Integer roId) {
		this.roId = roId;
	}


	public String getCommentContext() {
		return commentContext;
	}


	public void setCommentContext(String commentContext) {
		this.commentContext = commentContext;
	}


	public String getProcessContext() {
		return processContext;
	}


	public void setProcessContext(String processContext) {
		this.processContext = processContext;
	}


	public MultipartFile getReorDeruploadFile() {
		return reorDeruploadFile;
	}


	public void setReorDeruploadFile(MultipartFile reorDeruploadFile) {
		this.reorDeruploadFile = reorDeruploadFile;
	}


	public MultipartFile getReorDerupload1File() {
		return reorDerupload1File;
	}


	public void setReorDerupload1File(MultipartFile reorDerupload1File) {
		this.reorDerupload1File = reorDerupload1File;
	}


	public MultipartFile getReorDerupload2File() {
		return reorDerupload2File;
	}


	public void setReorDerupload2File(MultipartFile reorDerupload2File) {
		this.reorDerupload2File = reorDerupload2File;
	}


	public MultipartFile getReorDerupload3File() {
		return reorDerupload3File;
	}


	public void setReorDerupload3File(MultipartFile reorDerupload3File) {
		this.reorDerupload3File = reorDerupload3File;
	}


	public MultipartFile getReorDerupload4File() {
		return reorDerupload4File;
	}


	public void setReorDerupload4File(MultipartFile reorDerupload4File) {
		this.reorDerupload4File = reorDerupload4File;
	}


	public MultipartFile getReorDerupload5File() {
		return reorDerupload5File;
	}


	public void setReorDerupload5File(MultipartFile reorDerupload5File) {
		this.reorDerupload5File = reorDerupload5File;
	}


	@Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
    
}
