/*
package com.se7en.dao;

import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.se7en.util.StringUtil;


*/
/**
 * spring jdbc 实现类
 * 
 * @author 段磊
 *//*

@SuppressWarnings("deprecation")
@Repository("jdbcDao")
public class JdbcDao extends BaseJdbcDao implements IJdbcDao {

	*/
/**
	 * 批量处理
	 *//*

	public int[] batchExecute(String sql, List<Object[]> params)
			throws Exception {

		return simpleJdbcTemplate.batchUpdate(sql, params);
	}

	*/
/**
	 * 批量处理
	 *//*

	public int[] batchExecute(String sql, Map<String, ?>[] params)
			throws Exception {

		return simpleJdbcTemplate.batchUpdate(sql, params);
	}

	*/
/**
	 * 批量处理
	 *//*

	public int[] batchExecute(String sql, Object[] params) throws Exception {
		int length = params.length;
		BeanPropertySqlParameterSource[] sources = new BeanPropertySqlParameterSource[length];
		for (int index = 0; index < length; index++) {
			sources[index] = new BeanPropertySqlParameterSource(params[index]);
		}
		return simpleJdbcTemplate.batchUpdate(sql, sources);
	}

	*/
/**
	 * sql执行增删改
	 *//*

	public int execute(String sql, Map<String, ?> map) throws Exception {

		return simpleJdbcTemplate.update(sql, map);
	}

	*/
/**
	 * sql增删改
	 *//*

	public int execute(String sql, Object bean) throws Exception {
		BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(
				bean);
		return simpleJdbcTemplate.update(sql, source);
	}

	*/
/**
	 * 获取记录数量
	 *//*

	public long totalSql(String sql, Map<String, ?> map) throws Exception {

		return simpleJdbcTemplate.queryForLong(sql, map);
	}

	*/
/**
	 * 获取记录数量
	 *//*

	public long totalSql(String sql, Object[] params) {

		return simpleJdbcTemplate.queryForLong(sql, params);
	}

	*/
/**
	 * 查询记录存储到List<Bean>中
	 *//*

	public <T> List<T> querySql2Bean(Class<T> type, String sql,
			Map<String, ?> map) throws Exception {

		return simpleJdbcTemplate.query(sql,
				BeanPropertyRowMapper.newInstance(type), map);
	}

	*/
/**
	 * 查询记录存储到List<Bean>中
	 *//*

	public <T> List<T> querySql2Bean(Class<T> type, String sql, Object[] params)
			throws Exception {

		return simpleJdbcTemplate.query(sql,
				BeanPropertyRowMapper.newInstance(type), params);
	}

	*/
/**
	 * 查询记录存储到List<Bean>中
	 *//*

	public <T> List<T> querySql2Bean(Class<T> type, String sql, Object bean)
			throws Exception {
		BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(
				bean);

		return simpleJdbcTemplate.query(sql,
				BeanPropertyRowMapper.newInstance(type), source);
	}

	*/
/**
	 * 查询记录存储到List<Map<String,Object>>中
	 *//*

	public List<Map<String, Object>> querySql2Map(String sql, Map<String, ?> map)
			throws Exception {

		return simpleJdbcTemplate.queryForList(sql, map);
	}

	*/
/**
	 * 查询记录存储到List<Map<String,Object>>中,无需条件参数时params传入null
	 *//*

	public List<Map<String, Object>> querySql2Map(String sql, Object[] params) {

		return simpleJdbcTemplate.queryForList(sql, params);
	}

	*/
/**
	 * 查询记录存储到List<Map<String,Object>>中
	 *//*

	public List<Map<String, Object>> querySql2Map(String sql, Object bean) {

		BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(
				bean);
		return simpleJdbcTemplate.queryForList(sql, source);
	}

	*/
/**
	 * 查询单条记录,返回到Map中
	 *//*

	public Map<String, Object> getMap(String sql, Map<String, ?> map)
			throws Exception {

		return simpleJdbcTemplate.queryForMap(sql, map);
	}

	*/
