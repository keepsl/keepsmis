package com.keeps.core.service;

import java.io.Serializable;
import java.util.List;

import com.keeps.core.dao.SoftDao;
import com.keeps.core.model.listener.RemoveRollback;
import com.keeps.core.model.utils.IdTypes;
import com.keeps.tools.utils.EditType;

public interface SoftService  extends SoftTopService{
	  public SoftDao getDao();

	  public String saveOrUpdateEntity(Object paramObject, String[] paramArrayOfString, EditType paramEditType);

	  public String saveOrUpdateEntity(Object paramObject);

	  public String saveOrUpdateEntity(Object paramObject, EditType paramEditType);

	  public void saveOrUpdateAllEntity(List paramList);

	  public void saveOrUpdateAllEntity(List paramList, EditType paramEditType);

	  public Integer removeEntity(Class paramClass, Serializable paramSerializable);

	  public Integer removeEntity(Class paramClass, Serializable paramSerializable, IdTypes paramIdTypes);

	  public Integer removeEntity(Class paramClass, Serializable paramSerializable, RemoveRollback paramRemoveRollback);

	  public void removeEntity(Class paramClass, RemoveRollback paramRemoveRollback);
}
