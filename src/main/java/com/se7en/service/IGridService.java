package com.se7en.service;

import com.se7en.common.ColInfo;

import java.util.Collection;
import java.util.List;

/**
 * The Interface IGridService.
 * 表格封装组件
 */
public interface IGridService {

	/**
	 * Creates the thead.
	 * 生成表头列信息
	 * @param fldNames the fld names
	 * @return the list
	 */
	List<List<ColInfo>> createModelThead(Class<?> cls, Collection<String> fldNames) throws Exception;
	
	/**
	 * Creates the thead.
	 * 生成表头信息
	 * @param fldNames the fld names
	 * @return the list
	 */
	List<List<ColInfo>> createModelThead(Class<?> cls,String... fldNames) throws Exception;
	
	List<List<ColInfo>> createSimpleThead(Collection<String> titles) throws Exception ;
	
	List<List<ColInfo>> createSimpleThead(String... titles) throws Exception ;
	
	/**
	 * Creates the thead. 
	 * 全属性
	 * @param cls the cls
	 * @return the list
	 * @throws Exception the exception
	 */
	List<List<ColInfo>> createReflectThead(Class<?> cls) throws Exception;
	
	/**
	 * Creates the model thead.
	 * 创建模型表头
	 * @param cls the cls
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<List<ColInfo>> createModelThead(Class<?> cls) throws Exception;
}
