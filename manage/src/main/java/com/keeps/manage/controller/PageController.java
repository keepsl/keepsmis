package com.keeps.manage.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.keeps.core.controller.AbstractController;
import com.keeps.login.utils.UserLoginUtils;
import com.keeps.manage.service.MenuService;

/**
 * 
 * <p>Title: CookieUtil.java</p>  
 * <p>Description: 首页 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2016年12月6日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Controller
@RequestMapping("page")
public class PageController extends AbstractController {

	@Autowired
	private MenuService menuService;
	
	/**
	  * @Title:			index 
	  * @Description:	进入后台首页
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年1月6日
	 */
	@RequestMapping("index")
	public ModelAndView index(ModelAndView view,HttpServletRequest request, HttpServletResponse response) {
		view.setViewName("page/index");
		view.addObject("menutree",menuService.getIndexMenuTree());
		return view;
	}
	
	/**
	  * @Title:			main 
	  * @Description:	主页面
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年1月6日
	 */
	@RequestMapping("main")
	public ModelAndView main(ModelAndView view,HttpServletRequest request, HttpServletResponse response) {
		view.setViewName("page/main");
		return view;
	}
	
	/**
	  * @Title:			logout 
	  * @Description:	退出
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年1月19日
	 */
    @RequestMapping("logout")
    public String logout(HttpServletRequest request,HttpServletResponse response){
		HttpSession session = request.getSession();
		session.invalidate();
		UserLoginUtils.removeLoginStatus(request, response);
		return "redirect:/login";
    }
	
}
