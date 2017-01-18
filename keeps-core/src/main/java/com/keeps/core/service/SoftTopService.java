package com.keeps.core.service;

import java.io.Serializable;
import java.util.List;

import com.keeps.core.model.listener.RemoveRollback;
import com.keeps.core.model.utils.IdTypes;
import com.keeps.tools.utils.EditStateEnum;
import com.keeps.tools.utils.EditType;

public interface SoftTopService {
	public Object saveOrUpdateEntity(Object paramObject, EditStateEnum paramEditStateEnum,
			String[] paramArrayOfString, EditType paramEditType);

	public Serializable save(Object paramObject);

	public Serializable update(Object paramObject);

	public <T> void saveOrUpdateAllEntity(List<T> paramList, EditStateEnum paramEditStateEnum,
			String[] paramArrayOfString, EditType paramEditType);

	public Integer removeEntity(Class paramClass, Serializable paramSerializable,
			EditStateEnum paramEditStateEnum, RemoveRollback paramRemoveRollback);

	public Integer removeEntity(Class paramClass, Serializable paramSerializable,
			EditStateEnum paramEditStateEnum, RemoveRollback paramRemoveRollback, IdTypes paramIdTypes);

	public <T> T get(Class<T> paramClass, Object paramObject);

	public <T> T get(Class<T> paramClass, Object paramObject, String[] paramArrayOfString);

	public List getAll(Class paramClass, String[] paramArrayOfString);

	public List getAll(Class paramClass);
}
