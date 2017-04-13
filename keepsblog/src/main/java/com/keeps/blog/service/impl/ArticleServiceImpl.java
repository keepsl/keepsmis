package com.keeps.blog.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keeps.blog.dao.ArticleDao;
import com.keeps.blog.service.ArticleService;
import com.keeps.utils.Constants;
import com.keeps.utils.SysConfigUtil;
import com.keeps.core.service.AbstractService;
import com.keeps.model.TArticle;
import com.keeps.tools.utils.StringUtils;

/** 
 * <p>Title: ArticleServiceImpl.java</p>  
 * <p>Description: @TODO </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年2月9日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Service
public class ArticleServiceImpl extends AbstractService implements ArticleService {
	@Autowired
	private ArticleDao articleDao;
	
	@Override
	public List<TArticle> queryTopRecommendList(HttpServletRequest request) {
		List<TArticle> list = articleDao.queryTopRecommendList();
		for (TArticle tArticle : list) {
			if (StringUtils.hasText(tArticle.getCoverimage())) {
				String url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
				tArticle.setCoverimage(url+"/"+SysConfigUtil.getConfig("keeps_view_path")+"/"+Constants.ARTICLE_COVER_IMAGE_PATH+"/cut"+tArticle.getCoverimage());
			}
		}
		return list;
	}

	@Override
	public List<TArticle> queryTopNewList(HttpServletRequest request) {
		List<TArticle> list = articleDao.queryTopNewList();
		for (TArticle tArticle : list) {
			if (StringUtils.hasText(tArticle.getCoverimage())) {
				String url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
				tArticle.setCoverimage(url+"/"+SysConfigUtil.getConfig("keeps_view_path")+"/"+Constants.ARTICLE_COVER_IMAGE_PATH+"/cut"+tArticle.getCoverimage());
			}
		}
		return list;
	}

	@Override
	public List<TArticle> queryTopHotList(HttpServletRequest request) {
		List<TArticle> list = articleDao.queryTopHotList();
		for (TArticle tArticle : list) {
			if (StringUtils.hasText(tArticle.getCoverimage())) {
				String url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
				tArticle.setCoverimage(url+"/"+SysConfigUtil.getConfig("keeps_view_path")+"/"+Constants.ARTICLE_COVER_IMAGE_PATH+"/cut"+tArticle.getCoverimage());
			}
		}
		return list;
	}
	
	public TArticle getById(Integer id,HttpServletRequest request){
		TArticle tArticle = super.get(TArticle.class, id);
		if (StringUtils.hasText(tArticle.getCoverimage())) {
			String url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
			tArticle.setCoverimage(url+"/"+SysConfigUtil.getConfig("keeps_view_path")+"/"+Constants.ARTICLE_COVER_IMAGE_PATH+"/cut"+tArticle.getCoverimage());
			
		}
		return tArticle;
	}

}
