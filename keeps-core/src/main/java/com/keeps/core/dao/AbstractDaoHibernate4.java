package com.keeps.core.dao;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.hibernate.Query;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.ReturningWork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.keeps.tools.exception.CapecException;
import com.keeps.core.hibernate.AliasToEntityHashMapResultTransformer;
import com.keeps.core.hibernate.AliasToEntityLinkMapResultTransformer;
import com.keeps.core.hibernate.AliasToEntityTreeMapResultTransformer;
import com.keeps.core.model.SoftEntityModel;
import com.keeps.core.model.utils.IdTypes;
import com.keeps.tools.utils.Assert;
import com.keeps.tools.utils.EditStateEnum;
import com.keeps.tools.utils.HqlUtils;
import com.keeps.tools.utils.NumberUtils;
import com.keeps.tools.utils.PageEnum;
import com.keeps.tools.utils.PropertyUtils;
import com.keeps.tools.utils.QlEnum;
import com.keeps.tools.utils.StringUtils;
import com.keeps.tools.utils.SupcanTable;
import com.keeps.tools.utils.page.ListPage;
import com.keeps.tools.utils.page.Page;
import com.keeps.tools.utils.page.SingleMap;

public abstract class AbstractDaoHibernate4 extends AbstractDaoSupport4 {
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	public Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	protected Object doMethod(EditStateEnum state, DaoMethodTemplate template) {
		Object r = null;
		if (EditStateEnum.RIGHT_NOW_YES.equals(state)) {
			getSession().clear();
			r = template.rollbockDaoMethod();
			getSession().flush();
		} else {
			r = template.rollbockDaoMethod();
		}
		return r;
	}

	public <T> T get(Class<T> clazz, Serializable id) {
		return (T) get(clazz, id, null);
	}

	public <T> T get(Class<T> clazz, Serializable id, String[] fields) {
		if ((id == null) || (StringUtils.notText(id.toString()))) {
			return null;
		}
		if ((fields == null) || (fields.length <= 0)) {
			return (T) getSession().get(clazz, id);
		}
		StringBuffer qlstring = new StringBuffer();
		qlstring.append(" select  ");
		qlstring.append(HqlUtils.arrayToString(fields));
		qlstring.append(" from " + clazz.getName());
		qlstring.append(" where id=:id");
		List<T> tl = getByNameParamHql(qlstring.toString(), new String[] { "id" }, new Object[] { id }, clazz);
		return (tl == null) || (tl.isEmpty()) ? null : tl.get(0);
	}

	public List getByNameParam(String qlstring, String[] params, Object[] values, QlEnum qltype, Class returnType) {
		return queryByNameParam(qlstring, params, values, qltype, PageEnum.PAGE_NO, new ListPage(), returnType)
				.getRecord();
	}

	public List getByProperty(String qlstring, Object[] values, QlEnum qltype, Class returnType) {
		return queryByNameParam(qlstring, null, values, qltype, PageEnum.PAGE_NO, new ListPage(), returnType)
				.getRecord();
	}

	protected abstract String rechangeBeforeQuery(String paramString, Page paramPage);

