package com.keeps.webservice.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.keeps.core.controller.AbstractController;
import com.keeps.security.utils.Base64;
import com.keeps.tools.exception.CapecException;
import com.keeps.tools.utils.StringUtils;
import com.keeps.webservice.utils.ReflectUtil;
import com.keeps.webservice.utils.ReturnData;

/** 
 * <p>Title: RegularTasksController.java</p>  
 * <p>Description: 定时任务执行方法统一入口 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年1月16日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@RequestMapping("/regularTasks")
@Controller
public class RegularTasksController extends AbstractController{
	private static final Logger log = Logger.getLogger(RegularTasksController.class);
	public static void main(String[] args) {
		try {
	    	String json = "{\"cmd\":\"loadMarkTest\"}";
	    	System.out.println("加密前"+json);
			json = URLEncoder.encode(json, "UTF-8");
			json = new String(Base64.encode(json.getBytes()));
	    	System.out.println("加密后"+json);
			String base64s = new String(Base64.decode(json));
			json = URLDecoder.decode(base64s, "UTF-8");
	    	System.out.println("解密后"+json);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    }
	
    @RequestMapping("entrance")
	public @ResponseBody String entrance(HttpServletRequest request,HttpServletResponse response){
    	ReturnData ret = new ReturnData();//返回结果实体
		String message = "";
		try {
	    	//处理请求参数
			Map<String, String> param = new HashMap<String, String>();
			param = this.getParamMap4Json(request);
			ret = ReflectUtil.getData(param);
			return new Gson().toJson(ret);
		} catch (CapecException e) {
			message = e.getMessage();
		} catch (Exception e) {
			message = "执行定时任务时发生异常!"+e;
			e.printStackTrace();
		}
		log.error(message);
		ret.setCode("-200");
		ret.setMessage(message);
		ret.setData("");
		return new Gson().toJson(ret);
    }
    
	@SuppressWarnings("unchecked")
	private Map<String, String> getParamMap4Json(HttpServletRequest request) {
		Map<String, String> params = new HashMap<String, String>();
		//get请求 test
		boolean isTest = false;
		Map map = request.getParameterMap();
		if(isTest && map!=null){
			Iterator<Map.Entry<String, String []>> entries = map.entrySet().iterator();  
			while (entries.hasNext()) {  
    		    Map.Entry<String, String []> entry = entries.next(); 
    		    String[] vArr = entry.getValue();
    		    for (int i = 0; i < vArr.length; i++) {
    		    	String key = entry.getKey();
    		    	params.put(key, vArr[i]);
				}
    		}  
		}
		if(!isTest){
			try {
				String json = request.getParameter("reqjson");
				if (StringUtils.notText(json)) {
					return null;
				}
				String base64 = new String(Base64.decode(json));
				json = URLDecoder.decode(base64, "UTF-8");
				params = new Gson().fromJson(json, new TypeToken<Map<String, String>>() { }.getType());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return params;
	}
}
