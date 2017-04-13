package com.keeps.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * <p>Title: FileUtil.java</p>  
 * <p>Description: 文件操作工具类 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年2月7日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public class FileUtil {
	
	/**
	  * @Title:			downloadFiles 
	  * @Description:	打包下载多文件
	  * @param:			filePaths文件路径数组  fileNames文件名称数组 zipname压缩包名称
	  * @return: 
	  * @author:		李园园
	  * @data:			2016年10月20日
	 */
	public static boolean downloadFiles(String[] filePaths,String[] fileNames,String zipname,HttpServletRequest request,HttpServletResponse response){
	    ZipOutputStream zipout = null;   
		try{
			String userAgent=request.getHeader("USER-AGENT");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/msword;charset=UTF-8;");
			String zipName = BrowserCheck.encodeText(userAgent, zipname);
			response.setHeader("Content-disposition","attachment; filename="+zipName);
		    zipout = new ZipOutputStream(response.getOutputStream());
			for (int i = 0; i < filePaths.length; i++) {
				String filePath = filePaths[i];
				File file = new File(filePath);
				if(file.exists()){
					zipout.putNextEntry(new ZipEntry(fileNames[i]));
					FileInputStream fis = new FileInputStream(file);
					int len;
					while((len=fis.read())!=-1){
						zipout.write(len);
					}
					zipout.closeEntry();
					fis.close();
				}
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				if(zipout!=null)
					zipout.close();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}
		return false;
	}	
	/**
	  * @Title:			downloadFile 
	  * @Description:	下载文件
	  * @param:			filePath文件路径  fileName文件名称
	  * @return: 
	  * @author:		keeps
	  * @data:			2016年8月26日
	 */
	public static boolean downloadFile(String filePath,String fileName,HttpServletResponse response){
		File file = new File(filePath);
		FileInputStream is = null;
		OutputStream os = null;
		try{
			if(file.exists()){
				is = new FileInputStream(file);
				os = response.getOutputStream();
				response.reset();
				response.setCharacterEncoding("gb2312");
				response.setHeader("Content-Disposition","attachment;filename="+new String((fileName).getBytes("GBK"),"ISO8859_1"));
				int i;
				while((i=is.read())!=-1){
					os.write(i);
				}
				return true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				if(is!=null)
					is.close();
				if(os!=null)
					os.close();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}
		return false;
	}
	
	/**
     * 输入流转字节
     * @param inStream
     * @return
     * @throws IOException
     */
    public static final byte[] input2byte(InputStream inStream)  
            throws IOException {  
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();  
        byte[] buff = new byte[100];  
        int rc = 0;  
        while ((rc = inStream.read(buff, 0, 100)) > 0) {  
            swapStream.write(buff, 0, rc);  
        }  
        byte[] in2b = swapStream.toByteArray();  
        return in2b;  
    }
}
