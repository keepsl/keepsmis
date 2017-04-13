package com.keeps.tools.utils.html;

import java.util.concurrent.ConcurrentHashMap;

import com.keeps.tools.common.SoftUtils;
import com.keeps.tools.utils.Assert;
import com.keeps.tools.utils.FileUtils;
import com.keeps.tools.utils.StringUtils;
import com.keeps.tools.utils.velocity.VelocityStaticView;

/** 
 * <p>Title: HtmlCreator.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年4月8日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public class HtmlCreator implements SoftUtils{
	private String dir;
	  private static ConcurrentHashMap<String, Long> vector = new ConcurrentHashMap();

	  public static synchronized void put(String url, Long time) {
	    vector.put(url, time);
	  }

	  public static ConcurrentHashMap<String, Long> get() {
	    return vector;
	  }

	  public void init() {
	    Assert.isTrue(StringUtils.notText(this.dir), "HtmlCreator不能缺少dir");

	    VelocityStaticView.setHtmlCreator(this);
	  }

	  public String getDir() {
	    return this.dir;
	  }

	  public void setDir(String dir) {
	    this.dir = dir;
	  }

	  public synchronized void remove(String key) {
	    FileUtils.removeFile(this.dir + key);
	  }
}
