package com.keeps.ksxtmanager.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keeps.core.model.utils.IdTypes;
import com.keeps.core.service.AbstractService;
import com.keeps.ksxtmanager.system.dao.NewsDao;
import com.keeps.ksxtmanager.system.service.NewsService;
import com.keeps.model.TNews;
import com.keeps.tools.utils.Assert;
import com.keeps.tools.utils.DateUtils;
import com.keeps.tools.utils.EditType;
import com.keeps.tools.utils.StringUtils;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: NewsServiceImpl.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年7月1日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Service
public class NewsServiceImpl extends AbstractService implements NewsService {

	@Autowired
	private NewsDao newsDao;
	
	@Override
	public Page query(TNews news) {
		return newsDao.query(news);
	}

	@Override
	public TNews getById(Integer id) {
		return newsDao.getById(id);
	}

	@Override
	public String saveOrUpdate(TNews news) {
		Assert.isTrue(StringUtils.hasText(news.getTitle()), "公告标题不能为空！");
		Assert.isTrue(StringUtils.hasText(news.getPublishtimestr()), "公告发布日期不能为空！");
		Assert.isTrue(news.getDicttype()!=null && news.getDicttype()!=0, "公告类型不能为空！");
		Assert.isTrue(StringUtils.hasText(news.getContent()), "公告内容不能为空！");
		news.setPublishtime(DateUtils.parse(news.getPublishtimestr(), "yyyy-MM-dd HH:mm:ss"));
		super.saveOrUpdateEntity(news,EditType.NULL_UN_UPDATE);
		return null;
	}

	@Override
	public String delete(String ids) {
		Assert.isTrue(StringUtils.hasText(ids), "请选择要删除的数据.");
		super.removeEntity(TNews.class, ids, IdTypes.Integer);
		return "删除成功!";
	}

}
