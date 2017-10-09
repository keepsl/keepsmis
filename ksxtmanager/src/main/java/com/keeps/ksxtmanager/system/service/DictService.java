package com.keeps.ksxtmanager.system.service;

import java.util.List;

import com.keeps.core.service.SoftService;
import com.keeps.model.TDict;
import com.keeps.model.TDictType;
import com.keeps.tools.utils.page.Page;
import com.keeps.utils.TreeNode;

/** 
 * <p>Title: DictService.java</p>  
 * <p>Description: 字典SERVICE接口 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年1月19日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public interface DictService extends SoftService{

	public Page queryDictList(TDict dict);
	
	public List<TreeNode> getDictTree(String code);
	
	public List<TDict> getDictList(TDict dict);
	
	public List<TDictType> getDictTypeList(TDictType tDictType);

	public TDict getDictById(Integer id);
	
	public TDictType geTDictTypeById(Integer id);
	
	public String deleteDictById(String ids);
	
	public String deleteDictTypeById(Integer id);
	
	public List<TreeNode> getDictTypeTree();
	
	public List<TreeNode> getDicttypeTreeByCode(String code);
	
	public List<TreeNode> getDictTypeSelectByCode(String code);

	public String saveOrUpdateDict(TDict dict);
	
	public String saveOrUpdateDictType(TDictType dictType);
	
	public List<TDictType> getDictTypeByCode(String code);

	public List<TDict> getDictByCode(String code);
}
