package com.keeps.quartz;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.keeps.security.utils.Base64;

/**
 * 
 * <p>Title: QuartzJob.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年1月16日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@SuppressWarnings("deprecation")
public class QuartzJob implements StatefulJob {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		final String uniformInterface = (String) context.getJobDetail().getJobDataMap().get("uniformInterface");
		final String paramKey = (String) context.getJobDetail().getJobDataMap().get("paramKey");
		final String paramValue = (String) context.getJobDetail().getJobDataMap().get("paramValue");
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(uniformInterface);
		NameValuePair[] data = { new NameValuePair("reqjson", encryptParam(paramKey, paramValue)) };
		postMethod.setRequestBody(data);
		try {
			httpClient.executeMethod(postMethod);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			postMethod.releaseConnection();
		}
	}

	private String encryptParam(String paramKey, String paramValue) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		node.put(paramKey, paramValue);
		String encode = null;
		try {
			encode = URLEncoder.encode(node.toString(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String base64 = new String(Base64.encode(encode.getBytes()));
		return base64;
	}
}
