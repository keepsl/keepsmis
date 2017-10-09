package com.keeps.ksxtmanager.system.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.keeps.core.controller.AbstractController;
import com.keeps.ksxtmanager.system.service.MenuService;
import com.keeps.ksxtmanager.system.service.NewsService;
import com.keeps.ksxtmanager.system.service.RoleService;
import com.keeps.ksxtmanager.system.service.ScheduleService;
import com.keeps.login.utils.UserLoginUtils;
import com.keeps.model.TClient;
import com.keeps.model.TNews;
import com.keeps.model.TSchedule;
import com.keeps.tools.utils.threadlocal.UserSchoolThread;
import com.keeps.utils.FileUtil;

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
	@Autowired
	private RoleService roleService;
	@Autowired
	private NewsService newsService;
	@Autowired
	private ScheduleService scheduleService;
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
		view.addObject("username", UserSchoolThread.get().getNickname());
		view.addObject("rolename", roleService.getRolenamesByEmpid());
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
		//查询公告
		TNews news = new TNews();
		news.setPage(1);
		news.setRows(20);
		view.addObject("newslist", newsService.query(news));
		
		TSchedule schedule = new TSchedule();
		schedule.setStatus(-1);
		view.addObject("schedulelist", scheduleService.query(schedule));
		
		view.addObject("menutree",menuService.getIndexMenuTree());
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
    


	@RequestMapping("downloadkj")
	public void downloadkj(HttpServletRequest request,HttpServletResponse response){
		String filepath = request.getSession().getServletContext().getRealPath("/skins/template/download/kuaijiefangshi.html");
		String fileName = "客户管理平台.html";
		FileUtil.downloadFile(filepath, fileName,response);
	}
	
}
