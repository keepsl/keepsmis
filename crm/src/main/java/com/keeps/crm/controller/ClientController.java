package com.keeps.crm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.keeps.core.controller.AbstractAPIController;
import com.keeps.crm.service.ClientService;
import com.keeps.crm.service.DictService;
import com.keeps.crm.service.EmpService;
import com.keeps.crm.service.impl.ClientServiceImpl;
import com.keeps.model.TClient;
import com.keeps.model.TEmp;
import com.keeps.tools.utils.DateUtils;
import com.keeps.tools.utils.ExcelUtil;
import com.keeps.tools.utils.JsonPost;
import com.keeps.tools.utils.StringUtils;
import com.keeps.tools.utils.page.Page;
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
public class ClientController extends AbstractAPIController {
	
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
				Page page = clientService.queryList(client,operType);
				/*if(operType.intValue()!=1){
					if (StringUtils.hasText(client.getPhone())) {//通过搜索电话号码找寻客户。
						if (page.getTotal()==0) {
							//查询客户，告诉当前搜索人员，该客户属于谁。
							
						}
					}
				}*/
				map.put("message", page);
			}
		});
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping("getListInfoByPhone")
	public @ResponseBody Map getListInfoByPhone(String phone) {
		Map m = success();
		List<TClient> list = clientService.getListInfoByPhone(phone);
		TClient client = new TClient();
		if (list!=null) {
			client = list.get(0);
		}
		m.put("recored", client);
		return m;
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
	
	
	@RequestMapping("exportOfFile/{operType}")
	public void exportOfFile(ModelAndView view,HttpServletRequest request, HttpServletResponse response,@PathVariable("operType")Integer operType) {
		HSSFWorkbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("客户详细列表");
        List<TClient> list = clientService.getListAll(new TClient(), operType);
        int k = 0;
		for (TClient tClient : list) {
			k++;
			Row row = sheet.createRow((int)k);//创建一行
			ExcelUtil.createCell(row, 0, tClient.getName());
			ExcelUtil.createCell(row, 1, tClient.getPhone());
			ExcelUtil.createCell(row, 2, tClient.getLinkempname());
			ExcelUtil.createCell(row, 3, StringUtils.notText(tClient.getContacttime())?"无":DateUtils.format(tClient.getContacttime(), "yyyy-MM-dd HH:mm"));
			ExcelUtil.createCell(row, 4, StringUtils.notText(tClient.getNexttime())?"无":DateUtils.format(tClient.getNexttime(), "yyyy-MM-dd HH:mm"));
			ExcelUtil.createCell(row, 5, StringUtils.notText(tClient.getVisittime())?"无":DateUtils.format(tClient.getVisittime(), "yyyy-MM-dd HH:mm"));
			ExcelUtil.createCell(row, 6, tClient.getAttentionname());
			ExcelUtil.createCell(row, 7, tClient.getTypename());
			ExcelUtil.createCell(row, 8, tClient.getFzempname());
			ExcelUtil.createCell(row, 9, tClient.getReceivename());
			ExcelUtil.createCell(row, 10, tClient.getReceivetypename());
			ExcelUtil.createCell(row, 11, tClient.getIsopen()==1?"否":"是");
			ExcelUtil.createCell(row, 12, DateUtils.format(tClient.getCreatetime(), "yyyy-MM-dd HH:mm:ss"));
		}
		String[] titlenames = new  String[] {"姓名","联系电话","最近联系人","最近联系时间","下次联系时间","来访时间","关注度","客户类型","负责人","邀约人","邀约方式","是否开放","创建时间"};
        ExcelUtil.downloadExeclFile(workbook,sheet,titlenames, "客户详细备份-"+DateUtils.getCurrentZhDate(),response);
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
