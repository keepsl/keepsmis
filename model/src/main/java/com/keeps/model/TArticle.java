package com.keeps.model;

// default package
// Generated 2017-1-23 13:33:06 by Hibernate Tools 3.2.2.GA

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
 * TArticle generated by hbm2java
 */
@Entity
@Table(name = "t_article")
public class TArticle extends AbstractModelInteger implements ModelPlusSchool{

	private Integer id;
	private String title;
	private String keyword;
	private String taglist;
	private String coverimage;
	private String abstract_;
	private String content;
	private String contenttext;
	private String source;
	private Integer clicknum;
	private Integer typeid;
	private Integer isshow;
	private Integer ishot;
	private Integer isrecommend;
	private Integer ispublish;
	private Integer isdelete;
	private String publishnickname;
	private String publishtime;
	private Date createtime;
	private Date updatetime;
	private Integer createperson;

	private Integer iscoverimage;
	private String typename;
	private String viewcoverimage;
	public TArticle() {
	}

	public TArticle(String title, String keyword, String taglist, String coverimage, String abstract_, String content,
			String contenttext, String source, Integer clicknum, Integer typeid, Integer isshow, Integer ishot,
			Integer isrecommend, Integer ispublish, Integer isdelete, String publishnickname, String publishtime, Date createtime, Date updatetime, Integer createperson) {
		this.title = title;
		this.keyword = keyword;
		this.taglist = taglist;
		this.coverimage = coverimage;
		this.abstract_ = abstract_;
		this.content = content;
		this.contenttext = contenttext;
		this.source = source;
		this.clicknum = clicknum;
		this.typeid = typeid;
		this.isshow = isshow;
		this.ishot = ishot;
		this.isrecommend = isrecommend;
		this.ispublish = ispublish;
		this.isdelete = isdelete;
		this.publishtime = publishtime;
		this.publishnickname = publishnickname;
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

	@Column(name = "title")
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "keyword")
	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Column(name = "taglist")
	public String getTaglist() {
		return this.taglist;
	}

	public void setTaglist(String taglist) {
		this.taglist = taglist;
	}

	@Column(name = "coverimage", length = 40)
	public String getCoverimage() {
		return this.coverimage;
	}

	public void setCoverimage(String coverimage) {
		this.coverimage = coverimage;
	}

	@Column(name = "abstract", length = 500)
	public String getAbstract_() {
		return this.abstract_;
	}

	public void setAbstract_(String abstract_) {
		this.abstract_ = abstract_;
	}

	@Column(name = "content")
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "contenttext")
	public String getContenttext() {
		return this.contenttext;
	}

	public void setContenttext(String contenttext) {
		this.contenttext = contenttext;
	}

	@Column(name = "source")
	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Column(name = "clicknum")
	public Integer getClicknum() {
		return this.clicknum;
	}

	public void setClicknum(Integer clicknum) {
		this.clicknum = clicknum;
	}

	@Column(name = "typeid")
	public Integer getTypeid() {
		return this.typeid;
	}

	public void setTypeid(Integer typeid) {
		this.typeid = typeid;
	}

	@Column(name = "isshow")
	public Integer getIsshow() {
		return this.isshow;
	}

	public void setIsshow(Integer isshow) {
		this.isshow = isshow;
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

	@Column(name = "ispublish")
	public Integer getIspublish() {
		return ispublish;
	}

	public void setIspublish(Integer ispublish) {
		this.ispublish = ispublish;
	}

	@Column(name = "isdelete")
	public Integer getIsdelete() {
		return isdelete;
	}

	public void setIsdelete(Integer isdelete) {
		this.isdelete = isdelete;
	}

	@Column(name = "publishnickname")
	public String getPublishnickname() {
		return publishnickname;
	}

	public void setPublishnickname(String publishnickname) {
		this.publishnickname = publishnickname;
	}

	@Column(name = "publishtime", length = 19)
	public String getPublishtime() {
		return this.publishtime;
	}

	public void setPublishtime(String publishtime) {
		this.publishtime = publishtime;
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
	public Integer getIscoverimage() {
		return iscoverimage;
	}

	public void setIscoverimage(Integer iscoverimage) {
		this.iscoverimage = iscoverimage;
	}
	
	@Transient
	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}
	
	@Transient
	public String getViewcoverimage() {
		return viewcoverimage;
	}

	public void setViewcoverimage(String viewcoverimage) {
		this.viewcoverimage = viewcoverimage;
	}
	

}
