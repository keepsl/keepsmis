package com.keeps.model;

// default package
// Generated 2017-6-20 15:17:02 by Hibernate Tools 3.2.2.GA

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

import com.keeps.core.model.AbstractModelInteger;

/**
 * TMenuAccess generated by hbm2java
 */
@Entity
@Table(name = "t_menu_access")
public class TMenuAccess extends AbstractModelInteger implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer type;
	private Integer relid;
	private Integer menuid;

	public TMenuAccess() {
	}

	public TMenuAccess(Integer type, Integer relid, Integer menuid) {
		this.type = type;
		this.relid = relid;
		this.menuid = menuid;
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

	@Column(name = "type", nullable = false)
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "relid", nullable = false)
	public Integer getRelid() {
		return this.relid;
	}

	public void setRelid(Integer relid) {
		this.relid = relid;
	}

	@Column(name = "menuid", nullable = false)
	public Integer getMenuid() {
		return this.menuid;
	}

	public void setMenuid(Integer menuid) {
		this.menuid = menuid;
	}

}
