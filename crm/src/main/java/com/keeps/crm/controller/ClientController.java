package com.keeps.crm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.keeps.core.controller.AbstractController;
import com.keeps.crm.service.ClientService;
import com.keeps.crm.service.DictService;
import com.keeps.crm.service.EmpService;
import com.keeps.crm.service.impl.ClientServiceImpl;
import com.keeps.model.TClient;
import com.keeps.model.TEmp;
import com.keeps.tools.utils.JsonPost;
import com.keeps.tools.utils.threadlocal.UserSchoolThread;
import com.keeps.utils.Constants;
import com.keeps.utils.FileUtil;

/** 
 * <p>Title: ClientController.java</p>  
 * <p>Description: 客户管理控制类 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年6月23日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Controller
@RequestMapping("client")
public class ClientController extends AbstractController {
	
	@Autowired
	private ClientService clientService;
	@Autowired
	private DictService dictService;
	@Autowired
	private EmpService empService;
	
	@RequestMapping("index/{operType}")
	public ModelAndView index(ModelAndView view,HttpServletRequest request, HttpServletResponse response,@PathVariable("operType")Integer operType) {
		//operType 是否查询所有客户，1客户管理，2开放客户,3我的客户
		if (operType==null) {
			operType = 3;
		}
		view.setViewName("manager/client/list");
		view.addObject("clienttypelist", dictService.getDictTypeByCode(Constants.DICT_CODE[0]));
		view.addObject("clientstarslist", dictService.getDictByCode(Constants.DICT_CODE[2]));
		view.addObject("clientreceivelist", dictService.getDictTypeByCode(Constants.DICT_CODE[4]));
		TEmp emp = new TEmp();
		if (operType.intValue()!=1) {
			emp.setId(UserSchoolThread.get().getUserid());
		}
		view.addObject("emplist", empService.getList(emp));
		view.addObject("operType", operType);
		return view;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("query/{operType}")
	public @ResponseBody Map query(final TClient client, @PathVariable("operType")Integer operType) {
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map map) {
				map.put("message", clientService.queryList(client,operType));
			}
		});
	}

	@RequestMapping("progressbar")
	public @ResponseBody String progressbar(HttpServletRequest request,Integer flag) {
		Map<Object, Object> map= new HashMap<>();
		if (flag==1) {
			ClientServiceImpl.uplength = 0;
			ClientServiceImpl.upcontent = "正在上传...";
		}
		map.put("uplength", ClientServiceImpl.uplength);
		map.put("upcontent", ClientServiceImpl.upcontent);
		return new Gson().toJson(map);
	}
	
	@RequestMapping("imp/{operType}")
	public ModelAndView imp(ModelAndView view,HttpServletRequest request, HttpServletResponse response,@PathVariable("operType")Integer operType) {
		view.setViewName("manager/client/imp");
		view.addObject("operType", operType);
		return view;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("saveClientfile/{operType}")
	public @ResponseBody Map saveClientfile(final @RequestParam(value = "clientfile", required = false) MultipartFile clientfile,final HttpServletRequest request, final HttpServletResponse response,@PathVariable("operType")Integer operType){
		Map map = super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message",clientService.saveClientfile(clientfile, request,operType));
			}
		});
        return map;
	}

	@RequestMapping("download")
	public void downloadTemplate(HttpServletRequest request,HttpServletResponse response){
		String filepath = request.getSession().getServletContext().getRealPath("/skins/template/download/client_template.xls");
		String fileName = "客户信息导入模版.xls";
		FileUtil.downloadFile(filepath, fileName,response);
	}
	

	@RequestMapping("add/{operType}")
	public ModelAndView add(ModelAndView view,HttpServletRequest request, HttpServletResponse response,@PathVariable("operType")Integer operType) {
		view.setViewName("manager/client/add");
		view.addObject("clienttypelist", dictService.getDictTypeByCode(Constants.DICT_CODE[0]));
		view.addObject("clientstarslist", dictService.getDictByCode(Constants.DICT_CODE[2]));
		view.addObject("clientreceivelist", dictService.getDictTypeByCode(Constants.DICT_CODE[4]));
		TClient client = new TClient();
		client.setFzempid(String.valueOf(UserSchoolThread.get().getUserid()));
		client.setFzempname(UserSchoolThread.get().getNickname());
		view.addObject("client", client);
		view.addObject("operType", operType);
		return view;
	}


	@RequestMapping("batchEdit/{operType}")
	public ModelAndView batch_edit(ModelAndView view,HttpServletRequest request, HttpServletResponse response,String ids, @PathVariable("operType")Integer operType) {
		view.setViewName("manager/client/batch_edit");
		view.addObject("clienttypelist", dictService.getDictTypeByCode(Constants.DICT_CODE[0]));
		view.addObject("clientstarslist", dictService.getDictByCode(Constants.DICT_CODE[2]));
		view.addObject("clientreceivelist", dictService.getDictTypeByCode(Constants.DICT_CODE[4]));
		view.addObject("clientids", ids);
		List<TClient> list = clientService.getByIds(ids);
		view.addObject("clientlist", list);
		view.addObject("operType", operType);
		return view;
	}
	
	@RequestMapping("edit/{operType}")
	public ModelAndView edit(ModelAndView view,HttpServletRequest request, HttpServletResponse response,Integer id, @PathVariable("operType")Integer operType) {
		view.setViewName("manager/client/edit");
		view.addObject("clienttypelist", dictService.getDictTypeByCode(Constants.DICT_CODE[0]));
		view.addObject("clientstarslist", dictService.getDictByCode(Constants.DICT_CODE[2]));
		view.addObject("clientreceivelist", dictService.getDictTypeByCode(Constants.DICT_CODE[4]));
		view.addObject("client", clientService.getById(id));
		view.addObject("operType", operType);
		return view;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("batchEditUpdate/{operType}")
	public @ResponseBody Map batchEditUpdate(HttpServletRequest request,final TClient client, String clientids,@PathVariable("operType")Integer operType){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", clientService.updateBatch(client,clientids,operType));
			}
		});
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("save/{operType}")
	public @ResponseBody Map save(HttpServletRequest request,final TClient client, @PathVariable("operType")Integer operType){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", clientService.saveOrUpdate(client,operType));
			}
		});
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("update/{operType}")
	public @ResponseBody Map update(HttpServletRequest request,final TClient client, @PathVariable("operType")Integer operType){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", clientService.saveOrUpdate(client,operType));
			}
		});
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("isopen")
	public @ResponseBody Map isopen(final Integer id, String isopen){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", clientService.updateFieidById("isopen", isopen, String.valueOf(id),1));
			}
		});
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("saveEmpClient")
	public @ResponseBody Map saveEmpClient(final String empids, Integer clientid){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", clientService.saveEmpClient(empids, clientid));
			}
		});
	}
	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("delete/{operType}")
	public @ResponseBody Map delete(final String ids, @PathVariable("operType")Integer operType){
		return super.doJsonPost(new JsonPost() {
			@SuppressWarnings("unchecked")
			@Override
			public void doInstancePost(Map arg0) {
				arg0.put("message", clientService.updateFieidById("status", "2", ids,operType));
			}
		});
	}
	
}
