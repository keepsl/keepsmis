package com.keeps.tools.interceptor.pojo;

import com.keeps.tools.utils.CommonUtils;

/** 
 * <p>Title: UploadProgressPojo.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年4月16日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public class UploadProgressPojo {
	private Long pBytesRead = 0L;//已读字节
	private Long pContentLength = 0L;//总字节
	private Integer pItems;//当前文件
	private String mbRead = "0";//已读MB
	private String percent;//已读百分比
	private String speed;//速度
	private Long startReatTime = System.currentTimeMillis();//读取时间
	
	public Long getpBytesRead() {
		return pBytesRead;
	}
	public void setpBytesRead(Long pBytesRead) {
		this.pBytesRead = pBytesRead;
	}
	public Long getpContentLength() {
		return pContentLength;
	}
	public void setpContentLength(Long pContentLength) {
		this.pContentLength = pContentLength;
	}
	public Integer getpItems() {
		return pItems;
	}
	public void setpItems(Integer pItems) {
		this.pItems = pItems;
	}
	public String getPercent() {
		percent = CommonUtils.computePercent(pBytesRead, pContentLength);
		return percent;
	}
	public void setPercent(String percent) {
		this.percent = percent;
	}
	public String getSpeed() {
		speed = CommonUtils.computeProportion(CommonUtils.computeProportion(pBytesRead * 1000, System.currentTimeMillis()- startReatTime), 1000);
		return speed;
	}
	public void setSpeed(String speed) {
		this.speed = speed;
	}
	public String getMbRead() {
		mbRead = CommonUtils.computeProportion(pBytesRead, 1000000);
		return mbRead;
	}
	public void setMbRead(String mbRead) {
		this.mbRead = mbRead;
	}
	public Long getStartReatTime() {
		return startReatTime;
	}
	public void setStartReatTime(Long startReatTime) {
		this.startReatTime = startReatTime;
	}
	

}
