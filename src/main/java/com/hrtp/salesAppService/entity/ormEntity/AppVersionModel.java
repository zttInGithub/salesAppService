package com.hrtp.salesAppService.entity.ormEntity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="AGENT_APP_VERSION")
public class AppVersionModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
	private String id;
	@Column(name="apkversion")
	private String   Version;
	@Column(name ="url")
	private String   url;
	@Column(name ="create_Date")
	private Date  createDate;
	@Column(name= "version_desc")
	private String   versionDesc;
	@Column(name = "os")
	private String   os;
	@Column(name="is_force_update")
	private String   isForceUpdate;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getVersion() {
		return Version;
	}
	public void setVersion(String version) {
		Version = version;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getVersionDesc() {
		return versionDesc;
	}
	public void setVersionDesc(String versionDesc) {
		this.versionDesc = versionDesc;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public String getIsForceUpdate() {
		return isForceUpdate;
	}
	public void setIsForceUpdate(String isForceUpdate) {
		this.isForceUpdate = isForceUpdate;
	}

	
}
