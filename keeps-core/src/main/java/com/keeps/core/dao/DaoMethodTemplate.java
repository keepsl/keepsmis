package com.keeps.core.dao;

import com.keeps.tools.exception.CapecException;

public abstract interface DaoMethodTemplate {
	public abstract Object rollbockDaoMethod() throws CapecException;
}