/**
	 * 查询单条记录,返回到Map中
	 *//*

	public Map<String, Object> getMap(String sql, Object[] params)
			throws Exception {

		return simpleJdbcTemplate.queryForMap(sql, params);
	}

	*/
/**
	 * 查询单条记录,返回到Map中
	 *//*

	public Map<String, Object> getMap(String sql, Object bean)
			throws Exception {
		BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(
				bean);

		return simpleJdbcTemplate.queryForMap(sql, source);
	}

	*/
/**
	 * 查询单条记录,返回到Bean中
	 *//*

	public <T> T get(Class<T> type, String sql, Map<String, ?> map)
			throws Exception {

		return simpleJdbcTemplate.queryForObject(sql,
				ParameterizedBeanPropertyRowMapper.newInstance(type), map);
	}

	*/
/**
	 * 查询单条记录,返回到Bean中
	 *//*

	public <T> T get(Class<T> type, String sql, Object[] params)
			throws Exception {

		return simpleJdbcTemplate.queryForObject(sql,
				ParameterizedBeanPropertyRowMapper.newInstance(type), params);
	}

	*/
/**
	 * 查询单条记录,返回到Bean中
	 *//*

	public <T> T get(Class<T> type, String sql, Object bean)
			throws Exception {
		BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(
				bean);
		return simpleJdbcTemplate.queryForObject(sql,
				ParameterizedBeanPropertyRowMapper.newInstance(type), source);
	}

	*/
/**
	 * 插入单条记录
	 *//*

	public int insert(String tableName, Map<String, Object> params)
			throws Exception {
		checkTableName(tableName);

		return simpleJdbcInsert.execute(params);
	}

	*/
/**
	 * 插入单条记录
	 *//*

	public int insert(String tableName, Object bean) throws Exception {
		checkTableName(tableName);
		BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(
				bean);

		return simpleJdbcInsert.execute(source);
	}

	*/
/**
	 * simpleJdbcInsert 表名预检查
	 * 
	 * @param tableName
	 *//*

	private void checkTableName(String tableName) {
		if (StringUtil.isEmpty(simpleJdbcInsert.getTableName())) {
			simpleJdbcInsert.setTableName(tableName);
		} else if (!simpleJdbcInsert.getTableName().equals(tableName)) {
			this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
			simpleJdbcInsert.setTableName(tableName);
		}
	}

	*/
/**
	 * 批量插入
	 * 
	 * @param tableName
	 * @param params
	 * @return
	 *//*

	public int[] batchInsert(String tableName, Map<String, Object>[] params)
			throws Exception {
		checkTableName(tableName);
		return simpleJdbcInsert.executeBatch(params);
	}

	*/
/**
	 * 批量插入
	 * 
	 * @param tableName
	 * @param beans
	 * @return
	 *//*

	public int[] batchInsert(String tableName, Object[] beans) throws Exception {
		int length = beans.length;
		BeanPropertySqlParameterSource[] sources = new BeanPropertySqlParameterSource[length];
		for (int index = 0; index < length; index++) {
			sources[index] = new BeanPropertySqlParameterSource(beans[index]);
		}
		checkTableName(tableName);
		return simpleJdbcInsert.executeBatch(sources);
	}

	@Override
	public List<Object[]> querySql2Array(String sql, Object[] params) {
		return jdbcTemplate.query(sql, params, new RowMapper<Object[]>(){

			@Override
			public Object[] mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				List<Object> list = new LinkedList<Object>();
				
				ResultSetMetaData rmd = rs.getMetaData();
				int colSize = rmd.getColumnCount();
				for(int index = 1;index <= colSize;index++){
					list.add(rs.getObject(index));
				}
				
				return list.toArray();
			}
			
		});
	}

	@Override
	public List<Object> querySql2Single(String sql, Object[] params) {
		return jdbcTemplate.query(sql, params, new RowMapper<Object>(){

			@Override
			public Object mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				
				return rs.getObject(1);
			}
			
		});
	}

	@Override
	public int execute(String sql, Object[] params) {
		return jdbcTemplate.update(sql, params);
	}

}
*/
