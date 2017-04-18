package com.keeps.model;

// default package
// Generated 2017-4-5 23:10:06 by Hibernate Tools 3.2.2.GA

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.keeps.core.model.AbstractModelLong;
import com.keeps.core.model.plus.ModelPlusSchool;

/**
 * SzlGoods generated by hbm2java
 */
@Entity
@Table(name = "szl_goods")
public class SzlGoods extends AbstractModelLong implements ModelPlusSchool{

	private Long id;
	private Integer pclassid;
	private Integer classid;
	private String goodsname;
	private String origurl;
	private String toshorturl;
	private String  tolongurl;
	private String qrcodepath;
	private String taokouling;
	private String tocouponsurl;
	private String goodsimage;
	private Float originalprice;
	private Float currentprice;
	private Float couponafterprice;
	private Float couponprice;
	private String starttime;
	private String endtime;
	private Integer realtocouponnum;
	private Integer tocouponnum;
	private String taglistid;
	private String taglistname;
	private Integer goodssource;
	private Integer ishot;
	private Integer isrecommend;
	private Integer isdelete;
	private String description;
	private Date createtime;
	private Date updatetime;
	private Integer createperson;

	private String viewgoodsimage;
	private String viewqrcodepath;
	private String classname;
	private Integer searchtype;//查询类型，1正常出售商品，2到期商品

	public SzlGoods() {
	}

	public SzlGoods(Long id, Integer pclassid, Integer classid, String goodsname, String origurl, String toshorturl, String tolongurl, String qrcodepath, 
			String taokouling, String tocouponsurl, String goodsimage, Float currentprice,
			Float couponafterprice, Float couponprice, String starttime, String endtime, Integer realtocouponnum,
			Integer tocouponnum, String taglistid, String taglistname,
			Integer goodssource, String description, Date createtime, Date updatetime, Integer createperson) {
		this.id = id;
		this.pclassid = pclassid;
		this.classid = classid;
		this.goodsname = goodsname;
		this.origurl = origurl;
		this.toshorturl = toshorturl;
		this.tolongurl = tolongurl;
		this.qrcodepath = qrcodepath;
		this.taokouling = taokouling;
		this.tocouponsurl = tocouponsurl;
		this.goodsimage = goodsimage;
		this.currentprice = currentprice;
		this.couponafterprice = couponafterprice;
		this.couponprice = couponprice;
		this.starttime = starttime;
		this.endtime = endtime;
		this.realtocouponnum = realtocouponnum;
		this.tocouponnum = tocouponnum;
		this.taglistid = taglistid;
		this.taglistname = taglistname;
		this.goodssource = goodssource;
		this.description = description;
		this.createtime = createtime;
		this.updatetime = updatetime;
		this.createperson = createperson;
	}
	public SzlGoods(Long id, Integer pclassid, Integer classid, String goodsname, String origurl, String toshorturl, String tolongurl, String qrcodepath, 
			String taokouling, String tocouponsurl, String goodsimage, Float originalprice,
			Float currentprice, Float couponafterprice, Float couponprice, String starttime, String endtime, Integer realtocouponnum, Integer tocouponnum,
			String taglistid, String taglistname, Integer goodssource, Integer ishot, Integer isrecommend, Integer isdelete, String description, Date createtime,
			Date updatetime, Integer createperson) {
		this.id = id;
		this.pclassid = pclassid;
		this.classid = classid;
		this.goodsname = goodsname;
		this.origurl = origurl;
		this.toshorturl = toshorturl;
		this.tolongurl = tolongurl;
		this.qrcodepath = qrcodepath;
		this.taokouling = taokouling;
		this.tocouponsurl = tocouponsurl;
		this.goodsimage = goodsimage;
		this.originalprice = originalprice;
		this.currentprice = currentprice;
		this.couponafterprice = couponafterprice;
		this.starttime = starttime;
		this.endtime = endtime;
		this.realtocouponnum = realtocouponnum;
		this.tocouponnum = tocouponnum;
		this.taglistid = taglistid;
		this.taglistname = taglistname;
		this.goodssource = goodssource;
		this.ishot = ishot;
		this.isrecommend = isrecommend;
		this.isdelete = isdelete;
		this.description = description;
		this.createtime = createtime;
		this.updatetime = updatetime;
		this.createperson = createperson;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "pclassid", nullable = false)
	public Integer getPclassid() {
		return pclassid;
	}

	public void setPclassid(Integer pclassid) {
		this.pclassid = pclassid;
	}

	@Column(name = "classid", nullable = false)
	public Integer getClassid() {
		return this.classid;
	}

	public void setClassid(Integer classid) {
		this.classid = classid;
	}

