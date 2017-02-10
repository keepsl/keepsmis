package com.keeps.core.model;

public interface SoftModel {
	public void empty();

	public boolean isSave();

	public String getIds();

	public void setIds(String paramString);

	public String toJson();

	public <T extends SoftEntityModel> T clone(String[] paramArrayOfString);

	public void setGet(Boolean paramBoolean);

	public Boolean isGet();

	public void idhandle();
}
