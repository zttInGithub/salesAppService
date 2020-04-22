package com.hrtp.salesAppService.entity.ormEntity.v2;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
@Entity
@Table(name="CHECK_PROFITTEMPLATE_RECORD_LOG")
public class CheckMicroProfittemplateLog implements Serializable {

	/**
	 * 分润模板记录表
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "s_check_profittemplate_record", sequenceName = "s_check_profittemplate_record",allocationSize = 1)
	@GeneratedValue(generator = "s_check_profittemplate_record",strategy = GenerationType.SEQUENCE)
	@Column(name="CPRID")
	private Integer cprId;

}
