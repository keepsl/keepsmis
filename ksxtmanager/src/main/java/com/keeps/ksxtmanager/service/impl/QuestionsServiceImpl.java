package com.keeps.ksxtmanager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keeps.core.model.utils.IdTypes;
import com.keeps.core.service.AbstractService;
import com.keeps.ksxtmanager.dao.QuestionsDao;
import com.keeps.ksxtmanager.model.KsQuestions;
import com.keeps.ksxtmanager.service.QuestionsService;
import com.keeps.model.TSchedule;
import com.keeps.tools.utils.Assert;
import com.keeps.tools.utils.EditType;
import com.keeps.tools.utils.StringUtils;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: QuestionsServiceImpl.java</p>  
 * <p>Description: 试题管理SERVICE接口实现类 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年9月17日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Service
public class QuestionsServiceImpl extends AbstractService implements QuestionsService {

	@Autowired
	private QuestionsDao questionsDao;
	
	/**
	 * 查询试题列表
	 */
	public Page queryList(KsQuestions questions){
		return questionsDao.queryList(questions);
	}
	
	public String saveOrUpdate(KsQuestions questions){
		if (questions.getId()==null) {
			questions.setIsatuoscoring(0);
			questions.setExamnum(0);
			questions.setCorrectnum(0);
			questions.setErrornum(0);
		}
		Assert.isTrue(StringUtils.hasText(questions.getTitle()), "题目标题不能为空!");
		Assert.isTrue(StringUtils.hasText(questions.getAnsweroption()), "答案选项不能为空!");
		Assert.isTrue(StringUtils.hasText(questions.getCorrectanswer()), "正确答案不能为空!");
		Assert.isTrue(questions.getDegofdifftype()!=null, "难易度不能为空!");
		Assert.isTrue(questions.getProblemtype()!=null, "类型不能为空!");
		Assert.isTrue(questions.getQuestionstype()!=null, "分类不能为空!");
		super.saveOrUpdateEntity(questions,EditType.NULL_UN_UPDATE);
		return null;
	}
	
	public KsQuestions getById(Integer id){
		return super.get(KsQuestions.class, id);
	}

	public KsQuestions getInfoById(Integer id){
		return questionsDao.getInfoById(id);
	}

	public String delete(String ids){
		Assert.isTrue(StringUtils.hasText(ids), "请选择要删除的数据.");
		super.removeEntity(KsQuestions.class, ids, IdTypes.Integer);
		return "删除成功!";
	}


}
