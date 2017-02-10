package com.keeps.core.model.listener;

import com.keeps.tools.exception.CapecException;
import com.keeps.core.service.SoftService;

public abstract interface SaveOrUpdateListener extends ModelListener {
	public abstract void onSaveOrUpdate(SoftService paramSoftService) throws CapecException;
}
