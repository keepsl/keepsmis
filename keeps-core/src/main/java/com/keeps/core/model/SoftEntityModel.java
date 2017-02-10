package com.keeps.core.model;

public interface SoftEntityModel<T> extends SoftModel {
	public void setId(T paramT);

	public T getId();
}
