package com.keeps.crm.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.keeps.core.dao.AbstractDao;
import com.keeps.crm.dao.DictDao;
import com.keeps.model.TDict;
import com.keeps.model.TDictType;
import com.keeps.tools.utils.StringUtils;
import com.keeps.tools.utils.page.Page;
import com.keeps.utils.TreeNode;

/** 
 * <p>Title: DictDaoImpl.java</p>  
 * <p>Description: 字典DAO实现类 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年1月19日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Repository
public class DictDaoImpl extends AbstractDao implements DictDao {

	@Override
	public Page queryDictList(TDict dict) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select a.*,b.name as typename from t_dict a inner join t_dict_type b on a.typeid = b.id ");
		List<Object> values = new ArrayList<Object>();
		if (dict.getTypeid()!=null && dict.getTypeid()!=0) {
			sb.append(" and a.typeid = ? ");
			values.add(dict.getTypeid());
		}
		if (StringUtils.hasText(dict.getName())) {
			sb.append(" and a.name like ? ");
			values.add("%"+dict.getName().trim()+"%");
		}
		if(StringUtils.hasText(dict.getSidx()) && StringUtils.hasText(dict.getSord())) {
			if (dict.getSidx().equals("typename")) {
	        	sb.append("order by b.name "+dict.getSord());
			}else{
	        	sb.append("order by a."+dict.getSidx()+" "+dict.getSord());
			}
		}else{
			sb.append("  order by a.sort ");
		}
		return super.queryBySql(sb.toString(), values.toArray(), dict, TDict.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TDict> getDictList(TDict dict){
		StringBuffer sb = new StringBuffer();
		sb.append(" select * from t_dict where status=1 ");
		List<Object> values = new ArrayList<Object>();
		if (dict.getTypeid()!=null) {
			sb.append(" and typeid = ? ");
			values.add(dict.getTypeid());
		}
		sb.append("order by sort ");
		return super.getByPropertySql(sb.toString(),values.toArray(),TDict.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TDictType> getDictTypeList(TDictType tDictType){
		StringBuffer sb = new StringBuffer();
		sb.append(" select * from t_dict_type where 1=1 ");
		List<Object> values = new ArrayList<Object>();
		if (StringUtils.hasText(tDictType.getName())) {
			sb.append(" and name like ? ");
			values.add("%"+tDictType.getName().trim()+"%");
		}
		sb.append("  order by sort ");
		return super.getByPropertySql(sb.toString(), values.toArray(), TDictType.class);
	}


	@SuppressWarnings("unchecked")
	@Override
	public TDict getDictById(Integer id) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select a.*,b.name as typename ");
		sb.append(" from t_dict a ");
		sb.append(" left JOIN t_dict_type b ON a.typecode = b.code ");
		sb.append(" where a.id =? ");
		List<TDict> list = super.getByPropertySql(sb.toString(), new Object[]{id}, TDict.class);
		return (CollectionUtils.isEmpty(list)) ? null : list.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TreeNode> getDictTypeTree(String code) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select id,pid as pId,CONCAT(name,if(fixed=1,'[内置]','')) as name,code as obj,isinsertdict as type from t_dict_type ");
		List<Object> values = new ArrayList<Object>();
		if (StringUtils.hasText(code)) {
			sb.append(" where code = ? ");
			values.add(code);
		}
		sb.append("order by sort ");
		return super.getByPropertySql(sb.toString(),values.toArray(),TreeNode.class);
	}
	
	@SuppressWarnings("rawtypes")
	public Integer countDictFixedByIds(String ids){
		List list = super.getByNameParamSql(" select count(1) from t_dict where id in (:ids) and fixed = 1 ", 
				new String[]{"ids"},
				new Object[]{ids.split(",")});
		return Integer.parseInt(list.get(0).toString());
	}

	@SuppressWarnings("rawtypes")
	public Integer countDictByTypeid(Integer typeid){
		List list = super.getByPropertySql(" select count(1) from t_dict where typeid = ? ", new Object[]{typeid});
		return Integer.parseInt(list.get(0).toString());
	}

	@SuppressWarnings("rawtypes")
	public Integer countDictTypeById(Integer id){
		List list = super.getByPropertySql(" select count(1) from t_dict_type where pid = ? ", new Object[]{id});
		return Integer.parseInt(list.get(0).toString());
	}

	@SuppressWarnings("unchecked")
	public List<TDictType> getDictTypeByCode(String code){
		StringBuffer sb = new StringBuffer();
		sb.append(" select * from t_dict_type a where a.pid = (select a.id from t_dict_type a where a.code = ? ) ");
		sb.append("order by sort ");
		return super.getByPropertySql(sb.toString(),new Object[]{code},TDictType.class);
	}

	@SuppressWarnings("unchecked")
	public List<TDict> getDictByCode(String code){
		StringBuffer sb = new StringBuffer();
		sb.append(" select * from t_dict where status=1 and typecode=? ");
		sb.append("order by sort ");
		return super.getByPropertySql(sb.toString(),new Object[]{code},TDict.class);
	}

}
