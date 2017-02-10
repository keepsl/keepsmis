package com.keeps.core.dao;

import java.util.List;
import java.util.Map;

import com.keeps.tools.utils.PageEnum;
import com.keeps.tools.utils.QlEnum;
import com.keeps.tools.utils.page.Page;

public abstract interface SoftDao extends SoftTopDao{
	  public abstract List getByPropertySql(String paramString, Object[] paramArrayOfObject, Class paramClass);

	  public abstract List getByNameParamSql(String paramString, String[] paramArrayOfString, Object[] paramArrayOfObject, Class paramClass);

	  public abstract Page queryByNameParamSql(String paramString, String[] paramArrayOfString, Object[] paramArrayOfObject, PageEnum paramPageEnum, Page paramPage, Class paramClass);

	  public abstract Integer getTotalCountByNameParamSql(String paramString, String[] paramArrayOfString, Object[] paramArrayOfObject);

	  public abstract Integer executeSQL(String paramString, String[] paramArrayOfString, Object[] paramArrayOfObject);

	  public abstract Integer executeSQL(String paramString1, String paramString2, Object paramObject);

	  public abstract Integer executeSQL(String paramString);

	  public abstract Integer executeSQL(String paramString, Object[] paramArrayOfObject);

	  public abstract Integer executeHQL(String paramString, String[] paramArrayOfString, Object[] paramArrayOfObject);

	  public abstract Integer executeHQL(String paramString1, String paramString2, Object paramObject);

	  public abstract Integer executeHQL(String paramString);

	  public abstract Integer executeHQL(String paramString, Object[] paramArrayOfObject);

	  public abstract List getByPropertySql(String paramString, Object[] paramArrayOfObject);

	  public abstract List getByNameParamSql(String paramString, String[] paramArrayOfString, Object[] paramArrayOfObject);

	  public abstract Page queryByNameParamSql(String paramString, String[] paramArrayOfString, Object[] paramArrayOfObject);

	  public abstract Page queryByNameParamSql(String paramString, String[] paramArrayOfString, Object[] paramArrayOfObject, Page paramPage);

	  public abstract Page queryByNameParamSqlMap(String paramString, String[] paramArrayOfString, Object[] paramArrayOfObject);

	  public abstract Page queryByNameParamSqlMap(String paramString, String[] paramArrayOfString, Object[] paramArrayOfObject, Page paramPage);

	  public abstract Page queryByPropertySql(String paramString, Object[] paramArrayOfObject, Page paramPage);

	  public abstract Page queryByPropertySql(String paramString, Object[] paramArrayOfObject, Page paramPage, PageEnum paramPageEnum);

	  public abstract Page queryByPropertySql(String paramString, Object[] paramArrayOfObject, Class paramClass);

	  public abstract Page queryByPropertySql(String paramString, String[] paramArrayOfString, Object[] paramArrayOfObject, Class paramClass);

	  public abstract List getByPropertyHql(String paramString, Object[] paramArrayOfObject, Class paramClass);

	  public abstract List getByNameParamHql(String paramString, String[] paramArrayOfString, Object[] paramArrayOfObject, Class paramClass);

	  public abstract List getByPropertyHql(String paramString, Object[] paramArrayOfObject);

	  public abstract List getByPropertyHql(String paramString, Object paramObject);

	  public abstract List getByNameParamHql(String paramString, String[] paramArrayOfString, Object[] paramArrayOfObject);

	  public abstract List<Map> executeCall(String paramString, String[] paramArrayOfString, boolean paramBoolean);

	  public abstract <T> List<T> executeCall(String paramString, String[] paramArrayOfString, Class<T> paramClass);

	  public abstract <T> List<T> getByPropertyTop(String paramString, String[] paramArrayOfString, Object[] paramArrayOfObject, QlEnum paramQlEnum, Integer paramInteger, Class<T> paramClass);

	  public abstract <T> List<T> getByPropertySqlTop(String paramString, String[] paramArrayOfString, Object[] paramArrayOfObject, Integer paramInteger, Class<T> paramClass);

	  public abstract <T> List<T> getByPropertySqlTop(String paramString, Object[] paramArrayOfObject, Integer paramInteger, Class<T> paramClass);

	  public abstract <T> List<T> getByPropertyHqlTop(String paramString, Object[] paramArrayOfObject, Integer paramInteger, Class<T> paramClass);

	  public abstract <T> List<T> getByPropertyHqlTop(String paramString, String[] paramArrayOfString, Object[] paramArrayOfObject, Integer paramInteger, Class<T> paramClass);
}