	public Page queryByNameParam(String qlstring, String[] params, Object[] values, QlEnum type, PageEnum needPage,
			Page pageModel, Class returnType) {
		Assert.isTrue(StringUtils.hasText(qlstring));

		String qs = rechangeBeforeQuery(qlstring, pageModel);
		if (StringUtils.hasText(qs)) {
			qlstring = qs;
		}
		Query query = null;
		if (type.equals(QlEnum.SQL)) {
			query = getSession().createSQLQuery(qlstring);
		} else if (type.equals(QlEnum.HQL)) {
			query = getSession().createQuery(qlstring);
		}
		Boolean beginWithFrom = Boolean.valueOf(qlstring.trim().startsWith("from"));
		if ((beginWithFrom.booleanValue()) && (!type.equals(QlEnum.HQL))) {
			throw new CapecException("以from开头的只能是HQL语句，请检查" + getClass() + "方法！");
		}
		if (!beginWithFrom.booleanValue()) {
			if (TreeMap.class.equals(returnType)) {
				query.setResultTransformer(AliasToEntityTreeMapResultTransformer.INSTANCE);
			} else if (LinkedHashMap.class.equals(returnType)) {
				query.setResultTransformer(AliasToEntityLinkMapResultTransformer.INSTANCE);
			} else if ((returnType != null) && (returnType != SingleMap.class)) {
				query.setResultTransformer(AliasToEntityHashMapResultTransformer.INSTANCE);
			}
		}
		if ((PageEnum.PAGE_NEED.equals(needPage)) || (PageEnum.PAGE_TOP.equals(needPage))) {
			query.setFirstResult(pageModel.getFirst().intValue());
			query.setMaxResults(pageModel.getRows().intValue());
		}
		if (params == null) {
			applyQueryParameters(query, values);
		} else {
			applyQueryNamedParameters(query, params, values);
		}
		List l = query.list();
		if ((returnType == null) || (beginWithFrom.booleanValue())) {
			pageModel.setRecord(l);
		} else if ((Map.class.equals(returnType)) || (TreeMap.class.equals(returnType))
				|| (HashMap.class.equals(returnType)) || (LinkedHashMap.class.equals(returnType))) {
			pageModel.setRecord(l);
		} else if (SingleMap.class.equals(returnType)) {
			pageModel.setRecord(converList2SinglMap(l));
		} else {
			List l2 = converList2Model(returnType, l);
			pageModel.setRecord(l2);
		}
		if (PageEnum.PAGE_NEED.equals(needPage)) {
			pageModel.setTotal(getTotalCountByNameParam(qlstring, params, values, type));
		}
		return pageModel;
	}

	public Integer getTotalCountByNameParam(String qlstring, String[] params, Object[] values, QlEnum type) {
		Query query = null;
		if (type.equals(QlEnum.SQL)) {
			String sql = " select count(1) from ( " + qlstring + " ) randomQueryTb";

			query = getSession().createSQLQuery(sql);
		} else if (type.equals(QlEnum.HQL)) {
			query = getSession().createQuery(qlstring);
		}
		if ((params == null) || (params.length <= 0)) {
			applyQueryParameters(query, values);
		} else {
			applyQueryNamedParameters(query, params, values);
		}
		if (type.equals(QlEnum.SQL)) {
			List l = query.list();
			return Integer.valueOf((l == null) || (l.isEmpty()) ? 0 : NumberUtils.toInteger(l.get(0)).intValue());
		}
		ScrollableResults scrollableResults = query.scroll(ScrollMode.SCROLL_SENSITIVE);
		scrollableResults.last();
		return Integer.valueOf(scrollableResults.getRowNumber() + 1);
	}

	public Integer executeQl(String qlstring, String[] params, Object[] values, QlEnum type) {
		Query query = null;
		if (QlEnum.SQL.equals(type)) {
			query = getSession().createSQLQuery(qlstring);
		} else if (QlEnum.HQL.equals(type)) {
			query = getSession().createQuery(qlstring);
		}
		if ((params == null) || (params.length <= 0)) {
			applyQueryParameters(query, values);
		} else {
			applyQueryNamedParameters(query, params, values);
		}
		return Integer.valueOf(query.executeUpdate());
	}

	public <T> void remove(final T entity, EditStateEnum state) {
		doMethod(state, new DaoMethodTemplate() {
			public Object rollbockDaoMethod() throws CapecException {
				AbstractDaoHibernate4.this.getSession().delete(entity);
				return null;
			}
		});
	}

	public <T> Integer remove4Ids(Class<T> clazz, EditStateEnum state, Serializable ids) {
		return remove4Ids(clazz, state, ids, IdTypes.String);
	}

