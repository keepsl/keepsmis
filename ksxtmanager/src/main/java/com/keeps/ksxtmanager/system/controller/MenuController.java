package com.keeps.ksxtmanager.system.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.keeps.core.controller.AbstractAPIController;
import com.keeps.ksxtmanager.system.service.MenuService;
import com.keeps.model.TManagerMenu;
import com.keeps.tools.utils.JsonPost;

/**
 * 
 * <p>Title: MenuController.java</p>  
 * <p>Description: 菜单控制类 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年1月13日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Controller
@RequestMapping("menu")
public class MenuController extends AbstractAPIController {
	
	@Autowired
	private MenuService menuService;
	/**
	  * @Title:			index 
	  * @Description:	菜单首页，查询菜单列表
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年1月12日
	 */
	@RequestMapping("index")
	public ModelAndView index(ModelAndView view,HttpServletRequest request, HttpServletResponse response) {
		view.setViewName("system/menu/list");
		return view;
	}
	
	@RequestMapping("getRightByUrl")
	public @ResponseBody Map getRightByUrl(HttpServletRequest request,String url) {
		Map m = success();
		m.put("status", menuService.countUrlByRole(url));
		return m;
	}
	
	
	/**
	  * @Title:			query 
	  * @Description:	查询菜单列表
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年1月12日
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("query")
	public @ResponseBody Map query(final TManagerMenu menu) {
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map map) {
				map.put("message", menuService.queryList(menu));
			}
		});
	}
	/**
	  * @Title:			add 
	  * @Description:	菜单添加页
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年1月13日
	 */
	@RequestMapping("add")
	public ModelAndView add(ModelAndView view,HttpServletRequest request, HttpServletResponse response,Integer pid) {
		view.setViewName("system/menu/add");
		view.addObject("pid", pid);
		view.addObject("menulist", menuService.queryListAll(1,null));
		return view;
	}
	/**
	  * @Title:			edit 
	  * @Description:	菜单编辑页
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年1月13日
	 */
	@RequestMapping("edit")
	public ModelAndView edit(ModelAndView view,HttpServletRequest request, HttpServletResponse response,Integer id) {
		view.setViewName("system/menu/edit");
		view.addObject("menu",menuService.getById(id));
		view.addObject("menulist", menuService.queryListAll(1,null));
		return view;
	}
 
	/**
	  * @Title:			save 
	  * @Description:	保存菜单
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年1月13日
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("save")
	public @ResponseBody Map save(final TManagerMenu menu){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", menuService.saveOrUpdate(menu));
			}
		});
	}

	/**
	  * @Title:			update
	  * @Description:	更新菜单 
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年1月13日
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("update")
	public @ResponseBody Map update(final TManagerMenu menu){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", menuService.saveOrUpdate(menu));
			}
		});
	}
	
	/**
	  * @Title:			delete 
	  * @Description:	删除菜单
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年1月13日
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("delete")
	public @ResponseBody Map delete(final String ids){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", menuService.delete(ids));
			}
		});
	}
	
	
	/**
	  * @Title:			getMenuTree 
	  * @Description:	获得菜单树
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年1月13日
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("getMenuTree")
	public @ResponseBody Map getMenuTree(final String ids){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", menuService.getMenuTree());
			}
		});
	}
	
	/**
	  * @Title:			roleSelectWin 
	  * @Description:	打开菜单+操作授权页
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年6月19日
	 */
	@RequestMapping("showMenuGrantWin")
	public ModelAndView showMenuGrantWin(ModelAndView view,HttpServletRequest request, HttpServletResponse response,String roleid) {
		view.addObject("roleid", roleid);//传入要授权的角色
		view.setViewName("system/menu/menuGrantWin");
		return view;
	}
	

	/**
	  * @Title:			getMenuGrantTree 
	  * @Description:	获得菜单+操作授权树
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年6月20日
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("getMenuGrantTree")
	public @ResponseBody Map getMenuGrantTree(Integer roleid){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", menuService.getMenuGrantTree(roleid));
			}
		});
	}
	
	/**
	  * @Title:			saveOrgMenuGrant 
	  * @Description:	保存角色菜单权限
	  * @param:
	  * @return: 
	  * @author:		keeps
	  * @data:			2017年6月20日
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("saveOrgMenuGrant")
	public @ResponseBody Map saveOrgMenuGrant(Integer roleid, String idarr, String typearr){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", menuService.saveOrgMenuGrant(roleid, idarr, typearr));
			}
		});
	}
	
}
