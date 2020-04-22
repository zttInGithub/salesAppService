package com.hrtp.salesAppService.entity.ormEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * FileUtilsModel
 * description TODO
 * sl
 **/
@Entity
@Table(name = "SYS_PARAM")
public class FileUtilsModel implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "TITLE")
    private String title;
    @Column(name = "PWD_EXPIRY_DAYS")
    private String pwd_expiry_days;
    @Column(name = "PWD_ERR_TIMES")
    private String pwd_err_times;
    @Column(name = "UPLOAD_PATH")
    private String upload_path;
    @Column(name = "DOWNLOAD_PATH")
    private String download_path;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "CREATE_DATE")
    private Date create_date;
    @Column(name = "CREATE_USER")
    private String create_user;
    @Column(name = "UPDATE_DATE")
    private Date update_date;
    @Column(name = "UPDATE_USER")
    private String update_user;
    

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPwd_expiry_days() {
		return pwd_expiry_days;
	}

	public void setPwd_expiry_days(String pwd_expiry_days) {
		this.pwd_expiry_days = pwd_expiry_days;
	}

	public String getPwd_err_times() {
		return pwd_err_times;
	}

	public void setPwd_err_times(String pwd_err_times) {
		this.pwd_err_times = pwd_err_times;
	}

	public String getUpload_path() {
		return upload_path;
	}

	public void setUpload_path(String upload_path) {
		this.upload_path = upload_path;
	}

	public String getDownload_path() {
		return download_path;
	}

	public void setDownload_path(String download_path) {
		this.download_path = download_path;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public String getCreate_user() {
		return create_user;
	}

	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}

	public Date getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}

	public String getUpdate_user() {
		return update_user;
	}

	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}

	public FileUtilsModel() {}
	
	public FileUtilsModel(String upload_path) {
		this.upload_path=upload_path;
	}

}
