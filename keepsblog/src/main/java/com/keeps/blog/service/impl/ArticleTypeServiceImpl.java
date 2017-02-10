package com.keeps.blog.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keeps.blog.dao.ArticleTypeDao;
import com.keeps.blog.service.ArticleTypeService;
import com.keeps.core.service.AbstractService;
import com.keeps.model.TArticleType;
import com.keeps.tools.utils.AsyncTreeNode;

/** 
 * <p>Title: ArticleTypeServiceImpl.java</p>  
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
public class ArticleTypeServiceImpl extends AbstractService implements ArticleTypeService {
	
	@Autowired
	private ArticleTypeDao articleTypeDao;
	
	public AsyncTreeNode queryListMenu(){
		List<TArticleType> listarticle = articleTypeDao.queryListAll();
		AsyncTreeNode treeNode = new AsyncTreeNode();
		loadMenuChild(listarticle,treeNode,0,true);
		return treeNode;
	}

	private static void loadMenuChild(List<TArticleType> listarticle,AsyncTreeNode treeNode,Integer pid,boolean ishref){
		List<TArticleType> childList = getArticleTypeListPid(listarticle,pid);
		if(childList.size()!=0) {
			List<AsyncTreeNode> listtree = new ArrayList<>();
			for(TArticleType menu:childList){
				AsyncTreeNode tree = new AsyncTreeNode();
				tree.setId(menu.getId());
				tree.setName(menu.getName());
				loadMenuChild(listarticle,tree,menu.getId(),ishref);
				listtree.add(tree);
			}
			treeNode.setChildren(listtree);
		}
	}

	private static List<TArticleType> getArticleTypeListPid(List<TArticleType> list,Integer pid){
		List<TArticleType> newArticleType = new ArrayList<TArticleType>();
		for (TArticleType articleType : list) {
			if(articleType.getPid().equals(pid))
				newArticleType.add(articleType);
		}
		return newArticleType;
	}
}
