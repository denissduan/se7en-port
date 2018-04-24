package com.se7en.service.md;

import java.lang.Class;
import java.text.ParseException;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.se7en.common.util.PropertyUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.eclipse.uml2.uml.*;
import org.eclipse.uml2.uml.internal.impl.ClassImpl;
import org.eclipse.uml2.uml.internal.impl.EnumerationImpl;
import org.eclipse.uml2.uml.internal.impl.PrimitiveTypeImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.se7en.common.Format;
import com.se7en.service.sys.SysParamService;
import com.se7en.common.util.StringUtil;

@Service
public class ModelSqlService {

	private static Logger log = LoggerFactory.getLogger(ModelSqlService.class);

	public static final String OPERATION_LIST = PropertyUtil.getProperty("model.list");
	public static final String OPERATION_SEARCH = PropertyUtil.getProperty("model.search");

	@Resource
	private ModelEngine modelEngine;

	@Resource
	private SysParamService paramSrv;

	/**
	 * The join table names. 表链接表名缓存
	 * */
	final private Set<String> joinTableNames = new HashSet<String>(10);

	/**
	 * The sel fields. 查询字段
	 * */
	final private Set<String> selectFields = new HashSet<String>(20);

	/**
	 * Gets the sql by query. 生成查询sql
	 * 
	 * @param cls
	 *            the cls
	 * @return the sql by query
	 */
	public SqlParamBean createQuerySql(Class<?> cls, HttpServletRequest request) {
		ClassImpl ci = modelEngine.getClass(cls);

		List<Parameter> queryParams = getQueryParams(ci);
		List<Parameter> filterParams = getFilterParams(ci);

		return createQuerySql(ci, request, queryParams, filterParams);
	}

	public SqlParamBean createQuerySql(ClassImpl ci, HttpServletRequest request, List<Parameter> queryParams, List<Parameter> filterParams) {
		joinTableNames.clear();
		selectFields.clear();
		SqlParamBean ret = new SqlParamBean();

		String clsName = ci.getName();
		String tableName = TemplateStringUtil.getTableNameFromClassName(clsName);
		String nickName = TemplateStringUtil.getNickNameFromClassName(clsName);

		StringBuilder sql = new StringBuilder();

		StringBuilder querySql = new StringBuilder(" select ");
		StringBuilder tableSql = new StringBuilder();
		StringBuilder whereSql = new StringBuilder(" where 1 = 1 ");
		List<Object> params = new ArrayList<Object>();

		ret.setSql(sql);
		ret.setParams(params);

		querySql.append(nickName + ".id "); // 预置主键
		tableSql.append(" from " + tableName + " " + nickName + " ");
		// 查询字段和表连接sql
		if (CollectionUtils.isEmpty(queryParams))
			return ret;
		int size = queryParams.size();
		for (int index = 0; index < size; index++) {
			Parameter parameter = queryParams.get(index);
			String propName = parameter.getName();
			if(index == 0){		//判断主键是否存在
				if("id".equals(propName)){
					continue;
				}
			}
			SqlSelectJoinBean bean = new SqlSelectJoinBean(ci, propName,
					querySql, tableSql);
			mergeSelectAndJoinSql(bean);
		}
		sql.append(querySql).append(tableSql);
		// 过滤条件sql
		if (CollectionUtils.isEmpty(filterParams)) {
			rebuildSql(sql, querySql, tableSql, whereSql);
			return ret;
		}
		int filterSize = filterParams.size();
		for (int index = 0; index < filterSize; index++) {
			Parameter parameter = filterParams.get(index);
			String propName = parameter.getName();
			mergeJoinAndFilterSql(new SqlJoinFilterBean(ci, propName, request,
					tableSql, whereSql, params));
		}

		rebuildSql(sql, querySql, tableSql, whereSql);
		return ret;
	}

	public void rebuildSql(StringBuilder sql, StringBuilder selectSql,
			StringBuilder tableSql, StringBuilder whereSql) {
		sql.delete(0, sql.length());
		sql.append(selectSql);
		sql.append(tableSql);
		sql.append(whereSql);
	}

