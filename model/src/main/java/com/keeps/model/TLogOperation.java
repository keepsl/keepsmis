package com.keeps.model;


// default package
// Generated 2017-1-16 10:14:25 by Hibernate Tools 3.2.2.GA

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.keeps.core.model.AbstractModelInteger;

/**
 * TLogOperation generated by hbm2java
 */
@Entity
@Table(name = "t_log_operation")
public class TLogOperation extends AbstractModelInteger implements java.io.Serializable  {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String method;
	private String message;
	private String ip;
	private Integer userid;
	private Date createtime;
	
	private String nickname;

	public TLogOperation() {
	}

	public TLogOperation(String message, String method,String ip,Integer userid, Date createtime) {
		this.message = message;
		this.method = method;
		this.ip = ip;
		this.userid = userid;
		this.createtime = createtime;
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

	@Column(name = "message", length = 2000)
	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@Column(name = "method", length = 255)
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	@Column(name = "ip", length = 20)
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(name = "userid")
	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createtime", length = 19)
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	@Transient
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	
}
