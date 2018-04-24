package com.se7en.common.util.runtime;

import org.hibernate.SessionFactory;
import org.hibernate.persister.entity.AbstractEntityPersister;

import com.se7en.common.util.ContextUtil;

public final class HibernateUtil {
	
	private HibernateUtil() {
		throw new UnsupportedOperationException("工具类不能被实例化");
	}
	
	public static SessionFactory getSessionFactory(){
		return ContextUtil.getBean("sessionFactory");
	}
	
	public static String getTableName(final Class<?> cls){
		final SessionFactory sf = getSessionFactory();
		final AbstractEntityPersister cm = (AbstractEntityPersister)sf.getClassMetadata(cls);
		return cm.getTableName();
	}
	
	public static String getColumnName(Class<?> cls,String fldName){
		SessionFactory sf = getSessionFactory();
		AbstractEntityPersister cm = (AbstractEntityPersister)sf.getClassMetadata(cls);
		String[] names = cm.getPropertyColumnNames(fldName);
		if(names.length > 0){
			return names[0];
		}
		return null;
	}
	
}