	/**
	 * Gets the operation. 获取操作集
	 * 
	 * @param ci
	 *            the ci
	 * @return the operation
	 */
	public Operation getOperation(ClassImpl ci, String optName) {
		Operation ret = null;
		/*List<SysParam> params = paramSrv.getParamByPName(
				ModelEngine.moduleName, optName);
		for (SysParam sysParam : params) {
			ret = ci.getOperation(sysParam.getPvalue(), null, null);
			if (ret != null)
				break;
		}*/
		ret = ci.getOperation(optName, null, null);

		return ret;
	}

	/**
	 * Merge select and join sql. 查询字段和表连接sql
	 * 
	 */
	public void mergeSelectAndJoinSql(SqlSelectJoinBean bean) {
		ClassImpl cls = bean.getCls();
		String propName = bean.getPropName();
		StringBuilder selectSql = bean.getSelectSql();
		StringBuilder tableSql = bean.getTableSql();

		String clsName = cls.getName();
		String classNickName = TemplateStringUtil.getNickNameFromClassName(clsName);
		if (propName.contains(".")) { // 关联字段
			String propNickName = propName.substring(0, propName.indexOf("."));
			ClassImpl fldCls = modelEngine.getRelativePropertyCls(clsName,
					propNickName);
			handleJoinSql(tableSql, classNickName, propNickName, fldCls);

			mergeSelectAndJoinSql(new SqlSelectJoinBean(fldCls,
					propName.substring(propName.indexOf(".") + 1), selectSql,
					tableSql));
		} else { //
			String fldName = TemplateStringUtil.getFieldNameFromPropertyName(propName);

			Property prop = modelEngine.getProperty(clsName, propName);
			Type propType = prop.getType();
			String selFld = classNickName + "."
					+ TemplateStringUtil.getFieldNameFromPropertyName(fldName);
			String fldNickName = getFieldNickName(classNickName, fldName);
			if (selectFields.contains(selFld))
				return;
			selectSql.append(",");
			if (org.eclipse.uml2.uml.Class.class.isInstance(propType)) {
				selectSql.append(selFld + "_id");
			} else if(EnumerationImpl.class.isInstance(propType)) {
				Map<String,String> enumMap = modelEngine.getEnumerationMap(prop);
				selectSql.append(" case ");
				for(Map.Entry<String,String> enumEty : enumMap.entrySet()){
					String key = enumEty.getKey();
					String value = enumEty.getValue();
					selectSql.append(" when " + selFld + " = '" + key + "' then '" + value + "' ");
				}
				selectSql.append(" end ");
//				selectSql.append(selFld);
			} else {
				selectSql.append(selFld);
			}
			selectSql.append(" as " + fldNickName);
			selectFields.add(selFld);
		}
	}

	private String getFieldNickName(String classNickName, String fldName) {
		return classNickName + fldName;
	}

	private void handleJoinSql(StringBuilder tableSql, String leftNickName,
			String leftFldName, ClassImpl fldCls) {
		String fldClsName = fldCls.getName();
		String fldTableName = TemplateStringUtil.getTableNameFromClassName(fldClsName);
		if (this.joinTableNames.contains(fldTableName)) {
			return;
		}
		String fldNickName = TemplateStringUtil.getNickNameFromClassName(fldClsName);
		tableSql.append(formatJoinSql(leftNickName, leftFldName, fldTableName,
				fldNickName));
		joinTableNames.add(fldTableName);
	}

	/**
	 * Format join sql. 格式化表链接sql
	 * 
	 * @param leftNickName
	 *            the nick name
	 * @param fldTableName
	 *            the fld table name
	 * @param fldNickName
	 *            the fld nick name
	 * @return the string
	 */
	protected String formatJoinSql(String leftNickName, String leftFldName,
			String fldTableName, String fldNickName) {
		return " left join " + fldTableName + " " + fldNickName + " on "
				+ leftNickName + "."
				+ TemplateStringUtil.getFieldNameFromPropertyName(leftFldName) + "_id = "
				+ fldNickName + ".id ";
	}

