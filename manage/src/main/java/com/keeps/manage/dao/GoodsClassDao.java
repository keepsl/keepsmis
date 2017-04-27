package com.keeps.manage.dao;

import java.util.List;

import com.keeps.core.dao.SoftDao;
import com.keeps.utils.TreeNode;
import com.keeps.model.SzlGoodsClass;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: GoodsClass.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年4月5日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public interface GoodsClassDao extends SoftDao{
	

	public Page queryList(SzlGoodsClass goods_class);
	
	public List<SzlGoodsClass> queryListAll(Integer pid);

	public List<TreeNode> queryListTree();
	
	public Integer updateFieidById(String fieid,Integer value,String ids);
	
	public SzlGoodsClass getByClassname(String classname);

	
}