	public <T> Integer remove4Ids(final Class<T> clazz, EditStateEnum state, final Serializable ids, IdTypes type) {
		if ((ids == null) || (clazz == null)) {
			return 0;
		}
		if ((IdTypes.String.equals(type)) && (ids instanceof String)) {
			return remove(clazz, state, new Object[] { ((String) ids).split(",") });
		}
		String[] arrayOfString1;
		int j;
		int i;
		if ((IdTypes.Integer.equals(type)) || (ids instanceof Integer)) {
			if ((ids instanceof String)) {
				String[] vvs = ids.toString().split(",");
				List<Integer> values = new ArrayList(vvs.length);
				j = (arrayOfString1 = vvs).length;
				for (i = 0; i < j; i++) {
					String vv = arrayOfString1[i];
					if (!StringUtils.notText(vv)) {
						values.add(NumberUtils.toInteger(vv));
					}
				}
				return remove(clazz, state, new Object[] { values.toArray() });
			}
			return remove(clazz, state, new Object[] { (Integer) ids });
		}
		if ((IdTypes.Long.equals(type)) || ((ids instanceof Long))) {
			if ((ids instanceof String)) {
				String[] vvs = ids.toString().split(",");
				List<Long> values = new ArrayList(vvs.length);
				j = (arrayOfString1 = vvs).length;
				for (i = 0; i < j; i++) {
					String vv = arrayOfString1[i];
					if (!StringUtils.notText(vv)) {
						values.add(NumberUtils.toLong(vv));
					}
				}
				return remove(clazz, state, new Object[] { values.toArray() });
			}
			return remove(clazz, state, new Object[] { (Long) ids });
		}
		return (Integer) doMethod(state, new DaoMethodTemplate() {
			public Object rollbockDaoMethod() throws CapecException {
				StringBuffer qlstring = new StringBuffer();
				qlstring.append(" delete from " + clazz.getName());
				qlstring.append(" where id = :ids ");
				return AbstractDaoHibernate4.this.executeQl(qlstring.toString(), new String[] { "ids" },
						new Object[] { ids }, QlEnum.HQL);
			}
		});
	}

	private <T> Integer remove(final Class<T> clazz, EditStateEnum state, final Object[] values) {
		return (Integer) doMethod(state, new DaoMethodTemplate() {
			public Object rollbockDaoMethod() throws CapecException {
				StringBuffer qlstring = new StringBuffer();
				qlstring.append(" delete from " + clazz.getName());
				qlstring.append(" where id in(:ids)");
				return AbstractDaoHibernate4.this.executeQl(qlstring.toString(), new String[] { "ids" }, values,
						QlEnum.HQL);
			}
		});
	}

	public <T> void saveOrUpdateEntity(final List<T> entities, EditStateEnum state) {
		doMethod(state, new DaoMethodTemplate() {
			public Object rollbockDaoMethod() throws CapecException {
				for (T entity : entities) {
					AbstractDaoHibernate4.this.getSession().saveOrUpdate(entity);
				}
				return null;
			}
		});
	}

	public <T> Object saveOrUpdateEntity(final T entity, EditStateEnum state) {
		return doMethod(state, new DaoMethodTemplate() {
			public Object rollbockDaoMethod() throws CapecException {
				AbstractDaoHibernate4.this.getSession().saveOrUpdate(entity);
				if ((entity instanceof SoftEntityModel)) {
					return ((SoftEntityModel) entity).getId();
				}
				return null;
			}
		});
	}

	public <T> Serializable save(final T entity, EditStateEnum state) {
		return (Serializable) doMethod(state, new DaoMethodTemplate() {
			public Object rollbockDaoMethod() throws CapecException {
				return AbstractDaoHibernate4.this.getSession().save(entity);
			}
		});
	}

	public <T> Serializable update(final T entity, EditStateEnum state) {
		return (Serializable) doMethod(state, new DaoMethodTemplate() {
			public Object rollbockDaoMethod() throws CapecException {
				AbstractDaoHibernate4.this.getSession().update(entity);
				if ((entity instanceof SoftEntityModel)) {
					return ((SoftEntityModel) entity).getId();
				}
				return null;
			}
		});
	}

	public Integer executeSQL(String qlstring, String param, Object value) {
		Assert.isTrue(StringUtils.hasText(param), "param不能为空", true);
		return executeSQL(qlstring, new String[] { param }, new Object[] { value });
	}

	public Integer executeSQL(String qlstring, String[] params, Object[] values) {
		return executeQl(qlstring, params, values, QlEnum.SQL);
	}

	public Integer executeSQL(String qlstring) {
		return executeSQL(qlstring, null);
	}

