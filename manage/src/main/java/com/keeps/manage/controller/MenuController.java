package com.keeps.manage.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.keeps.core.controller.AbstractController;
import com.keeps.manage.service.MenuService;
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
public class MenuController extends AbstractController {
	
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
	public ModelAndView index(ModelAndView view,HttpServletRequest request, HttpServletResponse response,TManagerMenu menu) {
		if (menu==null) {
			menu = new TManagerMenu();
		}
		view.addObject("menu",menu);
		view.addObject("menulist",menuService.queryList(menu));
		view.setViewName("manager/menu/list");
		return view;
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
	public ModelAndView add(ModelAndView view,HttpServletRequest request, HttpServletResponse response) {
		view.setViewName("manager/menu/add");
		view.addObject("menulist", menuService.queryListAll(1,0));
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
		view.setViewName("manager/menu/edit");
		view.addObject("menu",menuService.getById(id));
		view.addObject("menulist", menuService.queryListAll(1,0));
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
				arg0.put("message", menuService.save(menu));
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
				arg0.put("message", menuService.update(menu));
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
	
}