	/**
	 * Merge join and filter sql. 表链接和条件sql
	 * 
	 */
	public void mergeJoinAndFilterSql(SqlJoinFilterBean bean) {
		try {
			String propName = bean.getPropName();
			String clsName = bean.getCls().getName();
			String nickName = TemplateStringUtil.getNickNameFromClassName(clsName);

			if(StringUtil.isEmpty(bean.getValue())){
				String tagName = ViewUtil.transPropertyName2TagId(propName);
				String value = bean.getReq().getParameter(tagName); // 查询值
				bean.setValue(value);
			}

			if (propName.contains(".")) { // 关联字段
			// ClassImpl fldCls = modelEngine.getRelativePropertyCls(clsName,
			// propNickName);
				ClassImpl fldCls = modelEngine.getRelativePropertyFirstCls(clsName,
						propName);
				String propNickName = propName.substring(0,
						propName.indexOf("."));

				handleJoinSql(bean.getTableSql(), nickName, propNickName,
						fldCls);

				bean.setPropName(propName.substring(propName.indexOf(".") + 1));
				bean.setCls(fldCls);
				mergeJoinAndFilterSql(bean);
			} else { //
				String fldName = TemplateStringUtil.getFieldNameFromPropertyName(propName);

				Property prop = modelEngine.getProperty(clsName, propName);
				String value = bean.getValue();

				if (StringUtil.isEmpty(value)) { // 无空查询条件
					return;
				}
				Type propType = prop.getType();
				if (PrimitiveTypeImpl.class.isInstance(propType)) { // 原始类型
					if (!propType.eIsProxy()) {
						return;
					}
					appendFields(bean, nickName, fldName, prop, value);
				} else if (EnumerationImpl.class.isInstance(propType)) { // 枚举
					bean.getWhereSql().append(" and " + nickName + "." + fldName + " = ? ");
					bean.getParams().add(value);
				} else if (ClassImpl.class.isInstance(propType)) { // 关联类型
					bean.getWhereSql().append(" and " + nickName + ".id = ? ");
					bean.getParams().add(value);
				}

			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private void appendFields(SqlJoinFilterBean bean, String nickName,
			String fldName, Property prop, String value) throws ParseException {
		String typeStr = ((org.eclipse.emf.ecore.InternalEObject) prop
				.getType()).eProxyURI().fragment()
				.toLowerCase();
		if (typeStr.contains("string")) { // 字符串
			bean.getWhereSql().append(" and " + nickName + "." + fldName + " like ? ");
			bean.getParams().add("%" + value + "%");
		} else if (typeStr.contains("int")) {
			bean.getWhereSql().append(" and " + nickName + "." + fldName + " = ? ");
			bean.getParams().add(value);
		} else if (typeStr.contains("boolean")) {
			bean.getWhereSql().append(" and " + nickName + "." + fldName + " = ? ");
			bean.getParams().add(value.equals("1") ? 1 : 0);
		} else if (typeStr.contains("date")) {
			bean.getWhereSql().append(" and " + nickName + "." + fldName + " = ? ");
			bean.getParams().add(DateUtils.parseDate(value, Format.DATE));
		} else if (typeStr.contains("datetime")) {
			bean.getWhereSql().append(" and " + nickName + "." + fldName + " = ? ");
			bean.getParams().add(DateUtils.parseDate(value, Format.DATETIME));
		} else if (typeStr.contains("byte") || typeStr.contains("short") || typeStr.contains("long")) {
			bean.getWhereSql().append(" and " + nickName + "." + fldName + " = ? ");
			bean.getParams().add(value);
		} else if (typeStr.contains("float") || typeStr.contains("double")) {
			bean.getWhereSql().append(" and " + nickName + "." + fldName + " = ? ");
			bean.getParams().add(value);
		} else if (typeStr.contains("char")) {
			bean.getWhereSql().append(" and " + nickName + "." + fldName + " like ? ");
			bean.getParams().add("%" + value + "%");
		} else if (typeStr.contains("blob")) {

		} else if (typeStr.contains("clob")) {
			bean.getWhereSql().append(" and " + nickName + "." + fldName + " like ? ");
			bean.getParams().add("%" + value + "%");
		}
	}

	public String parseModelSorter(String clsName) {
		StringBuilder ret = new StringBuilder("");

		ClassImpl ci = modelEngine.getClass(clsName);
		List<Parameter> queryParams = getQueryParams(ci);

		for(Parameter parameter : queryParams){
			String sortName = parameter.getName();
			if(parameter.isOrdered() == true){
				if (StringUtil.isEmpty(sortName))
					return ret.toString();
				if(ret.length() > 0){
					ret.append(",");
				}

				if (sortName.contains(".")) {
					int lastPos = sortName.lastIndexOf(".");
					String lastClsName = sortName.substring(0, lastPos);
					String lastFldName = sortName.substring(lastPos + 1);
					Property prop = modelEngine.getProperty(clsName, lastClsName);
					if (modelEngine.isClassProperty(prop)) {
						Property fld = modelEngine.getProperty(modelEngine.getPropertyCls(prop).getName(), lastFldName);
						if (modelEngine.isClassProperty(fld)) { // 最末级也是class默认按id排序
							ret.append(getSorterTableNickName(fld) + ".id");
						}else{
							ret.append(getSorterTableNickName(prop) + "." + TemplateStringUtil.getFieldNameFromPropertyName(lastFldName));
						}
					}
				}else{
					ret.append(sortName);
				}
			}
		}

		return ret.toString();
	}

	public String getSorterTableNickName(Property prop) {
		ClassImpl ci = (ClassImpl) prop.getType();
		String name = ci.getName();
		return TemplateStringUtil.getNickNameFromClassName(name);
	}
	
	/**
	 * Gets the query props. 获取查询(操作)字段
	 * 
	 * @param ci
	 *            the ci
	 * @return the query props
	 */
	public List<String> getQueryNames(ClassImpl ci) {

		return getOperaitonNames(ci, OPERATION_LIST);
	}

	/**
	 * Gets the query props. 获取查询(操作)字段
	 *
	 * @param ci
	 *            the ci
	 * @return the query props
	 */
	public List<Parameter> getQueryParams(ClassImpl ci) {

		return getOperaitonParams(ci, OPERATION_LIST);
	}

	/**
	 * Gets the filter props. 获取条件过滤(操作)字段
	 * 
	 * @param ci
	 *            the ci
	 * @return the filter props
	 */
	public List<String> getFilterNames(ClassImpl ci) {

		return getOperaitonNames(ci, OPERATION_SEARCH);
	}

	/**
	 * Gets the filter props. 获取条件过滤(操作)字段
	 *
	 * @param ci
	 *            the ci
	 * @return the filter props
	 */
	public List<Parameter> getFilterParams(ClassImpl ci) {

		return getOperaitonParams(ci, OPERATION_SEARCH);
	}

	/**
	 * Gets the operaiton props. 获取操作字段
	 * 
	 * @param ci
	 *            the ci
	 * @param optName
	 *            the opt name
	 * @return the operaiton props
	 */
	public List<String> getOperaitonNames(ClassImpl ci, String optName) {
		List<String> ret = Collections.emptyList();

		List<Parameter> params = getOperaitonParams(ci, optName);
		int size = params.size();
		for (int index = 0; index < size; index++) {
			Parameter param = params.get(index);
			String propName = param.getName();
			ret.add(propName);
		}

		return ret;
	}

	public List<Parameter> getOperaitonParams(ClassImpl ci, String optName) {
		Operation opt = getOperation(ci, optName);
		if (opt == null) {
			return null;
		}
		return opt.getOwnedParameters();
	}
}