package com.keeps.model;
// default package
// Generated 2017-6-20 21:33:10 by Hibernate Tools 3.2.2.GA

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.keeps.core.model.AbstractModelInteger;
import com.keeps.core.model.plus.ModelPlusSchool;

/**
 * TContactRecord generated by hbm2java
 */
@Entity
@Table(name = "t_contact_record")
public class TContactRecord extends AbstractModelInteger implements ModelPlusSchool{

	private Integer id;
	private Integer empid;
	private Integer clientid;
	private String nexttime;
	private String remark;
	private Date contacttime;
	private Date visittime;
	private Integer isvisit;
	private Date createtime;
	private Date updatetime;
	private Integer createperson;
	
	private String empname;
	private String clientname;
	private String contacttimestr;
	private String visittimestr;
	private String updatetimestr;
	private String clientphone;

	private String contactnum;
	private String fzempid;
	private String fzempname;
	
	private String contacttimesta;
	private String contacttimeend;

	public TContactRecord() {
	}

	public TContactRecord(Integer empid) {
		this.empid = empid;
	}

	public TContactRecord(Integer empid, String nexttime, String remark, Date contacttime,
			Date visittime, Date createtime, Integer isvisit, Date updatetime,Integer createperson) {
		this.empid = empid;
		this.nexttime = nexttime;
		this.remark = remark;
		this.contacttime = contacttime;
		this.visittime = visittime;
		this.isvisit = isvisit;
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

	@Column(name = "empid", nullable = false)
	public Integer getEmpid() {
		return this.empid;
	}

	public void setEmpid(Integer empid) {
		this.empid = empid;
	}

	@Column(name = "clientid")
	public Integer getClientid() {
		return clientid;
	}

	public void setClientid(Integer clientid) {
		this.clientid = clientid;
	}
	
	@Column(name = "nexttime")
	public String getNexttime() {
		return this.nexttime;
	}

	public void setNexttime(String nexttime) {
		this.nexttime = nexttime;
	}

	@Column(name = "remark", length = 1255)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "contacttime", length = 19)
	public Date getContacttime() {
		return contacttime;
	}

	public void setContacttime(Date contacttime) {
		this.contacttime = contacttime;
	}

	@Column(name = "visittime", length = 19)
	public Date getVisittime() {
		return visittime;
	}

	public void setVisittime(Date visittime) {
		this.visittime = visittime;
	}

	@Column(name = "isvisit")
	public Integer getIsvisit() {
		return isvisit;
	}

	public void setIsvisit(Integer isvisit) {
		this.isvisit = isvisit;
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

	@Transient
	public String getEmpname() {
		return empname;
	}

	public void setEmpname(String empname) {
		this.empname = empname;
	}

	@Transient
	public String getClientname() {
		return clientname;
	}

	public void setClientname(String clientname) {
		this.clientname = clientname;
	}

	@Transient
	public String getUpdatetimestr() {
		return updatetimestr;
	}

	public void setUpdatetimestr(String updatetimestr) {
		this.updatetimestr = updatetimestr;
	}

	@Transient
	public String getClientphone() {
		return clientphone;
	}

	public void setClientphone(String clientphone) {
		this.clientphone = clientphone;
	}

	@Transient
	public String getContacttimesta() {
		return contacttimesta;
	}

	public void setContacttimesta(String contacttimesta) {
		this.contacttimesta = contacttimesta;
	}

	@Transient
	public String getContacttimeend() {
		return contacttimeend;
	}

	public void setContacttimeend(String contacttimeend) {
		this.contacttimeend = contacttimeend;
	}

	@Transient
	public String getContactnum() {
		return contactnum;
	}

	public void setContactnum(String contactnum) {
		this.contactnum = contactnum;
	}

	@Transient
	public String getFzempid() {
		return fzempid;
	}

	public void setFzempid(String fzempid) {
		this.fzempid = fzempid;
	}

	@Transient
	public String getFzempname() {
		return fzempname;
	}

	public void setFzempname(String fzempname) {
		this.fzempname = fzempname;
	}

	@Transient
	public String getContacttimestr() {
		return contacttimestr;
	}

	public void setContacttimestr(String contacttimestr) {
		this.contacttimestr = contacttimestr;
	}

	@Transient
	public String getVisittimestr() {
		return visittimestr;
	}

	public void setVisittimestr(String visittimestr) {
		this.visittimestr = visittimestr;
	}

	
}
