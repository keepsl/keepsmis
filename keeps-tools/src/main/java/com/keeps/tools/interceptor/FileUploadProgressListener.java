package com.keeps.tools.interceptor;

import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.ProgressListener;

import com.keeps.tools.interceptor.pojo.UploadProgressPojo;

public class FileUploadProgressListener implements ProgressListener{
	private HttpSession session;

    public FileUploadProgressListener(HttpSession session) {
        this.session=session;
        //实例化 开始读取了。
        UploadProgressPojo progress = new UploadProgressPojo();
        session.setAttribute("uploadprogress", progress);  

    }

    /**
     *  pBytesRead 到目前为止读取文件字节
     *  pContentLength 文件总字节
     *  pItems 目前正在读取第几个文件
     */
	@Override
	public void update(long pBytesRead, long pContentLength, int pItems) {
		UploadProgressPojo progress = (UploadProgressPojo) session.getAttribute("uploadprogress");
		progress.setpBytesRead(pBytesRead);
		progress.setpContentLength(pContentLength);
		progress.setpItems(pItems);
		session.setAttribute("uploadprogress", progress);
	}
}
