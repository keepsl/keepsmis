package com.keeps.core.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.keeps.core.dao.SoftDao;
import com.keeps.core.dao.base.BasicDao;
import com.keeps.tools.exception.CapecException;
import com.keeps.core.model.AbstractModelEmbedded;
import com.keeps.core.model.SoftEntityModel;
import com.keeps.core.model.listener.RemoveListener;
import com.keeps.core.model.listener.RemoveRollback;
import com.keeps.core.model.listener.SaveOrUpdateListener;
import com.keeps.core.model.utils.IdTypes;
import com.keeps.core.model.utils.ModelId4DB;
import com.keeps.core.model.utils.ModelUtil;
import com.keeps.tools.utils.Assert;
import com.keeps.tools.utils.ClassUtils;
import com.keeps.tools.utils.EditStateEnum;
import com.keeps.tools.utils.EditType;
import com.keeps.tools.utils.PropertyUtils;
import com.keeps.tools.utils.StringUtils;

@Component
public abstract class AbstractService implements SoftService {
	protected Log log = LogFactory.getLog(super.getClass());

	@Autowired
	@Qualifier("basicDao")
	private BasicDao basicDao;

	public SoftDao getDao() {
		return this.basicDao;
	}

	public <T> T get(Class<T> clazz, Object id, String[] fields) {
		if (id == null) {
			return null;
		}
		T t;
		if (id instanceof Serializable)
			t = getDao().get(clazz, (Serializable) id, fields);
		else {
			t = getDao().get(clazz, id.toString(), fields);
		}
		if (t instanceof SoftEntityModel) {
			SoftEntityModel st = (SoftEntityModel) t;
			st.setGet(Boolean.TRUE);
		}
		return t;
	}

	public <T> T get(Class<T> clazz, Object id) {
		return get(clazz, id, null);
	}

	public List getAll(Class clazz) {
		return getAll(clazz, null);
	}

	public List getAll(Class clazz, String[] fields) {
		Assert.isTrue(clazz != null, "缺少必要参数=clazz");
		if ((fields == null) || (fields.length <= 0)) {
			return getDao().getAll(clazz);
		}
		return getDao().getAll(clazz, fields);
	}

	public Integer removeEntity(Class clazz, Serializable ids, EditStateEnum rightNow, RemoveRollback rollback,
			IdTypes types) {
		Assert.isTrue(clazz != null, "缺少必要参数=clazz");
		if (ClassUtils.isImplOf(clazz, RemoveListener.class)) {
			((RemoveListener) ClassUtils.newInstance(clazz)).onRemove(this);
		}
		if ((rollback != null) && (ids instanceof String)) {
			String[] idds = ids.toString().split(",");
			for (String id : idds) {
				if (StringUtils.notText(id)) {
					continue;
				}
				rollback.doRemove(id);
			}
		}

		return getDao().remove4Ids(clazz, rightNow, ids, types);
	}

	public Integer removeEntity(Class clazz, Serializable ids, IdTypes types) {
		return removeEntity(clazz, ids, EditStateEnum.RIGHT_NOW_NO, null, types);
	}

	public Integer removeEntity(Class clazz, Serializable ids, RemoveRollback rollback, IdTypes types) {
		return removeEntity(clazz, ids, EditStateEnum.RIGHT_NOW_NO, rollback, types);
	}

	public Integer removeEntity(Class clazz, Serializable ids, EditStateEnum rightNow, RemoveRollback rollback) {
		return removeEntity(clazz, ids, rightNow, rollback, IdTypes.String);
	}

	public void removeEntity(Class clazz, RemoveRollback rollback) {
		removeEntity(clazz, null, rollback);
	}

	public <T> void saveOrUpdateAllEntity(List<T> entities, EditStateEnum rightNow, String[] withoutFields,
			EditType type) {
		if ((entities == null) || (entities.isEmpty())) {
			return;
		}
		List souList = new ArrayList(entities.size());

		Boolean f = Boolean.valueOf(false);

		for (Object entity : entities) {
			if (entity == null) {
				continue;
			}
			if (entity instanceof SaveOrUpdateListener) {
				((SaveOrUpdateListener) entity).onSaveOrUpdate(this);
			}

			if ((entity instanceof SoftEntityModel) && (!(entity instanceof AbstractModelEmbedded))) {
				SoftEntityModel entity_new = (SoftEntityModel) entity;
				boolean isSave = entity_new.isSave();
				if (!(f.booleanValue())) {
					withoutFields = ModelUtil.modelPlusFields(entity_new, Boolean.valueOf(isSave), withoutFields);
					f = Boolean.valueOf(true);
				}
				if (isSave) {
					if (!(entity_new instanceof ModelId4DB))
						entity_new.idhandle();
				} else if (!(entity_new.isGet().booleanValue())) {
					SoftEntityModel u = (SoftEntityModel) get(entity_new.getClass(), entity_new.getId());

					PropertyUtils.copyTo(entity_new, u, type, withoutFields);

					entity_new = u;
				}
				ModelUtil.modelPlusCreator(entity_new, Boolean.valueOf(isSave));
				souList.add(entity_new);
			} else {
				souList.add(entity);
			}
		}
		getDao().saveOrUpdateEntity(souList, rightNow);
	}

