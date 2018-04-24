package com.se7en.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public interface IJdbcDao extends ICommonDao{

	/**
	 * 	query and return the recordset into the result(List<Map<String,Object>>),
	 * the sql parameter of the pass into is in the form of Map<String,?>
	 * @param sql
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> querySql2Map(String sql,Map<String,?> map) throws Exception;		
	
	/**
	 * 	query and return the recordset into the result(List<Map<String,Object>>),
	 * the sql parameter of the pass into is in the form of JavaBean,
	 * @param sql
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> querySql2Map(String sql,Object bean) throws Exception;
	
	/**
	 * 	query and return the recordset into the result(List<Bean>)
	 * the sql parameter of the pass into is in the form of Map<String,?>
	 * @param <T>
	 * @param type
	 * @param sql
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public <T> List<T> querySql2Bean(Class<T> type,String sql,Map<String,?> map) throws Exception;		
	
	/**
	 * 	query and return the recordset into the result(List<Bean>)
	 * the sql parameter of the pass into is in the form of Object[]
	 * @param <T>
	 * @param type
	 * @param sql
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public <T> List<T> querySql2Bean(Class<T> type,String sql,Object[] params) throws Exception; 
	
	/**
	 * 	query and return the recordset into the result(List<Bean>)
	 * the sql parameter of the pass into is in the form of JavaBean
	 * @param <T>
	 * @param type
	 * @param sql
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public <T> List<T> querySql2Bean(Class<T> type,String sql,Object bean) throws Exception; 
	
	/**
	 * 	query single record and return the recordset into the result(Map<String,Object>)
	 * the sql parameter of the pass into is in the form of Map<String,?>
	 * @param sql
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getMap(String sql,Map<String,?> map) throws Exception;
	
	/**
	 * 	query single record and return the recordset into the result(Map<String,Object>)
	 * the sql parameter of the pass into is in the form of Object[]
	 * @param sql
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getMap(String sql,Object[] params) throws Exception;	
	
	/**
	 * 	query single record and return the recordset into the result(Map<String,Object>)
	 * the sql parameter of the pass into is in the form of JavaBean
	 * @param sql
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getMap(String sql,Object bean) throws Exception;	
	
	/**
	 * 	query single record and return the recordset into the result(JavaBean)
	 * the sql parameter of the pass into is in the form of Map<String,?>
	 * @param <T>
	 * @param type
	 * @param sql
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public <T> T get(Class<T> type,String sql,Map<String,?> map) throws Exception;
	
	/**
	 * 	query single record and return the recordset into the result(JavaBean)
	 * the sql parameter of the pass into is in the form of Object[]
	 * @param <T>
	 * @param type
	 * @param sql
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public <T> T get(Class<T> type,String sql,Object[] params) throws Exception;
	
	/**
	 * 	query single record and return the recordset into the result(JavaBean)
	 * the sql parameter of the pass into is in the form of JavaBean
	 * @param <T>
	 * @param type
	 * @param sql
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public <T> T get(Class<T> type,String sql,Object bean) throws Exception;	
	
	/**
	 * 	query the count of record and return the recordset into the result type of long
	 * the sql parameter of the pass into is in the form of Map<String,?>
	 * @param sql
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public long totalSql(String sql,Map<String,?> map) throws Exception;	
	
	/**
	 * 	execute the method of insert,delete and update data and return the count of modify
	 * record
	 * the sql parameter of the pass into is in the form of Map<String,?>
	 * @param sql
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int execute(String sql,Map<String,?> map) throws Exception;
	
	/**
	 * 	execute the method of insert,delete and update data and return the count of modify
	 * record
	 * the sql parameter of the pass into is in the form of JavaBean
	 * @param sql
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int execute(String sql,Object bean) throws Exception;		
	
	/**
	 * batch handle the method of insert,delete and update data and return the count of modify
	 * record
	 * the sql parameter of the pass into is in the form of List<Object[]>
	 * @param sql
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int[] batchExecute(String sql,List<Object[]> params) throws Exception;	
	
	/**
	 * batch handle the method of insert,delete and update data and return the count of modify
	 * record
	 * the sql parameter of the pass into is in the form of Map<String,?>[]
	 * @param sql
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int[] batchExecute(String sql,Map<String,?>[] params) throws Exception;	
	
	/**
	 * batch handle the method of insert,delete and update data and return the count of modify
	 * record
	 * the sql parameter of the pass into is in the form of Object[Bean]
	 * @param sql
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int[] batchExecute(String sql,Object[] params) throws Exception;
	
	/**
	 * insert into table with tablename and the sql parameter in the form of Map<String,Object>
	 * @param tableName
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int insert(String tableName,Map<String,Object> params) throws Exception;
	
	/**
	 * insert into table with tablename and the sql parameter in the form of JavaBean
	 * @param tableName
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int insert(String tableName,Object bean) throws Exception;
	
	/**
	 * baatch insert
	 * @param tableName
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int[] batchInsert(String tableName,Map<String,Object>[] params) throws Exception;
	
	/**
	 * batch insert
	 * @param tableName
	 * @param beans
	 * @return
	 * @throws Exception
	 */
	public int[] batchInsert(String tableName,Object[] beans) throws Exception;
	
}
