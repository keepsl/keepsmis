package com.keeps.core.model.listener;

import com.keeps.tools.exception.CapecException;
import com.keeps.core.service.SoftService;

public abstract interface RemoveListener extends ModelListener{
	public abstract void onRemove(SoftService paramSoftService) throws CapecException;
}
