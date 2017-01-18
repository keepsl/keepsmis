package com.keeps.core.model.listener;

import java.io.Serializable;

import com.keeps.tools.exception.CapecException;

public interface RemoveRollback {
	
	public abstract void doRemove(Serializable paramSerializable) throws CapecException;
	
}
