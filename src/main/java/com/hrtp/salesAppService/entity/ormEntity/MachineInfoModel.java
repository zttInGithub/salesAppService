package com.hrtp.salesAppService.entity.ormEntity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BILL_MACHINEINFO")
public class MachineInfoModel implements Serializable{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -1394927864791168602L;

	@Id
    @Column(name = "BMAID")
	private Integer bmaID;				//商户主键
	
	@Column(name = "MACHINEMODEL")
	private String machineModel;		//机具型号
	
	@Column(name = "MACHINETYPE")
	private String machineType;			//机具类型
	
	@Column(name = "MACHINEPRICE")
	private String machinePrice;		//机具单价
	
	@Column(name = "MACHINESTOCK")
	private String machineStock;		//机具库存
	
	@Column(name = "MAINTAINUID")
	private String maintainUID;			//操作人员
	
	@Column(name = "MAINTAINDATE")
	private Date maintainDate;		//操作日期
	
	@Column(name = "MAINTAINTYPE")
	private String maintainType;		//操作类型

	public MachineInfoModel() {
	}
	
	public MachineInfoModel(Integer bmaID, String machineModel, String machineType) {
		super();
		this.bmaID = bmaID;
		this.machineModel = machineModel;
		this.machineType = machineType;
	}

	public Integer getBmaID() {
		return bmaID;
	}

	public void setBmaID(Integer bmaID) {
		this.bmaID = bmaID;
	}

	public String getMachineModel() {
		return machineModel;
	}

	public void setMachineModel(String machineModel) {
		this.machineModel = machineModel;
	}

	public String getMachineType() {
		return machineType;
	}

	public void setMachineType(String machineType) {
		this.machineType = machineType;
	}

	public String getMachinePrice() {
		return machinePrice;
	}

	public void setMachinePrice(String machinePrice) {
		this.machinePrice = machinePrice;
	}

	public String getMachineStock() {
		return machineStock;
	}

	public void setMachineStock(String machineStock) {
		this.machineStock = machineStock;
	}

	public String getMaintainUID() {
		return maintainUID;
	}

	public void setMaintainUID(String maintainUID) {
		this.maintainUID = maintainUID;
	}

	public Date getMaintainDate() {
		return maintainDate;
	}

	public void setMaintainDate(Date maintainDate) {
		this.maintainDate = maintainDate;
	}

	public String getMaintainType() {
		return maintainType;
	}

	public void setMaintainType(String maintainType) {
		this.maintainType = maintainType;
	}
}