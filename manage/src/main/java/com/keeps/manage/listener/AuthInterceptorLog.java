package com.keeps.manage.listener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.gson.Gson;
import com.keeps.manage.dao.LogOperationDao;
import com.keeps.model.TLogOperation;
import com.keeps.tools.utils.CommonUtils;
import com.keeps.tools.utils.DateUtils;
import com.keeps.tools.utils.threadlocal.UserSchoolThread;


/** 
 * <p>Title: AuthInterceptorLog.java</p>  
 * <p>Description: 日志拦截 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年1月16日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public class AuthInterceptorLog extends HandlerInterceptorAdapter {
	
	@Autowired
	private LogOperationDao logOperationDao;
	
	/**
	 * 处理操作日志
	 */
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            //Method method = handlerMethod.getMethod();
            Map map = request.getParameterMap();
    		Map<String, String> params = new HashMap<String, String>();
    		if(map!=null){
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
    		TLogOperation logOperation = new TLogOperation();
    		Integer userid = UserSchoolThread.get().getUserid();
    		logOperation.setMessage(new Gson().toJson(map));
    		logOperation.setUserid(userid);
    		logOperation.setMethod(request.getServletPath());
    		logOperation.setIp(CommonUtils.getIp(request));
    		logOperation.setCreatetime(DateUtils.getNow());
    		logOperationDao.saveLog(logOperation);
            return true;
        } else {
            return super.preHandle(request, response, handler);
        }
	}
	
	public void postHandle (HttpServletRequest request, HttpServletResponse response, Object handler,ModelAndView modelAndView)throws Exception {
        if (handler instanceof HandlerMethod) {
        	/*HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();*/
        }
	}
}
