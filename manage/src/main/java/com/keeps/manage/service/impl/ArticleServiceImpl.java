package com.keeps.manage.service.impl;

import java.io.File;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.keeps.core.service.AbstractService;
import com.keeps.manage.dao.ArticleDao;
import com.keeps.manage.service.ArticleService;
import com.keeps.utils.Constants;
import com.keeps.utils.ImageUtil;
import com.keeps.utils.SysConfigUtil;
import com.keeps.model.TArticle;
import com.keeps.tools.exception.CapecException;
import com.keeps.tools.utils.Assert;
import com.keeps.tools.utils.CommonUtils;
import com.keeps.tools.utils.EditType;
import com.keeps.tools.utils.StringUtils;
import com.keeps.tools.utils.page.Page;

/** 
 * <p>Title: ArticleServiceImpl.java</p>  
 * <p>Description: 文章SERVICE实现类 </p>  
 * <p>Copyright: Copyright (c) KEEPS</p>  
 * @author keeps
 * @version v 1.00
 * @date 创建日期：2017年1月19日
 * 修改日期：
 * 修改人：
 * 复审人：
 */
@Service
public class ArticleServiceImpl extends AbstractService implements ArticleService {

	@Autowired
	private ArticleDao articleDao;

	@Override
	public Page queryList(TArticle article) {
		return articleDao.queryList(article);
	}

	@Override
	public TArticle getById(Integer id,HttpServletRequest request) {
		TArticle article = super.get(TArticle.class, id);
		if (StringUtils.hasText(article.getCoverimage())) {
			article.setViewcoverimage(SysConfigUtil.getConfig("keeps_upload_path")+"/"+Constants.ARTICLE_COVER_IMAGE_PATH+"/"+"cut"+article.getCoverimage());
		}
		return article;
	}

	@Override
	public String saveOrUpdate(TArticle article,MultipartFile coverfile, HttpServletRequest request) {
		Assert.isTrue(StringUtils.hasLength(article.getTitle()), "标题不能为空!");
		Assert.isTrue(StringUtils.hasLength(article.getContenttext()), "内容不能为空!");
		Assert.isTrue(article.getTypeid()!=null, "文章所属栏目不能为空!");
		if (StringUtils.notText(article.getAbstract_())) {
			article.setAbstract_(article.getContenttext().length()>200?article.getContenttext().substring(0,200):article.getContenttext());
		}
		Assert.isTrue(article.getIsshow()!=null, "是否展示不能为空!");
		Assert.isTrue(article.getIshot()!=null, "是否热门不能为空!");
		Assert.isTrue(article.getIsrecommend()!=null, "是否推荐不能为空!");
		Assert.isTrue(article.getClicknum()!=null, "点击量不能为空!");
		Assert.isTrue(StringUtils.hasLength(article.getPublishtime()), "发布日期不能为空!");
		Assert.isTrue(article.getIspublish()!=null, "发布状态不能为空!");//1未发布，2已发布
		TArticle newarticle = new TArticle();
		if (article.getId()!=null) {
			newarticle = super.get(TArticle.class, article.getId());
		}
		String path = Constants.uploadPath+File.separator+Constants.ARTICLE_COVER_IMAGE_PATH;
		if (coverfile==null) {
			//=1（截取第一张图做为封面图）并且原图是空的
			if (article.getIscoverimage()!=null && article.getIscoverimage()==1 && StringUtils.notText(article.getCoverimage())) {
				//保存第一张图片  TODO
				//原图存在，删除原图片
				if (StringUtils.hasText(newarticle.getCoverimage())) {
					File file = new File(Constants.uploadPath, newarticle.getCoverimage());
					file.delete();
					file = new File(Constants.uploadPath, "cut"+newarticle.getCoverimage());
					file.delete();
				}
			}
		}else{
			String originalFilename = coverfile.getOriginalFilename();
			String filetype = originalFilename.substring(coverfile.getOriginalFilename().lastIndexOf(StringUtils.Symbol.POINT)+1);
			if(!CommonUtils.isExistStr(filetype.toLowerCase(), new String[]{"jpg","bmp","gif","png"}))
				throw new CapecException("不允许上传" + filetype + "格式的文件！");
			String filename = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf(StringUtils.Symbol.POINT), originalFilename.length());
			File targetFile = new File(path, filename);
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}
			try {
				coverfile.transferTo(targetFile);
			} catch (Exception e) {
				log.error(e);
				throw new CapecException("文件上传失败!");
			}
			Assert.isTrue(targetFile.length() / (1024 * 1024) <= Constants.MAX_UPLOAD_IMAGE_SIZE,"上传的图片大小请控制在" + Constants.MAX_UPLOAD_IMAGE_SIZE + "M内!");
			ImageUtil.createThumbnail(targetFile.getPath(), path + File.separator + "cut"+filename,Constants.MAX_COVER_IMAGE_WIDTH, Constants.MAX_COVER_IMAGE_HEIGHT);
			//原图存在，删除原图片
			if (StringUtils.hasText(newarticle.getCoverimage())) {
				File file = new File(path, newarticle.getCoverimage());
				file.delete();
				file = new File(path, "cut"+newarticle.getCoverimage());
				file.delete();
			}
			article.setCoverimage(filename);
		}
		article.setIsdelete(1);//正常
		article.setCreatetime(newarticle.getCreatetime());
		if (article.getId()==null) {
			super.save(article);
		}else{
			BeanUtils.copyProperties(article, newarticle);
			super.update(newarticle,EditType.NULL_UPDATE);
		}
		return String.valueOf(article.getId()+"|"+article.getCoverimage());
	}
	
	public String updateFieidById(String fieid,Integer value,String ids){
		articleDao.updateFieidById(fieid, value, ids);
		return "删除成功!";
	}

}
