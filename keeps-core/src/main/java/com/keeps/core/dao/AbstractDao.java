package com.keeps.core.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.keeps.core.model.AbstractSoftModelEntity;
import com.keeps.tools.utils.PageEnum;
import com.keeps.tools.utils.PropertyUtils;
import com.keeps.tools.utils.QlEnum;
import com.keeps.tools.utils.StringUtils;
import com.keeps.tools.utils.page.ListPage;
import com.keeps.tools.utils.page.Page;

@Component
public abstract class AbstractDao extends AbstractDaoHibernate4 {
	public static final String PARAM_REX = ":\\w+\\s";
	public Page queryByNameParam(String qlstring, String[] params, AbstractSoftModelEntity model, QlEnum type) {
		return queryByNameParam(qlstring, params, model, type, null);
	}

	public Page queryByNameParam(String qlstring, String[] params, AbstractSoftModelEntity model, QlEnum type,
			Class returnType) {
		Page page = new ListPage();
		initPageByModel(page, model);
		List<Object> values = null;
		if (params != null) {
			values = new ArrayList();
			String[] arrayOfString;
			int j = (arrayOfString = params).length;
			for (int i = 0; i < j; i++) {
				String param = arrayOfString[i];
				values.add(PropertyUtils.getProperty(model, param));
			}
		}
		return super.queryByNameParam(qlstring, params, values != null ? values.toArray() : null, type,
				PageEnum.PAGE_NEED, page, returnType);
	}

	public Page queryByPropertyHql(String qlstring, Object[] values, AbstractSoftModelEntity model) {
		return queryByNameParamHql(qlstring, null, values, model);
	}

	public Page queryByPropertyHql(String qlstring, Object[] values, AbstractSoftModelEntity model, Class returType) {
		return queryByNameParamHql(qlstring, null, values, model, returType);
	}

	public Page queryByNameParamHql(String qlstring, String[] params, Object[] values, Page page) {
		return super.queryByNameParam(qlstring, params, values, QlEnum.HQL, PageEnum.PAGE_NEED, page, null);
	}

	public Page queryByNameParamHql(String qlstring, String[] params, Object[] values, AbstractSoftModelEntity model,
			Class returnType) {
		Page page = new ListPage();
		initPageByModel(page, model);
		return super.queryByNameParam(qlstring, params, values, QlEnum.HQL, PageEnum.PAGE_NEED, page, returnType);
	}

	public Page queryByNameParamHql(String qlstring, String[] params, Object[] values, Page page, Class returnType) {
		return super.queryByNameParam(qlstring, params, values, QlEnum.HQL, PageEnum.PAGE_NEED, page, returnType);
	}

	public Page queryByNameParamSql(String qlstring, String[] params, Object[] values, AbstractSoftModelEntity model,
			Class returnType) {
		Page page = new ListPage();
		initPageByModel(page, model);
		return super.queryByNameParam(qlstring, params, values, QlEnum.SQL, PageEnum.PAGE_NEED, page, returnType);
	}

	public Page queryBySql(String qlstring, Object[] values, AbstractSoftModelEntity model, Class returnType) {
		return queryByNameParamSql(qlstring, null, values, model, returnType);
	}

	protected String rechangeBeforeQuery(String qlstring, Page pageModel) {
		return qlstring;
	}

	private String[] paramCreator(String qlstring) {
		Pattern p = Pattern.compile(":\\w+\\s");
		Matcher m = p.matcher(qlstring);
		List<String> params = new ArrayList();
		while (m.find()) {
			String t = m.group().trim();
			if (StringUtils.hasText(t)) {
				params.add(t.substring(1, t.length()));
			}
		}
		return (String[]) params.toArray(new String[params.size()]);
	}

	public Page queryBySql(String sql, AbstractSoftModelEntity model) {
		return queryBySql(sql, paramCreator(sql), model);
	}

	public Page queryByHql(String hql, AbstractSoftModelEntity model) {
		return queryByNameParam(hql + " ", paramCreator(hql), model, QlEnum.HQL);
	}

	public Page queryBySql(String sql, String[] params, AbstractSoftModelEntity model) {
		return queryByNameParam(sql + " ", params, model, QlEnum.SQL);
	}

	public Page queryBySql(String sql, Object[] values, AbstractSoftModelEntity model) {
		return queryByNameParamSql(sql, null, values, model);
	}

	public Page queryBySql4Values(String sql, Object[] values, AbstractSoftModelEntity model) {
		return queryByNameParamSql(sql, paramCreator(sql), values, model);
	}

	public Page queryByHql4Values(String hql, Object[] values, AbstractSoftModelEntity model) {
		return queryByNameParamHql(hql, paramCreator(hql), values, model);
	}

	public Page queryByHql(String hql, Object[] values, AbstractSoftModelEntity model) {
		return queryByNameParamHql(hql, null, values, model);
	}

	public Page queryByNameParamSql(String sql, String[] params, Object[] values, AbstractSoftModelEntity model) {
		Page page = new ListPage();
		initPageByModel(page, model);
		return super.queryByNameParamSql(sql, params, values, page);
	}

	public Page queryByNameParamHql(String sql, String[] params, Object[] values, AbstractSoftModelEntity model) {
		Page page = new ListPage();
		initPageByModel(page, model);
		return super.queryByNameParam(sql, params, values, QlEnum.HQL, PageEnum.PAGE_NEED, page, null);
	}

	private void initPageByModel(Page page, AbstractSoftModelEntity model) {
		page.setPage(Integer.valueOf(model.getPage() == null ? 1 : model.getPage().intValue()));
		page.setRows(model.getRows() == null ? Page.DEFAULT_PAGE_ROWS : model.getRows());
		page.setQuerynum(model.getQuerynum());
	}
}
