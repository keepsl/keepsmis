package com.keeps.crm.dao;

import java.util.List;

import com.keeps.core.dao.SoftDao;
import com.keeps.model.TDict;
import com.keeps.model.TDictType;
import com.keeps.tools.utils.page.Page;
import com.keeps.utils.TreeNode;

/** 
 * <p>Title: DictDao.java</p>  
 * <p>Description: 字典DAO接口 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年1月19日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
public interface DictDao extends SoftDao{
	
	public Page queryDictList(TDict dict);
	
	public List<TDict> getDictList(TDict dict);
	
	public List<TDictType> getDictTypeList(TDictType tDictType);
	
	public TDict getDictById(Integer id);
	
	public List<TreeNode> getDictTypeTree();

	public Integer countDictFixedByIds(String ids);

	public Integer countDictByTypeid(Integer dictid);
	
	public Integer countDictTypeById(Integer id);
	
	public List<TDictType> getDictTypeByCode(String code);

	public List<TDict> getDictByCode(String code);

}
