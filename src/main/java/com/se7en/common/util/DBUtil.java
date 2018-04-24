package com.se7en.common.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.se7en.common.util.entity.Field;

/**
 * 数据库工具类
 * 
 * @author dl
 */
public final class DBUtil {
	//集合类型
	public final static int COLTYPE_ARRAYLIST = 1;
	public final static int COLTYPE_HASHMAP = 2;

	// 数据库类型
	public final static String DBTYPE_MYSQL = "mysql";
	public final static String DBTYPE_ORACLE = "oracle";
	public final static String DBTYPE_SQLSERVER = "sqlserver";
	public final static String DBTYPE_DB2 = "db2";
	
	/**
	 * se7en配置项
	 */
	private final static String DRIVER_CLASS_SE7EN = "com.mysql.jdbc.Driver";
	private final static String JDBC_URL_SE7EN = "jdbc:mysql://localhost:3306/se7en";
	private final static String USERNAME_SE7EN = "dl";
	private final static String PASSWORD_SE7EN = "dl";
	
	/** The Constant RT_LIST_ARRAY. 
	 * 返回List<Object[]>类型结果集
	 * */
	public final static int RT_LIST_ARRAY = 1;
	
	/** The Constant RT_LIST_MAP. 
	 * 返回List<Map>类型结果集
	 * */
	public final static int RT_LIST_MAP = 2;

	/**
	 * mysql 列出所有表名
	 */
	private final static String MYSQL_TABLE_LIST = "select table_name from "
			+ " information_schema.tables where 1 = 1 ";

	/**
	 * mysql 根据表名查表注释
	 */
	private final static String MYSQL_TABLE_COMMENT = "select table_comment from "
			+ " information_schema.tables where table_name = ? ";

	/**
	 * oracle 根据表名查表注释
	 */
	private final static String ORACLE_TABLE_COMMENT = "select tab.comments from "
			+ " user_tab_cols where table_name = ? ";

	/**
	 * mysql 列出所有字段
	 */
	private final static String MYSQL_FIELD_LIST = " select "
			+ "c.table_name as tableName,"
			+ "c.column_name as fieldName,"
			+ " c.data_type as fieldType,"
			+ "c.character_maximum_length as fieldLength,"
			+ "c.numeric_precision as fieldPrecision,"
			+ "c.numeric_scale as fieldScale,"
			+ "c.is_nullable as nullable,"
			+ "c.ordinal_position as fieldId,"
			+ "c.column_comment as fieldComment,"
			+ "t.table_comment as tableComment"
			+ " from information_schema.columns c left join "
			+ " information_schema.tables t on c.table_name = t.table_name "
			+ " where c.table_name = ?";

	/**
	 * oracle 列出所有字段
	 */
	private final static String ORACLE_FIELD_LIST = "select "
			+ "user_tab_cols.table_name as tableName,"
			+ "user_tab_comments.comments as tableComment,"
			+ "user_tab_cols.column_name as fieldName ,"
			+ "user_tab_cols.data_type as fieldType,"
			+ "user_tab_cols.data_length as fieldLength,"
			+ "user_tab_cols.data_precision as fieldPrecision,"
			+ "user_tab_cols.data_scale as fieldScale,"
			+ "user_tab_cols.nullable as nullable,"
			+ "user_tab_cols.column_id as fieldId,"
			+ "user_col_comments.comments as fieldComment"
			+ " from user_tab_cols "
			+ " inner join user_col_comments on "
			+ " user_col_comments.table_name=user_tab_cols.table_name "
			+ " and user_col_comments.column_name=user_tab_cols.column_name "
			+ " inner join user_tab_comments "
			+ " on user_tab_comments.table_name = user_tab_cols.table_name "
			+ " where  user_tab_cols.table_name= ? ";
	
	private DBUtil() {
		throw new UnsupportedOperationException("工具类不能被实例化");
	}
	
	/**
	 * Gets the se7en connection.
	 * 获取se7en Connection
	 * @return the se7en connection
	 */
	public static Connection getSe7enConnection(){
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(DBUtil.DRIVER_CLASS_SE7EN);
		dataSource.setUrl(DBUtil.JDBC_URL_SE7EN);
		dataSource.setUsername(DBUtil.USERNAME_SE7EN);
		dataSource.setPassword(DBUtil.PASSWORD_SE7EN);
		return DataSourceUtils.getConnection(dataSource);
	}

