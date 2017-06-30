package com.keeps.crm.service;

import java.util.List;

import com.keeps.core.service.SoftService;
import com.keeps.model.SzlGoodsClass;
import com.keeps.tools.utils.page.Page;
import com.keeps.utils.TreeNode;

/** 
 * <p>Title: GoodsClassService.java</p>  
 * <p>Description: 商品分类Service接口 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年4月5日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public interface GoodsClassService extends SoftService{

	public Page queryList(SzlGoodsClass goodsClass);

	public List<SzlGoodsClass> queryListAll(Integer pid);
	
	public List<TreeNode> queryListTree();

	public SzlGoodsClass getById(Integer id);
	
	public String save(SzlGoodsClass goodsClass);
	
	public String update(SzlGoodsClass goodsClass);
	
	public String delete(String ids);
	
	public String updateFieidById(String fieid,Integer value,String ids);


}
