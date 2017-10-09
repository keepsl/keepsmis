package com.keeps.ksxtmanager.model;

// default package
// Generated 2017-10-6 15:46:15 by Hibernate Tools 3.2.2.GA

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

import com.keeps.core.model.AbstractModelInteger;

/**
 * KsShijuanEmp generated by hbm2java
 */
@Entity
@Table(name = "ks_shijuan_emp")
public class KsShijuanEmp extends AbstractModelInteger implements java.io.Serializable{

	private Integer id;
	private Integer shijuanid;
	private Integer empid;

	public KsShijuanEmp() {
	}

	public KsShijuanEmp(Integer shijuanid, Integer empid) {
		this.shijuanid = shijuanid;
		this.empid = empid;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "shijuanid", nullable = false)
	public Integer getShijuanid() {
		return this.shijuanid;
	}

	public void setShijuanid(Integer shijuanid) {
		this.shijuanid = shijuanid;
	}

	@Column(name = "empid", nullable = false)
	public Integer getEmpid() {
		return this.empid;
	}

	public void setEmpid(Integer empid) {
		this.empid = empid;
	}

}
