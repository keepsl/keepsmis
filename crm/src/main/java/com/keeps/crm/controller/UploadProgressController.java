package com.keeps.crm.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.keeps.core.controller.AbstractController;
import com.keeps.tools.interceptor.pojo.UploadProgressPojo;

/** 
 * <p>Title: UploadProgressController.java</p>  
 * <p>Description: 获取上传进度 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年4月16日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public class UploadProgressController extends AbstractController {
	
	@RequestMapping("upfile/progress")
	public @ResponseBody String initCreateInfo(HttpServletRequest request) {
		UploadProgressPojo progress = (UploadProgressPojo) request.getSession().getAttribute("uploadprogress");
		System.out.println("总字节："+progress.getpBytesRead());
		System.out.println("已读字节："+progress.getpContentLength());
		System.out.println("当前文件："+progress.getpItems());
		System.out.println("已读MB："+progress.getMbRead());
		System.out.println("已读百分比："+progress.getPercent());
		System.out.println("速度："+progress.getSpeed());
		return new Gson().toJson(progress);
	}
	
}
