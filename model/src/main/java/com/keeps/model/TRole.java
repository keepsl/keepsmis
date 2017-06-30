package com.keeps.model;

// default package
// Generated 2017-6-18 13:49:40 by Hibernate Tools 3.2.2.GA

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

import com.keeps.core.model.AbstractModelInteger;
import com.keeps.core.model.plus.ModelPlusSchool;

/**
 * TRole generated by hbm2java
 */
@Entity
@Table(name = "t_role")
public class TRole extends AbstractModelInteger implements ModelPlusSchool{

	private Integer id;
	private String name;
	private Integer roletype;
	private Integer status;
	private Integer sort;
	private Date createtime;
	private Date updatetime;
	private Integer createperson;

	public TRole() {
	}

	public TRole(String name, Integer roletype, Integer status) {
		this.name = name;
		this.roletype = roletype;
		this.status = status;
	}

	public TRole(String name, Integer roletype, Integer status, Integer sort, Date createtime, Date updatetime,
			Integer createperson) {
		this.name = name;
		this.roletype = roletype;
		this.status = status;
		this.sort = sort;
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

	@Column(name = "name", nullable = false)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "roletype", nullable = false)
	public Integer getRoletype() {
		return this.roletype;
	}

	public void setRoletype(Integer roletype) {
		this.roletype = roletype;
	}

	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "sort")
	public Integer getSort() {
		return this.sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Column(name = "createtime", length = 19)
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

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