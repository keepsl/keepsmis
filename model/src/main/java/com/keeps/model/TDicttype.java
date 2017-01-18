package com.keeps.model;
// default package
// Generated 2017-1-18 17:05:55 by Hibernate Tools 3.2.2.GA

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.keeps.core.model.AbstractModel;
import com.keeps.core.model.plus.ModelPlusSchool;

/**
 * TDicttype generated by hbm2java
 */
@Entity
@Table(name = "t_dicttype")
public class TDicttype extends AbstractModel implements ModelPlusSchool {

	private Integer id;
	private String name;
	private Integer isinternal;
	private Date createtime;
	private Date updatetime;
	private Integer createperson;

	public TDicttype() {
	}

	public TDicttype(Integer id) {
		this.id = id;
	}

	public TDicttype(Integer id, String name, Integer isinternal, Date createtime, Date updatetime, Integer createperson) {
		this.id = id;
		this.name = name;
		this.isinternal = isinternal;
		this.createtime = createtime;
		this.updatetime = updatetime;
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

	@Column(name = "name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "isinternal")
	public Integer getIsinternal() {
		return this.isinternal;
	}

	public void setIsinternal(Integer isinternal) {
		this.isinternal = isinternal;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createtime", length = 19)
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updatetime", length = 19)
	public Date getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	@Column(name = "createperson")
	public Integer getCreateperson() {
		return this.createperson;
	}

	public void setCreateperson(Integer createperson) {
		this.createperson = createperson;
	}

}