	/**
	 * 获取数据库链接
	 * 
	 * @param driveClass
	 * @param url
	 * @param name
	 * @param password
	 * @return
	 */
	public static Connection getConnection(final String driveClass,final String url,
			final String name,final String password) {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driveClass);
		dataSource.setUrl(url);
		dataSource.setUsername(name);
		dataSource.setPassword(password);
		return DataSourceUtils.getConnection(dataSource);
	}

	/**
	 * 列举mysql数据库表名
	 * 
	 * @param con
	 * @param jdbc
	 * @return
	 * @throws SQLException
	 */
	public static List<String> listMysqlTables(final Connection con,final String dbtype)
			throws SQLException {
		final List<String> tables = new ArrayList<String>();
		final StringBuilder tableSql = new StringBuilder(MYSQL_TABLE_LIST);
		final List<Object> params = new ArrayList<Object>();
		if (StringUtil.isNotEmpty(dbtype)) {
			tableSql.append(" and table_schema = ? ");
			params.add(dbtype);
		}
		final PreparedStatement stmt = con.prepareStatement(tableSql.toString());
		int size = params.size();
		for (int index = 0; index < size; index++) {
			final Object param = params.get(index);
			stmt.setObject(index + 1, param);
		}
		final ResultSet result = stmt.executeQuery();
		while (result.next()) {
			final String tableName = result.getString(1);
			tables.add(tableName);
		}
		result.close();
		stmt.close();
		return tables;
	}

	/**
	 * 列举mysql字段信息
	 * @param con
	 * @param table
	 * @return
	 * @throws SQLException
	 */
	public static List<Field> listMysqlFields(final Connection con,final String table)
			throws SQLException {

		return listFields(con, table, "mysql");
	}

	/**
	 * 列举字段信息
	 * @param con
	 * @param table
	 * @param dbtype
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public static List<Field> listFields(final Connection con,final String table,
			final String dbtype) throws SQLException {

		return (List<Field>) enmuFields(con, table, dbtype, 1);
	}

	/**
	 * 映射字段信息
	 * @param con
	 * @param table
	 * @param dbtype
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Field> mapFields(final Connection con,final String table,
			final String dbtype) throws SQLException {

		return (Map<String, Field>) enmuFields(con, table, dbtype, 2);
	}

	/**
	 * Enmu fields.
	 * 枚举字段
	 * @param con the con
	 * @param table the table
	 * @param dbtype the dbtype
	 * @param resultType the result type
	 * @return the object
	 * @throws SQLException the sQL exception
	 */
	private static Object enmuFields(final Connection con,final String table,
			final String dbtype,final int resultType) throws SQLException {
		if (StringUtil.isEmpty(table)) {
			return null;
		}
		final String sql = getEnmuFieldsSql(dbtype);
		final PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, table);
		final ResultSet result = stmt.executeQuery();
		final Object ret = iteFieldResult(result, resultType);
		result.close();
		stmt.close();
		return ret;
	}

	private static String getEnmuFieldsSql(final String dbtype) {
		String ret = StringUtil.EMPTY;
		if (dbtype.equals(DBUtil.DBTYPE_MYSQL)) {
			ret = MYSQL_FIELD_LIST;
		} else if (dbtype.equals(DBUtil.DBTYPE_ORACLE)) {
			ret = StringUtil.EMPTY;
		} 
		
		return ret;
	}

	/**
	 * 注入参数(只适用于基本类型)
	 * 
	 * @param stmt
	 * @param params
	 * @throws SQLException
	 */
	public static void setParameter(final PreparedStatement stmt,final List<Object> params)
			throws SQLException {
		final int size = params.size();
		for (int index = 0; index < size; index++) {
			final Object param = params.get(index);
			stmt.setObject(index + 1, param);
		}
	}
	
	/**
	 * Sets the parameter.
	 * 注入参数
	 * @param stmt the stmt
	 * @param params the params
	 * @throws SQLException the sQL exception
	 */
	public static void setParameter(final PreparedStatement stmt,final Object[] params)
			throws SQLException {
		if(params == null || params.length == 0){
			return;
		}
		final int size = params.length;
		for (int index = 0; index < size; index++) {
			final Object param = params[index];
			stmt.setObject(index + 1, param);
		}
	}

	/**
	 * 迭代字段结果集
	 * 
	 * @param result
	 * @param type
	 *            返回结果类型(1.list 2.hashmap)
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	private static Object iteFieldResult(final ResultSet result,final int type)
			throws SQLException {
		Object fields = null;
		if (type == COLTYPE_ARRAYLIST) {
			fields = new ArrayList<Field>();
		} else if (type == COLTYPE_HASHMAP) {
			fields = new HashMap<String, Field>();
		}
		if(fields == null){
			return null;
		}
		while (result.next()) {
			final Field fld = new Field();
			final String tableName = result.getString("tableName");
			final String tableComment = result.getString("tableComment");
			final String fieldName = result.getString("fieldName");
			final String fieldType = result.getString("fieldType");
			final int fieldLength = result.getInt("fieldLength");
			final int fieldPrecision = result.getInt("fieldPrecision");
			final float fieldScale = result.getFloat("fieldScale");
			final boolean nullable = result.getBoolean("nullable");
			final String fieldId = result.getString("fieldId");
			final String fieldComment = result.getString("fieldComment");
			// bean
			fld.setTableName(tableName);
			fld.setTableComment(tableComment);
			fld.setColumnName(fieldName);
			fld.setColumnType(fieldType);
			fld.setColumnLength(fieldLength);
			fld.setColumnPrecision(fieldPrecision);
			fld.setColumnScale(fieldScale);
			fld.setNullable(nullable);
			fld.setColumnId(fieldId);
			fld.setColumnComment(fieldComment);
			if (type == COLTYPE_ARRAYLIST) {
				((List<Field>) fields).add(fld);
			} else if (type == COLTYPE_HASHMAP) {
				((Map<String,Field>) fields).put(fieldName, fld);
			}
		}
		return fields;
	}

	/**
	 * 获取表名描述
	 * 
	 * @param con
	 * @param tableName
	 * @param dbtype
	 * @return
	 * @throws SQLException
	 */
	public static String getTableComment(final Connection con,final String tableName,
			final String dbtype) throws SQLException {
		String sql = "";
		if (dbtype.equals(DBUtil.DBTYPE_MYSQL)) {
			sql = MYSQL_TABLE_COMMENT;
		} else if (dbtype.equals(DBUtil.DBTYPE_ORACLE)) {
			sql = ORACLE_TABLE_COMMENT;
		}
		String ret = StringUtil.EMPTY;
		final PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, tableName);
		ResultSet result = stmt.executeQuery();
		ret = result.getString(1);
		result.close();
		stmt.close();
		
		return ret;
	}
	
	
	/**
	 * Execute query.
	 * 执行查询返回结果集
	 * @param con the con
	 * @param sql the sql
	 * @param params the params
	 * @return the list
	 */
	public static List<?> executeQuery(final Connection con,
			final String sql,final Object[] params){
		List<Object[]> result = Collections.emptyList();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(sql);
			setParameter(ps, params);
			rs = ps.executeQuery();
			result = iteQueryResult(rs, DBUtil.RT_LIST_ARRAY);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null){
					rs.close();
				}
				if(ps != null){
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * Execute.
	 * 执行sql语句
	 * @param con the con
	 * @param sql the sql
	 * @param params the params
	 * @return the int
	 */
	public static int execute(final Connection con,final String sql,final Object[] params){
		int result = 0;
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(sql);
			setParameter(ps, params);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(ps != null){
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * Execute batch.
	 * 批量执行
	 * @param con the con
	 * @param sql the sql
	 * @param params the params
	 * @return the int[]
	 */
	public static int[] executeBatch(final Connection con,
			final String sql,final List<Object[]> params){
		int[] result = ArrayUtils.EMPTY_INT_ARRAY;
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(sql);
			for(Object[] param : params){
				setParameter(ps, param);
				ps.addBatch();
			}
			result = ps.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(ps != null){
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * Ite query result.
	 * 遍历结果集,返回指定格式结果集
	 * @param rs the rs
	 * @param type the type
	 * @return the list
	 * @throws SQLException the sQL exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> List<T> iteQueryResult(final ResultSet rs,final int type)
			throws SQLException {
		final List result = new ArrayList();
		final ResultSetMetaData rsmd = rs.getMetaData();
		int colNums = rsmd.getColumnCount();
		int index = 1;
		//列名缓存
		List<String> colNames = Collections.emptyList();
		if(type == DBUtil.RT_LIST_MAP){
			colNames = new ArrayList<String>();
			for(index = 1;index <= colNums;index++){
				String colName = rsmd.getColumnName(index);
				colNames.add(colName);
			}
		}
		while (rs.next()) {
			QueryResultHandler handler = QueryResultHandleFactory.create(type, colNums);
			handler.execute(rs, result, colNums, colNames);
		}
		return result;
	}

	/**
	 * Close connection.
	 * 关闭Connection
	 * @param con the con
	 */
	public static void closeConnection(final Connection con){
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