	public Integer executeSQL(String qlstring, Object[] values) {
		return executeSQL(qlstring, new String[0], values);
	}

	public Integer executeHQL(String qlstring, String[] params, Object[] values) {
		return executeQl(qlstring, params, values, QlEnum.HQL);
	}

	public Integer executeHQL(String qlstring, String param, Object value) {
		Assert.isTrue(StringUtils.hasText(param), "param不能为空!", true);
		return executeHQL(qlstring, new String[] { param }, new Object[] { value });
	}

	public Integer executeHQL(String qlstring) {
		return executeHQL(qlstring, null);
	}

	public Integer executeHQL(String qlstring, Object[] values) {
		return executeHQL(qlstring, new String[0], values);
	}

	public List getByNameParamSql(String qlstring, String[] params, Object[] values, Class returnType) {
		return getByNameParam(qlstring, params, values, QlEnum.SQL, returnType);
	}

	public List getByPropertySql(String qlstring, Object[] values, Class returnType) {
		return getByProperty(qlstring, values, QlEnum.SQL, returnType);
	}

	public <T> List<T> getByPropertySqlTop(String sql, Object[] values, Integer top, Class<T> clazz) {
		return getByPropertySqlTop(sql, null, values, top, clazz);
	}

	public <T> List<T> getByPropertySqlTop(String sql, String[] params, Object[] values, Integer top, Class<T> clazz) {
		return getByPropertyTop(sql, params, values, QlEnum.SQL, top, clazz);
	}

	public <T> List<T> getByPropertyHqlTop(String hql, Object[] values, Integer top, Class<T> clazz) {
		return getByPropertyHqlTop(hql, null, values, top, clazz);
	}

	public <T> List<T> getByPropertyHqlTop(String hql, String[] params, Object[] values, Integer top, Class<T> clazz) {
		return getByPropertyTop(hql, params, values, QlEnum.HQL, top, clazz);
	}

	public <T> List<T> getByPropertyTop(String sql, String[] params, Object[] values, QlEnum type, Integer top,
			Class<T> clazz) {
		Page page = new ListPage();
		page.setPage(Integer.valueOf(1));
		page.setRows(top);
		page = queryByNameParam(sql, params, values, type, PageEnum.PAGE_TOP, page, clazz);
		return page.getRecord();
	}

	public Integer getTotalCountByNameParamSql(String qlstring, String[] params, Object[] values) {
		return getTotalCountByNameParam(qlstring, params, values, QlEnum.SQL);
	}

	public Page queryByNameParamSql(String qlstring, String[] params, Object[] values, PageEnum needPage,
			Page pageModel, Class returnType) {
		return queryByNameParam(qlstring, params, values, QlEnum.SQL, needPage, pageModel, returnType);
	}

	public Page queryByPropertySql(String qlstring, Object[] values, Class returnType) {
		return queryByPropertySql(qlstring, null, values, returnType);
	}

	public Page queryByPropertySql(String qlstring, Object[] values, Page page, PageEnum pageEnum) {
		return queryByNameParamSql(qlstring, null, values, pageEnum, page, null);
	}

	public Page queryByPropertySql(String qlstring, String[] params, Object[] values, Class returnType) {
		return queryByNameParamSql(qlstring, params, values, PageEnum.PAGE_NEED, new ListPage(), returnType);
	}

	public List getByNameParamSql(String qlstring, String[] params, Object[] values) {
		return getByNameParamSql(qlstring, params, values, null);
	}

	public List getByPropertySql(String qlstring, Object[] values) {
		return getByPropertySql(qlstring, values, null);
	}

	public Page queryByNameParamSql(String qlstring, String[] params, Object[] values) {
		return queryByNameParamSql(qlstring, params, values, PageEnum.PAGE_NEED, new ListPage(), null);
	}

	public Page queryByNameParamSqlMap(String qlstring, String[] params, Object[] values) {
		return queryByNameParamSql(qlstring, params, values, PageEnum.PAGE_NEED, new ListPage(), HashMap.class);
	}

	public Page queryByNameParamSqlMap(String qlstring, String[] params, Object[] values, Page page) {
		return queryByNameParamSql(qlstring, params, values, PageEnum.PAGE_NEED, page, HashMap.class);
	}

