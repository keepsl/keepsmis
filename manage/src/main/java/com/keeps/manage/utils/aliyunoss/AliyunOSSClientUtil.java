package com.keeps.manage.utils.aliyunoss;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import org.apache.log4j.Logger;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;

/**
 * <p>
 * Title: AliyunOSSClientUtil.java
 * </p>
 * <p>
 * Description: @TODO
 * </p>
 * <p>
 * Copyright: Copyright (c) KEEPS
 * </p>
 * 
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年4月27日 修改日期： 修改人： 复审人：
 */
public class AliyunOSSClientUtil {
	// log日志
	private static Logger logger = Logger.getLogger(AliyunOSSClientUtil.class);
	// 阿里云API的内或外网域名
	private static String ENDPOINT;
	// 阿里云API的密钥Access Key ID
	private static String ACCESS_KEY_ID;
	// 阿里云API的密钥Access Key Secret
	private static String ACCESS_KEY_SECRET;
	// 阿里云API的bucket名称
	private static String BACKET_NAME;
	// 阿里云API的文件夹名称
	private static String FOLDER;
	// 初始化属性
	static {
		ENDPOINT = OSSClientConstants.ENDPOINT;
		ACCESS_KEY_ID = OSSClientConstants.ACCESS_KEY_ID;
		ACCESS_KEY_SECRET = OSSClientConstants.ACCESS_KEY_SECRET;
		BACKET_NAME = OSSClientConstants.BACKET_NAME;
		FOLDER = OSSClientConstants.FOLDER;
	}

