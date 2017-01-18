package com.keeps.core.dao;

import java.io.Serializable;
import java.util.List;

import com.keeps.core.model.SoftEntityModel;
import com.keeps.core.model.utils.IdTypes;
import com.keeps.tools.utils.EditStateEnum;
import com.keeps.tools.utils.PageEnum;
import com.keeps.tools.utils.QlEnum;
import com.keeps.tools.utils.page.Page;

public abstract interface SoftTopDao {
	public abstract <T> Object saveOrUpdateEntity(T paramT, EditStateEnum paramEditStateEnum);

	public abstract <T> Serializable save(T paramT, EditStateEnum paramEditStateEnum);

	public abstract <T> Serializable update(T paramT, EditStateEnum paramEditStateEnum);

	public abstract <T> void saveOrUpdateEntity(List<T> paramList, EditStateEnum paramEditStateEnum);

	public abstract <T> void remove(T paramT, EditStateEnum paramEditStateEnum);

	public abstract <T> Integer remove4Ids(Class<T> paramClass, EditStateEnum paramEditStateEnum,
			Serializable paramSerializable);

	public abstract <T> Integer remove4Ids(Class<T> paramClass, EditStateEnum paramEditStateEnum,
			Serializable paramSerializable, IdTypes paramIdTypes);

	public abstract <T> T get(Class<T> paramClass, Serializable paramSerializable);

	public abstract <T> T get(Class<T> paramClass, Serializable paramSerializable, String[] paramArrayOfString);

	public abstract List getByProperty(String paramString, Object[] paramArrayOfObject, QlEnum paramQlEnum,
			Class paramClass);

	public abstract List getByNameParam(String paramString, String[] paramArrayOfString, Object[] paramArrayOfObject,
			QlEnum paramQlEnum, Class paramClass);

	public abstract Page queryByNameParam(String paramString, String[] paramArrayOfString, Object[] paramArrayOfObject,
			QlEnum paramQlEnum, PageEnum paramPageEnum, Page paramPage, Class paramClass);

	public abstract Integer getTotalCountByNameParam(String paramString, String[] paramArrayOfString,
			Object[] paramArrayOfObject, QlEnum paramQlEnum);

	public abstract Integer executeQl(String paramString, String[] paramArrayOfString, Object[] paramArrayOfObject,
			QlEnum paramQlEnum);

	public abstract <T extends SoftEntityModel> boolean isUnique(T paramT, String[] paramArrayOfString);

	public abstract <T extends SoftEntityModel> boolean isUniqueQl(T paramT, String paramString,
			String[] paramArrayOfString);

	public abstract List getAll(Class paramClass, String[] paramArrayOfString);

	public abstract List getAll(Class paramClass);
}
