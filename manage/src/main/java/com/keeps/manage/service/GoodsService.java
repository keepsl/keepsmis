package com.keeps.manage.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.keeps.core.service.SoftService;
import com.keeps.model.SzlGoods;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: GoodsService.java</p>  
 * <p>Description: 商品Service接口 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年4月5日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public interface GoodsService extends SoftService{

	public Page queryList(SzlGoods goods);
	
	public SzlGoods getById(Long id,HttpServletRequest request);
	
	public String saveOrUpdate(SzlGoods goods,MultipartFile goodsimagefile, MultipartFile qrcodepathfile, HttpServletRequest request);
	
	public String updateFieidById(String fieid,Integer value,String ids);

	public String saveGoodsfileData(MultipartFile file, HttpServletRequest request);
}
