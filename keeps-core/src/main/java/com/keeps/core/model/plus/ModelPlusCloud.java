package com.keeps.core.model.plus;

import java.util.Date;

public interface ModelPlusCloud {
	public abstract String getCreator();

	public abstract void setCreator(String paramString);

	public abstract String getSiteid();

	public abstract void setSiteid(String paramString);

	public abstract Date getModifyDate();

	public abstract void setModifyDate(Date paramDate);

	public abstract Date getCreatedate();

	public abstract void setCreatedate(Date paramDate);

	public abstract String getEndpoint();

	public abstract void setEndpoint(String paramString);

	public abstract String getMaps();

	public abstract void setMaps(String paramString);
}