	/**
	 * 获取阿里云OSS客户端对象
	 * 
	 * @return ossClient
	 */
	public static OSSClient getOSSClient() {
		return new OSSClient(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
	}

	/**
	 * 创建存储空间
	 * 
	 * @param ossClient
	 *            OSS连接
	 * @param bucketName
	 *            存储空间
	 * @return
	 */
	public static String createBucketName(OSSClient ossClient, String bucketName) {
		// 存储空间
		final String bucketNames = bucketName;
		if (!ossClient.doesBucketExist(bucketName)) {
			// 创建存储空间
			Bucket bucket = ossClient.createBucket(bucketName);
			logger.info("创建存储空间成功");
			return bucket.getName();
		}
		return bucketNames;
	}

	/**
	 * 删除存储空间buckName
	 * 
	 * @param ossClient
	 *            oss对象
	 * @param bucketName
	 *            存储空间
	 */
	public static void deleteBucket(OSSClient ossClient, String bucketName) {
		ossClient.deleteBucket(bucketName);
		logger.info("删除" + bucketName + "Bucket成功");
	}

	/**
	 * 创建模拟文件夹
	 * 
	 * @param ossClient
	 *            oss连接
	 * @param bucketName
	 *            存储空间
	 * @param folder
	 *            模拟文件夹名如"qj_nanjing/"
	 * @return 文件夹名
	 */
	public static String createFolder(OSSClient ossClient, String bucketName, String folder) {
		// 文件夹名
		final String keySuffixWithSlash = folder;
		// 判断文件夹是否存在，不存在则创建
		if (!ossClient.doesObjectExist(bucketName, keySuffixWithSlash)) {
			// 创建文件夹
			ossClient.putObject(bucketName, keySuffixWithSlash, new ByteArrayInputStream(new byte[0]));
			logger.info("创建文件夹成功");
			// 得到文件夹名
			OSSObject object = ossClient.getObject(bucketName, keySuffixWithSlash);
			String fileDir = object.getKey();
			return fileDir;
		}
		return keySuffixWithSlash;
	}

	/**
	  * @Title:			deleteFile 
	  * @Description:	删除文件
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年4月27日
	 */
	public static void deleteFile(String filepath, String key) {
		OSSClient ossClient = AliyunOSSClientUtil.getOSSClient();
		try {
			ossClient.deleteObject(BACKET_NAME,FOLDER + filepath + "/" + key);
		} catch (Exception e) {
			logger.error("删除阿里云OSS服务器异常." + e.getMessage(), e);
		} finally {
			ossClient.shutdown();
		}
	}
	/**
	 * 根据key删除OSS服务器上的文件
	 * 
	 * @param ossClient
	 *            oss连接
	 * @param bucketName
	 *            存储空间
	 * @param folder
	 *            模拟文件夹名 如"qj_nanjing/"
	 * @param key
	 *            Bucket下的文件的路径名+文件名 如："upload/cake.jpg"
	 */
	public static void deleteFile(OSSClient ossClient, String bucketName, String folder, String key) {
		ossClient.deleteObject(bucketName, folder + key);
		logger.info("删除" + bucketName + "下的文件" + folder + key + "成功");
	}

	public static String uploadObject2OSS(byte[] content,String filepath,String filename) {
		String resultStr = null;
		// 初始化OSSClient
		OSSClient ossClient = AliyunOSSClientUtil.getOSSClient();
		try {
			// 上传文件 (上传文件流的形式)
			PutObjectResult putResult = ossClient.putObject(BACKET_NAME, FOLDER + filepath + "/"+ filename, new ByteArrayInputStream(content));
			// 解析结果
			resultStr = putResult.getETag();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("上传阿里云OSS服务器异常." + e.getMessage(), e);
		} finally {
			ossClient.shutdown();
		}
		return resultStr;
	}


	public static void cutUploadObject2OSS(InputStream is,String filepath,String filename){
		// 初始化OSSClient
		OSSClient ossClient = AliyunOSSClientUtil.getOSSClient();
		try {
			String style = "image/crop,w_100,h_100,x_100,y_100,r_1"; 
			GetObjectRequest request = new GetObjectRequest(BACKET_NAME, filepath+"/"+filename);
		    request.setProcess(style);
		    ossClient.getObject(request, new File("cut"+filename));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("上传阿里云OSS服务器异常." + e.getMessage(), e);
		} finally {
			ossClient.shutdown();
		}
	}
	public static String uploadObject2OSS(InputStream is,String filepath,String filename) {
		String resultStr = null;
		// 初始化OSSClient
		OSSClient ossClient = AliyunOSSClientUtil.getOSSClient();
		try {
			// 上传文件 (上传文件流的形式)
			PutObjectResult putResult = ossClient.putObject(BACKET_NAME, FOLDER + filepath+"/"+filename, is);
			// 解析结果
			resultStr = putResult.getETag();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("上传阿里云OSS服务器异常." + e.getMessage(), e);
		} finally {
			ossClient.shutdown();
		}
		return resultStr;
	}
	/**
	 * 上传图片至OSS
	 * 
	 * @param ossClient
	 *            oss连接
	 * @param file
	 *            上传文件（文件全路径如：D:\\image\\cake.jpg）
	 * @param bucketName
	 *            存储空间
	 * @param folder
	 *            模拟文件夹名 如"keeps/"
	 * @return String 返回的唯一MD5数字签名
	 */
	public static String uploadObject2OSS(File file) {
		String resultStr = null;
		// 初始化OSSClient
		OSSClient ossClient = AliyunOSSClientUtil.getOSSClient();
		try {
			// 以输入流的形式上传文件
			InputStream is = new FileInputStream(file);
			// String filetype =
			// file.getName().substring(file.getName().lastIndexOf(StringUtils.Symbol.POINT));
			// 文件名
			String fileName = file.getName();
			// 文件大小
			Long fileSize = file.length();
			// 创建上传Object的Metadata
			ObjectMetadata metadata = new ObjectMetadata();
			// 上传的文件的长度
			metadata.setContentLength(is.available());
			// 指定该Object被下载时的网页的缓存行为
			metadata.setCacheControl("no-cache");
			// 指定该Object下设置Header
			metadata.setHeader("Pragma", "no-cache");
			// 指定该Object被下载时的内容编码格式
			metadata.setContentEncoding("utf-8");
			// 文件的MIME，定义文件的类型及网页编码，决定浏览器将以什么形式、什么编码读取文件。如果用户没有指定则根据Key或文件名的扩展名生成，
			// 如果没有扩展名则填默认值application/octet-stream
			metadata.setContentType(getContentType(fileName));
			// 指定该Object被下载时的名称（指示MINME用户代理如何显示附加的文件，打开或下载，及文件名称）
			metadata.setContentDisposition("filename/filesize=" + fileName + "/" + fileSize + "Byte.");
			// 上传文件 (上传文件流的形式)
			PutObjectResult putResult = ossClient.putObject(BACKET_NAME, FOLDER + fileName, is, metadata);
			// 解析结果
			resultStr = putResult.getETag();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("上传阿里云OSS服务器异常." + e.getMessage(), e);
		} finally {
			ossClient.shutdown();
		}
		return resultStr;
	}

	/**
	 * 通过文件名判断并获取OSS服务文件上传时文件的contentType
	 * 
	 * @param fileName
	 *            文件名
	 * @return 文件的contentType
	 */
	public static String getContentType(String fileName) {
		// 文件的后缀名
		String fileExtension = fileName.substring(fileName.lastIndexOf("."));
		if (".bmp".equalsIgnoreCase(fileExtension)) {
			return "image/bmp";
		}
		if (".gif".equalsIgnoreCase(fileExtension)) {
			return "image/gif";
		}
		if (".jpeg".equalsIgnoreCase(fileExtension) || ".jpg".equalsIgnoreCase(fileExtension)
				|| ".png".equalsIgnoreCase(fileExtension)) {
			return "image/jpeg";
		}
		if (".html".equalsIgnoreCase(fileExtension)) {
			return "text/html";
		}
		if (".txt".equalsIgnoreCase(fileExtension)) {
			return "text/plain";
		}
		if (".vsd".equalsIgnoreCase(fileExtension)) {
			return "application/vnd.visio";
		}
		if (".ppt".equalsIgnoreCase(fileExtension) || "pptx".equalsIgnoreCase(fileExtension)) {
			return "application/vnd.ms-powerpoint";
		}
		if (".doc".equalsIgnoreCase(fileExtension) || "docx".equalsIgnoreCase(fileExtension)) {
			return "application/msword";
		}
		if (".xml".equalsIgnoreCase(fileExtension)) {
			return "text/xml";
		}
		// 默认返回类型
		return "image/jpeg";
	}

	// 测试
	public static void main(String[] args) {
		
		//String md5key = AliyunOSSClientUtil.uploadObject2OSS("bbbbb".getBytes(),"sfdsd/sss.js");
		/*// 上传文件
		String files = "D:\\images\\111.jpg,D:\\images\\222.jpg,D:\\images\\banner_1.jpg,D:\\images\\layout_adv_27.js";
		String[] file = files.split(",");
		for (String filename : file) {
			// System.out.println("filename:"+filename);
			File filess = new File(filename);
			String md5key = AliyunOSSClientUtil.uploadObject2OSS(filess);
			System.out.println(md5key);
			logger.info("上传后的文件MD5数字唯一签名:" + md5key);
			// 上传后的文件MD5数字唯一签名:40F4131427068E08451D37F02021473A
		}
*/
	}
}
