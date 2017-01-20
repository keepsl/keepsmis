package com.keeps.model;

// default package
// Generated 2017-1-20 9:27:59 by Hibernate Tools 3.2.2.GA

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.keeps.core.model.AbstractModel;
import com.keeps.core.model.plus.ModelPlusSchool;

/**
 * TDict generated by hbm2java
 */
@Entity
@Table(name = "t_dict", catalog = "keeps")
public class TDict extends AbstractModel implements ModelPlusSchool{

	private Integer id;
	private String dicitem;
	private Integer dickey;
	private String dicvalue;
	private String remark;
	private Date createtime;
	private Date updatetime;
	private Integer createperson;

	public TDict() {
	}

	public TDict(String dicitem, Integer dickey, String dicvalue, String remark, Date createtime, Date updatetime,
			Integer createperson) {
		this.dicitem = dicitem;
		this.dickey = dickey;
		this.dicvalue = dicvalue;
		this.remark = remark;
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

	@Column(name = "dicitem")
	public String getDicitem() {
		return this.dicitem;
	}

	public void setDicitem(String dicitem) {
		this.dicitem = dicitem;
	}

	@Column(name = "dickey")
	public Integer getDickey() {
		return this.dickey;
	}

	public void setDickey(Integer dickey) {
		this.dickey = dickey;
	}

	@Column(name = "dicvalue")
	public String getDicvalue() {
		return this.dicvalue;
	}

	public void setDicvalue(String dicvalue) {
		this.dicvalue = dicvalue;
	}

	@Column(name = "remark")
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
