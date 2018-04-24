package com.se7en.service;

import java.util.Collection;
import java.util.Date;

import com.se7en.model.BaseObject;

public abstract class BaseObjectService<T extends BaseObject> extends AbstractEjbService<T> {
	
	@Override
	public boolean save(T bean) {
		injectInfomation(bean);
		return super.save(bean);
	}

	private void injectInfomation(T bean) {
		Date dt = new Date();
		bean.setCreateTime(dt);
		bean.setUpdateTime(dt);
		bean.setState(1);
	}
	
	private void injectInfomation(T bean,Date dt) {
		bean.setCreateTime(dt);
		bean.setUpdateTime(dt);
		bean.setState(1);
	}
	
	@Override
	public boolean saveOrUpdate(T bean) {
		if(bean.getId() == null){
			injectInfomation(bean);
		}else{
			Date dt = new Date();
			bean.setUpdateTime(dt);
		}
		return super.saveOrUpdate(bean);
	}
	
	@Override
	public boolean batchSaveOrUpdate(Collection<T> collection) {
		Date dt = new Date();
		for (T t : collection) {
			if(t.getId() == null){
				injectInfomation(t, dt);
			}else{
				t.setUpdateTime(dt);
			}
		}
		return super.batchSaveOrUpdate(collection);
	}
	
	@Override
	public boolean batchSave(Collection<T> collection) {
		Date dt = new Date();
		for (T t : collection) {
			injectInfomation(t, dt);
		}
		return super.batchSave(collection);
	}
	
	@Override
	public boolean batchUpdate(Collection<T> collection) {
		Date dt = new Date();
		for (T t : collection) {
			t.setUpdateTime(dt);
		}
		return super.batchUpdate(collection);
	}
}
