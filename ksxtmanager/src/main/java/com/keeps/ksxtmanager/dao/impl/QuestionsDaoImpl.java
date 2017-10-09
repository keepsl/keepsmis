package com.keeps.ksxtmanager.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import com.keeps.core.dao.AbstractDao;
import com.keeps.ksxtmanager.dao.QuestionsDao;
import com.keeps.ksxtmanager.model.KsQuestions;
import com.keeps.tools.utils.StringUtils;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: QuestionsDaoImpl.java</p>  
 * <p>Description: 试题管理DAO实现类 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年9月17日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Repository
public class QuestionsDaoImpl extends AbstractDao implements QuestionsDao {

	/**
	 * 查询试题列表
	 */
	public Page queryList(KsQuestions questions){
		StringBuffer sb = new StringBuffer();
		sb.append(" select a.*,b.name as questionstypename,c.name as problemtypename,d.name as degofdifftypename "
				+ "  from ks_questions a "
				+ " left join t_dict_type b on a.questionstype = b.id "
				+ " left join t_dict_type c on a.problemtype = c.id"
				+ " left join t_dict_type d on a.degofdifftype = d.id ");
		sb.append(" where 1=1 ");
		List<Object> values = new ArrayList<Object>();
		if (StringUtils.hasText(questions.getTitle())) {
			sb.append(" and a.title like ? ");
			values.add("%"+questions.getTitle().trim()+"%");
		}
		if (questions.getQuestionstype()!=null && 0!=questions.getQuestionstype()) {
			sb.append(" and (b.id = ? or b.pid = ?) ");
			values.add(questions.getQuestionstype());
			values.add(questions.getQuestionstype());
		}
		if (questions.getProblemtype()!=null && 0!=questions.getProblemtype()) {
			sb.append(" and a.problemtype = ? ");
			values.add(questions.getProblemtype());
		}
		if (questions.getDegofdifftype()!=null && 0!=questions.getDegofdifftype()) {
			sb.append(" and a.degofdifftype = ? ");
			values.add(questions.getDegofdifftype());
		}
		if(StringUtils.hasText(questions.getSidx()) && StringUtils.hasText(questions.getSord())) {
			if ("questionstypename".equals(questions.getSidx())) {
				sb.append("order by b.name "+questions.getSord());
			}else if ("problemtypename".equals(questions.getSidx())){
				sb.append("order by c.name "+questions.getSord());
			}else if ("degofdifftypename".equals(questions.getSidx())){
				sb.append("order by d.name "+questions.getSord());
			}else{
				sb.append("order by a."+questions.getSidx()+" "+questions.getSord());
			}
		}else{
			sb.append(" order by a.updatetime desc ");	
		}
		return super.queryBySql(sb.toString(), values.toArray(), questions, KsQuestions.class);
	}
	
	public KsQuestions getInfoById(Integer id){
		StringBuffer sb = new StringBuffer();
		sb.append(" select a.*,b.name as questionstypename,c.name as problemtypename,d.name as degofdifftypename "
				+ "  from ks_questions a "
				+ " left join t_dict_type b on a.questionstype = b.id "
				+ " left join t_dict_type c on a.problemtype = c.id"
				+ " left join t_dict_type d on a.degofdifftype = d.id ");
		sb.append(" where a.id=? ");
		List<Object> values = new ArrayList<Object>();
		values.add(id);
		List<KsQuestions> list = super.getByPropertySql(sb.toString(), values.toArray(), KsQuestions.class);
		return (CollectionUtils.isEmpty(list)) ? null : list.get(0);
	}


}
