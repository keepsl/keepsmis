package com.keeps.crm.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.assertj.core.internal.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.keeps.core.service.AbstractService;
import com.keeps.crm.dao.ClientDao;
import com.keeps.crm.service.ClientService;
import com.keeps.crm.utils.Hanyu;
import com.keeps.crm.utils.pojo.MessagePojo;
import com.keeps.model.TClient;
import com.keeps.model.TEmpclient;
import com.keeps.tools.exception.CapecException;
import com.keeps.tools.utils.Assert;
import com.keeps.tools.utils.CalendarUtil;
import com.keeps.tools.utils.CommonUtils;
import com.keeps.tools.utils.DateUtils;
import com.keeps.tools.utils.EditType;
import com.keeps.tools.utils.ExcelUtil;
import com.keeps.tools.utils.StringUtils;
import com.keeps.tools.utils.page.Page;
import com.keeps.tools.utils.threadlocal.UserSchoolThread;
import com.keeps.utils.ValidateUtil;

/** 
 * <p>Title: ClientServiceImpl.java</p>  
 * <p>Description: 客户Service接口实现类 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年6月23日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Service
public class ClientServiceImpl extends AbstractService implements ClientService {
	private ReentrantLock lock = new ReentrantLock();  
	public static Integer uplength = 0;
	public static String upcontent="正在上传中...";
	
	@Autowired
	private ClientDao clientDao;
	
	public Page queryList(TClient client,Integer operType){
		if (operType == null) {
			return null;
		}
		Page page = null;
		if (client.getTracktime()!=null) {
			if (client.getTracktime().intValue()==7) {//7-14天未联系
				client.setTracktimesta(DateUtils.format(DateUtils.addDay(DateUtils.getNow(), -7), "yyyy-MM-dd"));
				client.setTracktimeend(DateUtils.format(DateUtils.addDay(DateUtils.getNow(), -14), "yyyy-MM-dd"));
			}else if(client.getTracktime().intValue()==15){//15-29天未联系
				client.setTracktimesta(DateUtils.format(DateUtils.addDay(DateUtils.getNow(), -15), "yyyy-MM-dd"));
				client.setTracktimeend(DateUtils.format(DateUtils.addDay(DateUtils.getNow(), -29), "yyyy-MM-dd"));
			}else if(client.getTracktime().intValue()==30){
				client.setTracktimesta(DateUtils.format(DateUtils.addDay(DateUtils.getNow(), -30), "yyyy-MM-dd"));
				client.setTracktimeend(DateUtils.format(DateUtils.addDay(DateUtils.getNow(), -59), "yyyy-MM-dd"));
			}else if(client.getTracktime().intValue()==60){
				client.setTracktimesta(DateUtils.format(DateUtils.addDay(DateUtils.getNow(), -60), "yyyy-MM-dd"));
				client.setTracktimeend(DateUtils.format(DateUtils.addDay(DateUtils.getNow(), -99), "yyyy-MM-dd"));
			}else if(client.getTracktime().intValue()==100){
				client.setTracktimesta(DateUtils.format(DateUtils.addDay(DateUtils.getNow(), -100), "yyyy-MM-dd"));
			}
		}
		if (client.getHodgepodgetype()!=null) {//大杂烩 按照分类查询，1今日已联系，2本周已联系，3本月已联系 4，今日需要联系，5本周需联系，6本月需联系，7今日新登记，8本周新登记，9本月新登记
			if (client.getHodgepodgetype().intValue()==1) {
				client.setContacttimesta(DateUtils.formatNow());
				client.setContacttimeend(DateUtils.formatNow());
			}else if (client.getHodgepodgetype().intValue()==2) {
				client.setContacttimesta(CommonUtils.getWeekStart(DateUtils.getNow()));
				client.setContacttimeend(CommonUtils.getWeekLast(DateUtils.getNow()));
			}else if(client.getHodgepodgetype().intValue()==3){
				client.setContacttimesta(CommonUtils.getMonthStart());
				client.setContacttimeend(CommonUtils.getMonthLast());
			}else if(client.getHodgepodgetype().intValue()==4){
				client.setNextcontacttimesta(DateUtils.formatNow());
				client.setNextcontacttimeend(DateUtils.formatNow());
			}else if(client.getHodgepodgetype().intValue()==5){
				client.setNextcontacttimesta(CommonUtils.getWeekStart(DateUtils.getNow()));
				client.setNextcontacttimeend(CommonUtils.getWeekLast(DateUtils.getNow()));
			}else if(client.getHodgepodgetype().intValue()==6){
				client.setNextcontacttimesta(CommonUtils.getMonthStart());
				client.setNextcontacttimeend(CommonUtils.getMonthLast());
			}else if(client.getHodgepodgetype().intValue()==7){
				client.setCreatetimesta(DateUtils.formatNow());
				client.setCreatetimeend(DateUtils.formatNow());
			}else if(client.getHodgepodgetype().intValue()==8){
				client.setCreatetimesta(CommonUtils.getWeekStart(DateUtils.getNow()));
				client.setCreatetimeend(CommonUtils.getWeekLast(DateUtils.getNow()));
			}else if(client.getHodgepodgetype().intValue()==9){
				client.setCreatetimesta(CommonUtils.getMonthStart());
				client.setCreatetimeend(CommonUtils.getMonthLast());
			}
		}
		if (client.getVisitttimetype()!=null) {//按来访时间类型查询，1今天来访，2，明日来访，3后天来访，4.三天后要来访客户，5，本周所有来访客户，6，本月所有来访客户
			if (client.getVisitttimetype()==1) {//1 今日来访
				client.setVisittimesta(DateUtils.formatNow());
				client.setVisittimeend(DateUtils.formatNow());
			}else if (client.getVisitttimetype()==2) {//2 明日来访
				client.setVisittimesta(DateUtils.format(DateUtils.addDay(DateUtils.getNow(), 1),"yyyy-MM-dd"));
				client.setVisittimeend(DateUtils.format(DateUtils.addDay(DateUtils.getNow(), 1),"yyyy-MM-dd"));
			}else if (client.getVisitttimetype()==3) {//3 后天来访
				client.setVisittimesta(DateUtils.format(DateUtils.addDay(DateUtils.getNow(), 2),"yyyy-MM-dd"));
				client.setVisittimeend(DateUtils.format(DateUtils.addDay(DateUtils.getNow(), 2),"yyyy-MM-dd"));
			}else if (client.getVisitttimetype()==4) {//4两天后要来访客户
				client.setVisittimesta(DateUtils.format(DateUtils.addDay(DateUtils.getNow(), 2),"yyyy-MM-dd"));
			}else if (client.getVisitttimetype()==5) {//5本周所有来访客户
				client.setVisittimesta(CommonUtils.getWeekStart(DateUtils.getNow()));
				client.setVisittimeend(CommonUtils.getWeekLast(DateUtils.getNow()));
			}else if (client.getVisitttimetype()==6) {//6本月所有来访客户
				client.setVisittimesta(CommonUtils.getMonthStart());
				client.setVisittimeend(CommonUtils.getMonthLast());
			}
		}
		if (operType.intValue()==1 || operType.intValue()==2 || operType.intValue()==3) {
			client.setCreateperson(UserSchoolThread.get().getUserid());//当查询我的客户时使用
			page = clientDao.queryList(client,operType);
		}

		if (page==null) {
			return page;
		}
		/*if (operType.intValue()!=1) {//隐藏电话号码
			List<TClient> list = page.getRecord();
			for (TClient tClient : list) {
				tClient.setPhone(tClient.getPhone().replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
			}
		}
		*/
		return page;
	}
	
	public List<TClient> getListAll(TClient client,Integer operType){
		if (operType == null) {
			return null;
		}
		client.setCreateperson(UserSchoolThread.get().getUserid());
		return clientDao.getListAll(client, operType);
	}

	@Override
	public List<TClient> getListInfoByPhone(String phone){
		return clientDao.getListInfoByPhone(phone);
	}

	@Override
	public TClient getById(Integer id) {
		return clientDao.getListById(id);
	}

	@Override
	public List<TClient> getByIds(String ids){
		return clientDao.getListByIds(ids);
	}

	@Override
	public String saveOrUpdate(TClient client,Integer operType) {
		/*boolean issave = true;
		if (client.getId()!=null) {
			issave = false;
		}*/
		Assert.isTrue(operType!=null, "没有找到当前操作类型,系统检测到你有非法操作!");
		Assert.isTrue(StringUtils.hasText(client.getName()), "客户姓名不能为空!");
		if (operType==1) {
			Assert.isTrue(StringUtils.hasText(client.getPhone()), "电话不能为空!");
			if (client.getPhone().indexOf("-")==-1) {
				Assert.isTrue(CommonUtils.isNumeric(client.getPhone()), "电话格式不正确!");
				if (client.getPhone().length()!=7&&client.getPhone().length()!=11) {
					throw new CapecException("电话格式不正确!");
				}
			}else{
				String[] strs = client.getPhone().split("-");
				if (strs.length!=2) {
					throw new CapecException("电话格式不正确!");
				}
				if (strs[0].length()!=4) {
					throw new CapecException("电话格式不正确!");
				}
				if (strs[1].length()!=7) {
					throw new CapecException("电话格式不正确!");
				}
			}
		}
		if (StringUtils.hasText(client.getEmail())) {
			Assert.isTrue(ValidateUtil.isEmail(client.getEmail()), "邮箱格式不正确!");
		}
		Assert.isTrue(client.getSex()!=null, "性别不能为空!");
		client.setStatus(1);
		if (client.getIsopen()==null) {
			client.setIsopen(1);
		}
		if (client.getIslunar()==null) {//是否农历，1是，2否
			client.setIslunar(1);
		}
		if (StringUtils.hasText(client.getBirthdate())) {
			if (client.getIslunar().intValue()==1) {//是农历，把生日转换成阳历
				client.setSolarbirthdate(CalendarUtil.solarToLunar(client.getBirthdate()));
			}else{
				client.setSolarbirthdate(client.getBirthdate());
			}
		}else{
			client.setBirthdate(null);
			client.setSolarbirthdate(null);
		}
		/*if(!clientDao.isUnique(client, new String[]{"phone"})){
			throw new CapecException("["+client.getPhone()+"]手机号码已经存在,请确认号码是否正确!");
		}*/
		TClient client2 = clientDao.getClientByPhone(client.getPhone(),client.getId());
		if (client2!=null) {
			if (StringUtils.hasText(client2.getFzempname())) {
				throw new CapecException("["+client.getPhone()+"]号码已存在，客户姓名["+client2.getName()+"]，负责人["+client2.getFzempname()+"]!");
			}else{
				throw new CapecException("["+client.getPhone()+"]号码已存在，客户姓名["+client2.getName()+"]，无负责人!");
			}
		}
		client.setPym(Hanyu.getFirstSpell(client.getName()));
		client.setAllpym(Hanyu.getFullSpell(client.getName()));
		if (operType.intValue()==2) {//从开放客户操作添加客户
			client.setIsopen(2);
		}
		super.saveOrUpdateEntity(client,EditType.NULL_UN_UPDATE);
		
		clientDao.deleteEmpclientByClientids(String.valueOf(client.getId()));
		
		if (StringUtils.hasText(client.getFzempid())) {
			String[] empidsp = client.getFzempid().split(",");
			for (String empid : empidsp) {
				TEmpclient empclient = new TEmpclient();
				empclient.setEmpid(Integer.parseInt(empid));
				empclient.setClientid(client.getId());
				super.save(empclient);
			}
		}
		/*
		if(operType.intValue()==3){//从我的客户操作添加客户
			if (issave) {//保存时，保存我客户到我的关系中
				TEmpclient empclient = new TEmpclient();
				empclient.setEmpid(UserSchoolThread.get().getUserid());
				empclient.setClientid(client.getId());
				super.save(empclient);
			}
		}*/
		return null;
	}
	
	public String updateBatch(TClient client,String clientids,Integer operType){
		Assert.isTrue(StringUtils.hasText(clientids), "客户id组不能为空不能为空!");
		String[] ids = clientids.split(",");
		
		if (StringUtils.notText(client.getFzempid())) {
			client.setFzempid(null);
		}
		if (StringUtils.notText(client.getRemark())) {
			client.setRemark(null);
		}
		
		if (StringUtils.hasText(client.getFzempid())) {
			clientDao.deleteEmpclientByClientids(clientids);
		}

		for (String id : ids) {
			client.setId(Integer.parseInt(id));
			super.update(client,EditType.NULL_UN_UPDATE);
			

			if (StringUtils.hasText(client.getFzempid())) {
				String[] empidsp = client.getFzempid().split(",");
				for (String empid : empidsp) {
					TEmpclient empclient = new TEmpclient();
					empclient.setEmpid(Integer.parseInt(empid));
					empclient.setClientid(client.getId());
					super.save(empclient);
				}
			}
		}
		return null;
	}


	public String saveEmpClient(String empids, Integer clientid){
		Assert.isTrue(clientid!=null, "请选择客户!");
		//Assert.isTrue(StringUtils.hasText(empids), "请选择客户负责人!");
		clientDao.deleteEmpclientByClientids(String.valueOf(clientid));
		if (StringUtils.hasText(empids)) {
			String[] empidsp = empids.split(",");
			for (String empid : empidsp) {
				TEmpclient empclient = new TEmpclient();
				empclient.setEmpid(Integer.parseInt(empid));
				empclient.setClientid(clientid);
				super.save(empclient);
			}
		}
		return null;

	}
	

	@Override
	public String updateFieidById(String fieid, String value, String ids, Integer operType) {
		Assert.isTrue(operType!=null, "没有找到当前操作类型,系统检测到你有非法操作!");
		
		Assert.isTrue(StringUtils.hasText(ids), "要操作的数据传入的id不能为空!");

		clientDao.updateFieidById(fieid, value, ids);
		
		clientDao.deleteEmpclientByClientids(ids);

		return null;
	}
	
	public String saveClientfile(MultipartFile file, HttpServletRequest request,Integer operType){
		if(lock.isLocked()){
			throw new CapecException("有其他管理员正在进行操作,为防止数据冲突,请您稍等一会儿再来进行该操作.");
		}
		lock.lock();
		uplength = 0;
		upcontent = "正在校验文件格式...";
		Assert.isTrue(file!=null, "请选择文件!");
		Assert.isTrue(!file.isEmpty(), "上传的文件为空!");
		String filename = file.getOriginalFilename();
		String filetype = filename.substring(file.getOriginalFilename().lastIndexOf(".")+1);
		if(!CommonUtils.isExistStr(filetype.toLowerCase(), new String[]{"xls","xlsx"}))
			throw new CapecException("不允许上传" + filetype + "格式的文件!");
		Workbook wb  = null;
		try {
			//判断execl版本
			if(file.getOriginalFilename().toLowerCase().endsWith("xls")){//xls 2003包括2003之前的版本
				wb = new HSSFWorkbook(file.getInputStream());
			}else if(file.getOriginalFilename().toLowerCase().endsWith("xlsx")){
				wb = new XSSFWorkbook(file.getInputStream());
			}
			uplength = 10;
			upcontent = "正在校验文件格式...";
			Assert.isTrue(wb.getNumberOfSheets()>=0, "没有找到sheet!");
			Sheet sheet = wb.getSheetAt(0);
			Assert.isTrue(sheet!=null, "sheet不能为空!");
			/*for (int i = 0; i < wb.getNumberOfSheets(); i++) {
			}*/
			validateSheetTitle(sheet.getRow(0));
			List<TClient> listclient = new ArrayList<>();
			List<MessagePojo> listmessage = new ArrayList<>();
			uplength = 50;
			upcontent = "正在校验文件数据...";
			sheet2bean(sheet,listclient,listmessage,operType);
			
			uplength = 70;
			upcontent = "正在整理数据...";

			upcontent = "正在保存数据...";
			//super.saveOrUpdateAllEntity(listclient, EditType.NULL_UN_UPDATE);
			for (TClient client : listclient) {
				uplength ++;
				if(operType.intValue()==2){
					client.setIsopen(2);
				}
				super.saveOrUpdateEntity(client, EditType.NULL_UN_UPDATE);
				if(operType.intValue()==3){//从我的客户操作添加客户
					TEmpclient empclient = new TEmpclient();
					empclient.setEmpid(UserSchoolThread.get().getUserid());
					empclient.setClientid(client.getId());
					super.save(empclient);
				}
			}
			uplength = 100;
			upcontent = "数据保存完成...";
		} catch (Exception e) {
			
			log.error("导入客户失败:"+e);
			e.printStackTrace();
			throw new CapecException(e.getMessage());
		} finally {
			lock.unlock();
		}
		return "导入成功!";
	}

	private boolean sheet2bean(Sheet sheet,List<TClient> listclient,List<MessagePojo> listmessage,Integer operType){
		boolean zflag = true;//总标记 当有一处错时，标记为false。
		String message = "";
		TClient client = new TClient();
		for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
			Row row = sheet.getRow(rowNum);
			boolean isCellNullSta = false;//记录每个单元格为空状态，如果每个单元格都是为空，代表该行是空，不解析改行数据
			for (Integer cellNum = 0; cellNum <= 2; cellNum++) {
				Cell cell = row.getCell(cellNum);
				String value = ExcelUtil.getValue(cell);
				if (CommonUtils.isNotNull(value)) {
					isCellNullSta = true;
				}
			}
			if (!isCellNullSta) {
				continue;
			}
			for (int cellNum = 0; cellNum <= 2; cellNum++) {
				Cell cell = row.getCell(cellNum);
				
				//取得值
				String value = ExcelUtil.getValue(cell);
				if (cellNum!=2) {
					if (StringUtils.notText(value)) {
						message = impRetMsg(rowNum,cellNum,"不能为空");
						throw new CapecException(message);
					}
				}
				// 把row转换为bean
				switch (cellNum) {
					case 0 ://姓名
						client = new TClient();
						client.setSex(1);
						client.setStatus(1);
						client.setIsopen(1);
						client.setIslunar(1);
						client.setName(value);
						break;
					case 1://手机号
						Assert.isTrue(ValidateUtil.isMobile(value), "手机号格式不正确!");
						client.setPhone(value);
						/*if(!clientDao.isUnique(client, new String[]{"phone"})){
						}*/
						TClient client2 = clientDao.getClientByPhone(value,null);
						if (client2!=null) {
							if (StringUtils.hasText(client2.getFzempname())) {
								throw new CapecException("["+value+"]号码已存在，客户姓名["+client2.getName()+"]，负责人["+client2.getFzempname()+"]!");
							}else{
								throw new CapecException("["+value+"]号码已存在，客户姓名["+client2.getName()+"]，无负责人!");
							}
						}
						break;
					case 2://备注
						client.setRemark(value);
						if (isExistsByImp(client,listclient)) {
							client.setPym(Hanyu.getFirstSpell(client.getName()));
							client.setAllpym(Hanyu.getFullSpell(client.getName()));
							listclient.add(client);
						}
						break;
					default:
						break;
				}
			}
		}
		return zflag;
	}
	
	private boolean isExistsByImp(TClient client, List<TClient> list) {
		// 判断是否在当前excel中存在
		for (TClient mo : list) {
			String mostr = mo.getPhone();
			if (mostr.equals(client.getPhone())) {
				return false;
			}
		}
		return true;
	}
	private String impRetMsg(int rowindex,int cellindex,String msg){
		return "数据格式错误:第" + (rowindex+1)+ "行,第"+(cellindex+1)+"列["+getTitieName(cellindex)+"],"+msg+".";
	}
	
	private String validateSheetTitle(Row row){
		if(row==null){
			throw new CapecException("标题行不能为空");
		}
		StringBuffer sbBuffer = new StringBuffer();
		sbBuffer.append("标题格式错误:<br/>");
		boolean zflag = true;
		for(Integer i=0;i<=2;i++) {//循环读取每个单元格
			Cell cell = row.getCell(i);
			if (CommonUtils.isNull(cell)) {
				sbBuffer.append("第"+(i+1)+"列必须为["+getTitieName(i)+"].<br/>");
			}
			if(!ExcelUtil.getValue(row.getCell(i)).equals(getTitieName(i))){
				zflag = false;
				sbBuffer.append("第"+(i+1)+"列必须为["+getTitieName(i)+"].<br/>");
			}
		}
		if (!zflag) {
			throw new CapecException(sbBuffer.toString());
		}
		return null;
	}
	
	public static String getTitieName(int i){
		String[] titlename = new String[]{"姓名","手机号","备注"};
		return titlename[i].trim();
	}
	
}
