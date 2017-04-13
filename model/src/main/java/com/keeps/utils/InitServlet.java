package com.keeps.utils;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/** 
 * <p>Title: InitServlet.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年2月13日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public class InitServlet extends HttpServlet implements ServletContextListener {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(InitServlet.class.getName());
	/**
	 * 加载配置文件
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException{
		
		try{
			logger.info("====================================");
			logger.info("系统初始化......");
			//loadGlobalProperties();
			logger.info("系统初始化结束");
			logger.info("====================================");
		}catch(Exception e){
			logger.error(e);
		}
		
	}
	/**
	 * 服务器将要关闭时
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		arg0.getServletContext().getRealPath("/");
	}
	/**
	 * 服务器启动时
	 */
	public void contextInitialized(ServletContextEvent arg0) {
		try{
			logger.info("====================================");
			logger.info("系统初始化......");
			loadGlobalProperties(arg0);
			logger.info("系统初始化结束");
			logger.info("====================================");
		}catch(Exception e){
			logger.error(e);
		}
	}

	private void loadGlobalProperties(ServletContextEvent arg0) throws Exception{
		InputStream in = null;
		try {
			//项目路径
			Constants.realPath = arg0.getServletContext().getRealPath("/");
			Properties p = PropertiesLoaderUtils.loadAllProperties("system.properties");
			String path = arg0.getServletContext().getRealPath("/").replace("\\", "/");
			//文件上传路径
			Constants.uploadPath = Constants.realPath+File.separator+p.getProperty("keeps_upload_path");
			Constants.file_upload_path = p.getProperty("file.upload.path");
			Constants.file_view_path = p.getProperty("file.view.path");
		} catch (Exception e) {
			logger.error(e);
		}finally{
			if(in!=null)
				in.close();
		}
	}
	
}