	@Column(name = "goodsname", nullable = false)
	public String getGoodsname() {
		return this.goodsname;
	}

	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}

	@Column(name = "origurl")
	public String getOrigurl() {
		return origurl;
	}

	public void setOrigurl(String origurl) {
		this.origurl = origurl;
	}

	@Column(name = "toshorturl")
	public String getToshorturl() {
		return toshorturl;
	}

	public void setToshorturl(String toshorturl) {
		this.toshorturl = toshorturl;
	}

	@Column(name = "tolongurl", nullable = false)
	public String getTolongurl() {
		return tolongurl;
	}

	public void setTolongurl(String tolongurl) {
		this.tolongurl = tolongurl;
	}

	@Column(name = "qrcodepath")
	public String getQrcodepath() {
		return qrcodepath;
	}

	public void setQrcodepath(String qrcodepath) {
		this.qrcodepath = qrcodepath;
	}

	@Column(name = "taokouling")
	public String getTaokouling() {
		return taokouling;
	}

	public void setTaokouling(String taokouling) {
		this.taokouling = taokouling;
	}

	@Column(name = "tocouponsurl")
	public String getTocouponsurl() {
		return tocouponsurl;
	}

	public void setTocouponsurl(String tocouponsurl) {
		this.tocouponsurl = tocouponsurl;
	}

	@Column(name = "goodsimage", nullable = false, length = 40)
	public String getGoodsimage() {
		return this.goodsimage;
	}

	public void setGoodsimage(String goodsimage) {
		this.goodsimage = goodsimage;
	}

	@Column(name = "originalprice")
	public Float getOriginalprice() {
		return this.originalprice;
	}

	public void setOriginalprice(Float originalprice) {
		this.originalprice = originalprice;
	}

	@Column(name = "currentprice", nullable = false)
	public Float getCurrentprice() {
		return this.currentprice;
	}

	public void setCurrentprice(Float currentprice) {
		this.currentprice = currentprice;
	}

	@Column(name = "couponafterprice", nullable = false)
	public Float getCouponafterprice() {
		return this.couponafterprice;
	}

	public void setCouponafterprice(Float couponafterprice) {
		this.couponafterprice = couponafterprice;
	}

	@Column(name = "couponprice", nullable = false)
	public Float getCouponprice() {
		return this.couponprice;
	}

	public void setCouponprice(Float couponprice) {
		this.couponprice = couponprice;
	}

	@Column(name = "starttime", nullable = false, length = 19)
	public String getStarttime() {
		return this.starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	@Column(name = "endtime", nullable = false, length = 19)
	public String getEndtime() {
		return this.endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	@Column(name = "realtocouponnum", nullable = false)
	public Integer getRealtocouponnum() {
		return realtocouponnum;
	}

	public void setRealtocouponnum(Integer realtocouponnum) {
		this.realtocouponnum = realtocouponnum;
	}

	@Column(name = "tocouponnum", nullable = false)
	public Integer getTocouponnum() {
		return this.tocouponnum;
	}

	public void setTocouponnum(Integer tocouponnum) {
		this.tocouponnum = tocouponnum;
	}

	@Column(name = "taglistid")
	public String getTaglistid() {
		return taglistid;
	}

	public void setTaglistid(String taglistid) {
		this.taglistid = taglistid;
	}

	@Column(name = "taglistname")
	public String getTaglistname() {
		return taglistname;
	}

	public void setTaglistname(String taglistname) {
		this.taglistname = taglistname;
	}

	
	@Column(name = "goodssource", nullable = false)
	public Integer getGoodssource() {
		return this.goodssource;
	}

	public void setGoodssource(Integer goodssource) {
		this.goodssource = goodssource;
	}

	@Column(name = "ishot")
	public Integer getIshot() {
		return this.ishot;
	}

	public void setIshot(Integer ishot) {
		this.ishot = ishot;
	}

	@Column(name = "isrecommend")
	public Integer getIsrecommend() {
		return this.isrecommend;
	}

	public void setIsrecommend(Integer isrecommend) {
		this.isrecommend = isrecommend;
	}

	@Column(name = "isdelete")
	public Integer getIsdelete() {
		return this.isdelete;
	}

	public void setIsdelete(Integer isdelete) {
		this.isdelete = isdelete;
	}

	@Column(name = "description", nullable = false)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	@Column(name = "createperson", nullable = false)
	public Integer getCreateperson() {
		return this.createperson;
	}

	public void setCreateperson(Integer createperson) {
		this.createperson = createperson;
	}

	@Transient
	public String getViewgoodsimage() {
		return viewgoodsimage;
	}

	public void setViewgoodsimage(String viewgoodsimage) {
		this.viewgoodsimage = viewgoodsimage;
	}

	@Transient
	public String getViewqrcodepath() {
		return viewqrcodepath;
	}

	public void setViewqrcodepath(String viewqrcodepath) {
		this.viewqrcodepath = viewqrcodepath;
	}

	@Transient
	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	@Transient
	public Integer getSearchtype() {
		return searchtype;
	}

	public void setSearchtype(Integer searchtype) {
		this.searchtype = searchtype;
	}

}