	public Page queryByNameParamSql(String qlstring, String[] params, Object[] values, Page page) {
		return queryByNameParamSql(qlstring, params, values, PageEnum.PAGE_NEED, page, null);
	}

	public Page queryByPropertySql(String qlstring, Object[] values, Page page) {
		return queryByNameParamSql(qlstring, null, values, page);
	}

	public List getByNameParamHql(String qlstring, String[] params, Object[] values, Class returnType) {
		return getByNameParam(qlstring, params, values, QlEnum.HQL, returnType);
	}

	public List getByPropertyHql(String qlstring, Object[] values, Class returnType) {
		return getByProperty(qlstring, values, QlEnum.HQL, returnType);
	}

	public List getByPropertyHql(String qlstring, Object values) {
		return getByPropertyHql(qlstring, new Object[] { values });
	}

	public List getByNameParamHql(String qlstring, String[] params, Object[] values) {
		return getByNameParamHql(qlstring, params, values, null);
	}

	public List getByPropertyHql(String qlstring, Object[] values) {
		return getByPropertyHql(qlstring, values, null);
	}

	public <T extends SoftEntityModel> boolean isUnique(T entity, String[] fields) {
		return isUniqueQl(entity, null, fields);
	}

	public <T extends SoftEntityModel> boolean isUniqueQl(T entity, String tableName, String[] fields) {
		StringBuffer qlstring = new StringBuffer();
		QlEnum qltype = null;
		if (StringUtils.hasText(tableName)) {
			qlstring.append("select count(*) from " + tableName + " a where 1=1 ");
			qltype = QlEnum.SQL;
		} else {
			if (entity == null) {
				this.log.error("在执行操作时,发现所传的对象为null");
			}
			qlstring.append("select count(*) from " + entity.getClass().getName() + " a where 1=1 ");
			qltype = QlEnum.HQL;
		}
		List<Object> values = new ArrayList(fields.length);
		String[] arrayOfString;
		int j = (arrayOfString = fields).length;
		for (int i = 0; i < j; i++) {
			String name = arrayOfString[i];
			if (!StringUtils.notText(name)) {
				name = name.trim();
				Object propertyValue = PropertyUtils.getProperty(entity, name);
				qlstring.append(" and a." + name + "=?");
				values.add(propertyValue);
			}
		}
		if (entity.getId() != null) {
			qlstring.append(" and a.id<>?");
			Object propertyValue = entity.getId();
			values.add(propertyValue);
		}
		List list = getByProperty(qlstring.toString(), values.toArray(), qltype, null);
		return NumberUtils.toInteger(list.get(0)).intValue() <= 0;
	}

	public <T> List<T> executeCall(String callQL, String[] values, Class<T> clazz) {
		return super.converList2Model(clazz, executeCall(callQL, values, true));
	}

