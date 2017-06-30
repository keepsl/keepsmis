package com.keeps.crm.listener;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.keeps.crm.utils.ParameterRequestWrapper;

/** 
 * <p>Title: SessionFilter.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年6月20日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public class SessionFilter extends OncePerRequestFilter{
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,  
        HttpServletResponse response, FilterChain filterChain)  
        throws ServletException, IOException {  
        ParameterRequestWrapper requestWrapper = new ParameterRequestWrapper(request);  
        filterChain.doFilter(requestWrapper, response);  
    } 
}