	public Serializable save(Object entity) {
		if (entity == null) {
			this.log.error("entity is null");
			return "";
		}
		if (entity instanceof SaveOrUpdateListener) {
			((SaveOrUpdateListener) entity).onSaveOrUpdate(this);
		}
		if (entity instanceof SoftEntityModel) {
			SoftEntityModel entity_new = (SoftEntityModel) entity;
			ModelUtil.modelPlusCreator(entity_new, Boolean.valueOf(true));
		}
		return getDao().save(entity, EditStateEnum.RIGHT_NOW_NO);
	}

	public Serializable update(Object entity) {
		return update(entity, EditType.NULL_UN_UPDATE);
	}

	public Serializable update(Object entity, EditType type) {
		return update(entity, EditStateEnum.RIGHT_NOW_NO, type);
	}

	public Serializable update(Object entity, EditStateEnum rightnow, EditType type) {
		if (entity == null) {
			this.log.error("entity is null");
			return "";
		}
		if (entity instanceof SaveOrUpdateListener) {
			((SaveOrUpdateListener) entity).onSaveOrUpdate(this);
		}
		if (entity instanceof SoftEntityModel) {
			SoftEntityModel e = (SoftEntityModel) entity;
			if (e.isSave()) {
				throw new NullPointerException("在进行更新时，id不能为空值");
			}
			if ((type != null) && (type.equals(EditType.NULL_UN_UPDATE))) {
				SoftEntityModel u = (SoftEntityModel) get(e.getClass(), e.getId());
				if (u != null) {
					PropertyUtils.copyTo(entity, u, type, null);
					entity = u;
				} else {
					throw new CapecException("该数据已经不存在，不能操作数据");
				}
			}
			ModelUtil.modelPlusCreator((SoftEntityModel) entity, Boolean.valueOf(false));
		}
		return getDao().update(entity, rightnow);
	}

	public String saveOrUpdateEntity(Object entity, EditStateEnum rightNow, String[] withoutFields, EditType type) {
		if (entity == null)
			return "";
		if (entity instanceof SaveOrUpdateListener) {
			((SaveOrUpdateListener) entity).onSaveOrUpdate(this);
		}
		Object r = null;
		if (entity instanceof SoftEntityModel) {
			if (entity instanceof AbstractModelEmbedded) {
				r = getDao().saveOrUpdateEntity(entity, rightNow);
			} else {
				SoftEntityModel entity_new = (SoftEntityModel) entity;
				boolean isSave = entity_new.isSave();
				withoutFields = ModelUtil.modelPlusFields(entity_new, Boolean.valueOf(isSave), withoutFields);
				if (!(isSave))
					if (!(entity_new.isGet().booleanValue())) {
						SoftEntityModel u = null;
						u = (SoftEntityModel) get(entity_new.getClass(), entity_new.getId());

						if (u != null) {
							PropertyUtils.copyTo(entity_new, u, type, withoutFields);
							entity_new = u;
						} else {
							throw new CapecException("该数据已经不存在，不能操作数据");
						}
					} else if (!(entity_new instanceof ModelId4DB)) {
						entity_new.idhandle();
					}
				ModelUtil.modelPlusCreator(entity_new, Boolean.valueOf(isSave));
				r = getDao().saveOrUpdateEntity(entity_new, rightNow);
			}
		} else
			r = getDao().saveOrUpdateEntity(entity, rightNow);
		if (r == null) {
			return null;
		}
		if (r instanceof String)
			return ((String) r);
		if ((r instanceof Integer) || (r instanceof Long) || (r instanceof Short)) {
			return String.valueOf(r);
		}

		return null;
	}

	public Integer removeEntity(Class clazz, Serializable ids) {
		return removeEntity(clazz, ids, EditStateEnum.RIGHT_NOW_NO, null);
	}

	public Integer removeEntity(Class clazz, Serializable ids, RemoveRollback rollback) {
		return removeEntity(clazz, ids, EditStateEnum.RIGHT_NOW_NO, rollback);
	}

	public void saveOrUpdateAllEntity(List entities) {
		saveOrUpdateAllEntity(entities, EditStateEnum.RIGHT_NOW_NO, null, EditType.NULL_UPDATE);
	}

	public void saveOrUpdateAllEntity(List entities, EditType type) {
		saveOrUpdateAllEntity(entities, EditStateEnum.RIGHT_NOW_NO, null, type);
	}

	public String saveOrUpdateEntity(Object entity, String[] withoutFields, EditType type) {
		return saveOrUpdateEntity(entity, EditStateEnum.RIGHT_NOW_NO, withoutFields, type);
	}

	public String saveOrUpdateEntity(Object entity) {
		return saveOrUpdateEntity(entity, EditType.NULL_UPDATE);
	}

	public String saveOrUpdateEntity(Object entity, EditType type) {
		return saveOrUpdateEntity(entity, null, type);
	}
}
