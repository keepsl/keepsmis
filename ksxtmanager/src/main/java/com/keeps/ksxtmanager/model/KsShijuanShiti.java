package com.keeps.ksxtmanager.model;
// default package
// Generated 2017-10-5 16:25:39 by Hibernate Tools 3.2.2.GA

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.keeps.core.model.AbstractModelInteger;

/**
 * KsShijuanShiti generated by hbm2java
 */
@Entity
@Table(name = "ks_shijuan_shiti")
public class KsShijuanShiti extends AbstractModelInteger{

	private Integer id;
	private Integer shijuanid;
	private Integer shitiid;
	private Integer fenshu;
	private Date createtime;
	private Integer createperson;

	public KsShijuanShiti() {
	}

	public KsShijuanShiti(Integer id, Integer shijuanid, Integer shitiid, Integer fenshu, Date createtime, Integer createperson) {
		this.id = id;
		this.shijuanid = shijuanid;
		this.shitiid = shitiid;
		this.fenshu = fenshu;
		this.createtime = createtime;
		this.createperson = createperson;
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

	@Column(name = "shitiid", nullable = false)
	public Integer getShitiid() {
		return this.shitiid;
	}
	
	
	@Column(name = "fenshu", nullable = false)
	public Integer getFenshu() {
		return fenshu;
	}

	public void setFenshu(Integer fenshu) {
		this.fenshu = fenshu;
	}

	public void setShitiid(Integer shitiid) {
		this.shitiid = shitiid;
	}

	@Column(name = "createtime", nullable = false, length = 19)
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@Column(name = "createperson", nullable = false)
	public Integer getCreateperson() {
		return this.createperson;
	}

	public void setCreateperson(Integer createperson) {
		this.createperson = createperson;
	}


}