	public SupcanTable executeCall4SupcanTable(final String callQL, final String[] values, final boolean hasReturn) {
		return (SupcanTable) getSession().doReturningWork(new ReturningWork() {
			public Object execute(Connection conn) throws SQLException {
				CallableStatement cs = null;
				try {
					cs = conn.prepareCall(callQL);
					int vl = values == null ? 0 : values.length;
					if (vl > 0) {
						for (int i = 1; i <= vl; i++) {
							cs.setString(i, values[(i - 1)]);
						}
					}
					if (hasReturn) {
						cs.registerOutParameter(vl + 1, -10);
					}
					cs.execute();
					if (hasReturn) {
						ResultSet rs = (ResultSet) cs.getObject(vl + 1);
						return SupcanTable.init(rs);
					}
					return null;
				} catch (CapecException e) {
					throw e;
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						if (cs != null) {
							cs.close();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				return null;
			}
		});
	}

	public Map<String, Object> executeCallMap(final String callQL, final Object[] values,
			final Integer[] outParameterTypes) {
		return (Map) getSession().doReturningWork(new ReturningWork() {
			public Object execute(Connection conn) throws SQLException {
				CallableStatement cs = null;
				try {
					cs = conn.prepareCall(callQL);
					int vl = values == null ? 0 : values.length;
					if (vl > 0) {
						for (int i = 1; i <= vl; i++) {
							cs.setObject(i, values[(i - 1)]);
						}
					}
					boolean hasReturn = (outParameterTypes != null) && (outParameterTypes.length > 0);
					if (hasReturn) {
						for (int typeIndex = 1; typeIndex <= outParameterTypes.length; typeIndex++) {
							cs.registerOutParameter(vl + typeIndex, outParameterTypes[(typeIndex - 1)].intValue());
						}
					}
					cs.execute();
					if (hasReturn) {
						Map<String, Object> map = new LinkedHashMap();
						for (int opt = 1; opt <= outParameterTypes.length; opt++) {
							map.put("P" + opt, cs.getObject(opt));
						}
						return map;
					}
				} catch (CapecException e) {
					throw e;
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						if (cs != null) {
							cs.close();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				try {
					if (cs != null) {
						cs.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return null;
			}
		});
	}

	public List executeCallList(final String callQL, final Object[] values, final Integer[] outParameterTypes) {
		return (List) getSession().doReturningWork(new ReturningWork() {
			public Object execute(Connection conn) throws SQLException {
				CallableStatement cs = null;
				try {
					cs = conn.prepareCall(callQL);
					int vl = values == null ? 0 : values.length;
					if (vl > 0) {
						for (int i = 1; i <= vl; i++) {
							cs.setObject(i, values[(i - 1)]);
						}
					}
					boolean hasReturn = (outParameterTypes != null) && (outParameterTypes.length > 0);
					if (hasReturn) {
						for (int typeIndex = 1; typeIndex <= outParameterTypes.length; typeIndex++) {
							cs.registerOutParameter(vl + typeIndex, outParameterTypes[(typeIndex - 1)].intValue());
						}
					}
					cs.execute();
					if (hasReturn) {
						List list = new ArrayList();
						if (outParameterTypes.length > 1) {
							list.add(cs.getObject(1));
						} else {
							Object[] o = new Object[outParameterTypes.length];
							for (int opt = 1; opt <= outParameterTypes.length; opt++) {
								o[(opt - 1)] = cs.getObject(opt);
							}
							list.add(o);
						}
						return list;
					}
				} catch (CapecException e) {
					throw e;
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						if (cs != null) {
							cs.close();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				try {
					if (cs != null) {
						cs.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return null;
			}
		});
	}

	public List<Map> executeCall(final String callQL, final String[] values, final boolean hasReturn) {
		return (List) getSession().doReturningWork(new ReturningWork() {
			public Object execute(Connection conn) throws SQLException {
				CallableStatement cs = null;
				try {
					cs = conn.prepareCall(callQL);
					int vl = values == null ? 0 : values.length;
					if (vl > 0) {
						for (int i = 1; i <= vl; i++) {
							cs.setString(i, values[(i - 1)]);
						}
					}
					if (hasReturn) {
						cs.registerOutParameter(vl + 1, -10);
					}
					cs.execute();
					if (hasReturn) {
						ResultSet rs = (ResultSet) cs.getObject(vl + 1);
						ResultSetMetaData rsmd = rs.getMetaData();
						List<Map> list = new ArrayList();
						while (rs.next()) {
							Map map = new LinkedHashMap();
							int colCount = rsmd.getColumnCount();
							for (int j = 1; j <= colCount; j++) {
								String colName = rsmd.getColumnName(j).toUpperCase();
								Object colValue = rs.getObject(colName);
								if (colValue == null) {
									map.put(colName, null);
								} else {
									map.put(colName, colValue);
								}
							}
							list.add(map);
						}
						return list;
					}
					return null;
				} catch (CapecException e) {
					throw e;
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						if (cs != null) {
							cs.close();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				return null;
			}
		});
	}

	public List getAll(Class clazz, String[] fields) {
		String hql = " select a." + HqlUtils.arrayToString(fields, ",") + " from " + clazz.getName() + " a ";
		return getByPropertyHql(hql, null, clazz);
	}

	public List getAll(Class clazz) {
		return getByPropertyHql(" from " + clazz.getName() + " a ", null);
	}
}
