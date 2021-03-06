package com.keeps.model;

// default package
// Generated 2017-4-12 16:33:33 by Hibernate Tools 3.2.2.GA

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.keeps.core.model.AbstractModelInteger;
import com.keeps.core.model.plus.ModelPlusSchool;

/**
 * TAdvPosition generated by hbm2java
 */
@Entity
@Table(name = "t_adv_position")
public class TAdvPosition extends AbstractModelInteger implements ModelPlusSchool{

	private Integer id;
	private String apName;
	private String apIntro;
	private Integer apClass;
	private Integer apDisplay;
	private Integer isShow;
	private Integer apWidth;
	private Integer apHeight;
	private Float apPrice;
	private Integer advNum;
	private Integer clickNum;
	private String defaultContent;
	private Integer isCache;
	private Date createtime;
	private Date updatetime;
	private Integer createperson;
	private Integer apWidthWord;//文字字数 -》apWidth
	private String defaultContentWord;//默认文字 -》defaultContent
	public TAdvPosition() {
	}
	
	public TAdvPosition(String apName, String apIntro, Integer apClass, Integer apDisplay, Integer isShow, Integer apWidth,
			Integer apHeight, Float apPrice, Integer advNum, Integer clickNum, String defaultContent, Integer isCache, Date createtime, Date updatetime, Integer createperson) {
		this.apName = apName;
		this.apIntro = apIntro;
		this.apClass = apClass;
		this.apDisplay = apDisplay;
		this.isShow = isShow;
		this.apWidth = apWidth;
		this.apHeight = apHeight;
		this.apPrice = apPrice;
		this.advNum = advNum;
		this.clickNum = clickNum;
		this.defaultContent = defaultContent;
		this.isCache = isCache;
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

	@Column(name = "ap_name", nullable = false, length = 100)
	public String getApName() {
		return this.apName;
	}

	public void setApName(String apName) {
		this.apName = apName;
	}

	@Column(name = "ap_intro")
	public String getApIntro() {
		return this.apIntro;
	}

	public void setApIntro(String apIntro) {
		this.apIntro = apIntro;
	}

	@Column(name = "ap_class", nullable = false)
	public Integer getApClass() {
		return this.apClass;
	}

	public void setApClass(Integer apClass) {
		this.apClass = apClass;
	}

	@Column(name = "ap_display", nullable = false)
	public Integer getApDisplay() {
		return this.apDisplay;
	}

	public void setApDisplay(Integer apDisplay) {
		this.apDisplay = apDisplay;
	}

	@Column(name = "is_show", nullable = false)
	public Integer getIsShow() {
		return this.isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	@Column(name = "ap_width", nullable = false)
	public Integer getApWidth() {
		return this.apWidth;
	}

	public void setApWidth(Integer apWidth) {
		this.apWidth = apWidth;
	}

	@Column(name = "ap_height", nullable = false)
	public Integer getApHeight() {
		return this.apHeight;
	}

	public void setApHeight(Integer apHeight) {
		this.apHeight = apHeight;
	}

	@Column(name = "ap_price", nullable = false, precision = 10, scale = 2)
	public Float getApPrice() {
		return this.apPrice;
	}

	public void setApPrice(Float apPrice) {
		this.apPrice = apPrice;
	}

	@Column(name = "adv_num", nullable = false)
	public Integer getAdvNum() {
		return this.advNum;
	}

	public void setAdvNum(Integer advNum) {
		this.advNum = advNum;
	}

	@Column(name = "click_num", nullable = false)
	public Integer getClickNum() {
		return this.clickNum;
	}

	public void setClickNum(Integer clickNum) {
		this.clickNum = clickNum;
	}

	@Column(name = "default_content", length = 100)
	public String getDefaultContent() {
		return this.defaultContent;
	}

	public void setDefaultContent(String defaultContent) {
		this.defaultContent = defaultContent;
	}

	@Column(name = "is_cache", nullable = false)
	public Integer getIsCache() {
		return isCache;
	}

	public void setIsCache(Integer isCache) {
		this.isCache = isCache;
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
	public Integer getApWidthWord() {
		return apWidthWord;
	}

	public void setApWidthWord(Integer apWidthWord) {
		this.apWidthWord = apWidthWord;
	}

	@Transient
	public String getDefaultContentWord() {
		return defaultContentWord;
	}

	public void setDefaultContentWord(String defaultContentWord) {
		this.defaultContentWord = defaultContentWord;
	}

}
