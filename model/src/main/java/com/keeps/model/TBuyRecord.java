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
 * TBuyRecord generated by hbm2java
 */
@Entity
@Table(name = "t_buy_record")
public class TBuyRecord extends AbstractModelInteger implements ModelPlusSchool{

	private Integer id;
	private Integer empid;
	private Integer clientid;
	private Integer dicttypeid;
	private Integer dictid;
	private String productname;
	private Float price;
	private Date createtime;
	private Date updatetime;
	private String remark;
	private Integer createperson;

	private String empname;
	private String clientname;
	private String updatetimestr;
	private String clientphone;

	private String buynum;
	private String fzempid;
	private String fzempname;
	
	private String buytimesta;
	private String buytimeend;
	
	public TBuyRecord() {
	}

	public TBuyRecord(Integer empid) {
		this.empid = empid;
	}

	public TBuyRecord(Integer empid, Integer dicttypeid, Integer dictid, String productname, Float price, String remark,Date createtime, Date updatetime, Integer createperson) {
		this.empid = empid;
		this.dicttypeid = dicttypeid;
		this.dictid = dictid;
		this.productname = productname;
		this.price = price;
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

	@Column(name = "remark", length = 1255)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	@Column(name = "dicttypeid")
	public Integer getDicttypeid() {
		return dicttypeid;
	}

	public void setDicttypeid(Integer dicttypeid) {
		this.dicttypeid = dicttypeid;
	}

	@Column(name = "dictid")
	public Integer getDictid() {
		return dictid;
	}

	public void setDictid(Integer dictid) {
		this.dictid = dictid;
	}

	@Column(name = "productname")
	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	@Column(name = "price")
	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
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
	public String getBuytimesta() {
		return buytimesta;
	}

	public void setBuytimesta(String buytimesta) {
		this.buytimesta = buytimesta;
	}

	@Transient
	public String getBuytimeend() {
		return buytimeend;
	}

	public void setBuytimeend(String buytimeend) {
		this.buytimeend = buytimeend;
	}

	@Transient
	public String getBuynum() {
		return buynum;
	}

	public void setBuynum(String buynum) {
		this.buynum = buynum;
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

	
}
