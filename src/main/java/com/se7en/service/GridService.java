package com.se7en.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import javax.annotation.Resource;

import com.se7en.common.ColInfo;
import com.se7en.service.md.ModelEngine;
import com.se7en.web.vo.QueryView;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.internal.impl.ClassImpl;
import org.springframework.stereotype.Service;

import com.se7en.dao.ICommonDao;
import com.se7en.service.md.ModelSqlService;
import com.se7en.service.md.StringServices;
import com.se7en.common.util.StringUtil;

/**
 * The Class GridService. 表格组件封装
 */
@Service
public class GridService implements IGridService {

	@Resource(name = "modelEngine")
	private ModelEngine engine;

	@Resource(name = "stringServices")
	private StringServices strSrv;

	@Resource(name = "pageQueryService")
	private PageQueryService pageQuerySrv;
	
	@Resource(name = "modelSqlService")
	private ModelSqlService modelSqlSrv;

	@Resource(name = "commonDao")
	private ICommonDao commonDao;
	
	private final String PK_NAME = "id";

	/**
	 * 根据字段名反向生成字段信息
	 */
	@Override
	public List<List<ColInfo>> createModelThead(Class<?> cls, Collection<String> titles) throws SQLException {
		engine.clearCache();
		List<List<ColInfo>> thead = new ArrayList<List<ColInfo>>(10);

		List<ColInfo> ret = new ArrayList<ColInfo>(10);

		String clsName = cls.getSimpleName();

		// Connection con = DBUtil.getSe7enConnection();
		// Map<String,Field> colMap = DBUtil.mapFields(con, tableName,
		// DBUtil.DBTYPE_MYSQL);

		for (String pName : titles) {
			pName = pName.trim();
			ColInfo colInfo = new ColInfo();
			if (StringUtils.isBlank(pName)) {
				ret.add(colInfo);
				continue;
			}

			if (StringUtils.isAsciiPrintable(pName)) { // 不含中文字符,UML引擎获取
				/*if(pName.contains(".")){		//关联字段
					Property prop = engine.getProperty(clsName, pName);
					if(prop == null)
						continue;
					ClassImpl propCls = (ClassImpl)prop.getType();
					String propFinalName = prop.getName();
					String propClsName = propCls.getName();
					String propTableName = strSrv.getTableNameFromClassName(propClsName);
					String propNickName = strSrv.getNickNameFromClassName(propClsName);
					handlePrimartyProperty(propTableName, propClsName, propNickName,propFinalName, colInfo);
				}else{
					// Field fld = colMap.get(columnName);
					handlePrimartyProperty(tableName, clsName, nickName,pName, colInfo);
				}*/
				injectTHInfo(clsName, pName, colInfo);
			} else {
				colInfo.setTitle(pName);
			}

			ret.add(colInfo);
		}
		thead.add(ret);
		return thead;
	}

	protected void injectTHInfo(String clsName, String propName, ColInfo colInfo) {
		propName = parseFormat(propName, colInfo);
		
		Property prop = engine.getProperty(clsName, propName);
		if(prop == null)
			throw new RuntimeException(propName + "表字段不存在");
		colInfo.setOrder((short) 0);
		if (engine.isPrimartyProperty(prop)) {			//基础字段
			colInfo.setSort(propName); // 排序列名
		} else if (engine.isClassProperty(prop)) {		//关联字段
			String relFldName = "id";			//默认关联字段为id
			if(propName.contains(".")){
				relFldName = strSrv.getFieldNameFromPropertyName(propName.split("\\.")[1].trim());
			}
//			throw new RuntimeException(propName + "表头字段类型判断错误");
			colInfo.setSort(strSrv.getNickNameFromClassName(((ClassImpl) prop.getType()).getName()) + "." + relFldName);
		}
		String title = prop.getOwnedComments().get(0).getBody();
		if (StringUtil.isNotBlank(title)) {
			colInfo.setTitle(title);
		} else {
			colInfo.setTitle(propName);
		}
		colInfo.setEditable(false); // 可编辑单元格
		colInfo.setName(propName);
	}
	
	protected String parseFormat(String propName, ColInfo colInfo){
		String ret = propName;
		if(propName.contains(":")){
			String[] split = propName.split(":");
			String pkFormat = split[1];
			colInfo.setFormat(pkFormat);
			ret = split[0];
		}
		
		return ret;
	}
	
	@Override
	public List<List<ColInfo>> createModelThead(Class<?> cls, String... titles)
			throws Exception {

		return createModelThead(cls, Arrays.asList(titles));
	}

	/**
	 * 
	 * @param str
	 * @param list
	 * @return
	 */
	private ColInfo analyzeCol(String str) {
		ColInfo ci = new ColInfo();
		StringTokenizer st = new StringTokenizer(str, ":");
		if (st.hasMoreTokens()) {
			String title = st.nextToken().trim();
			ci.setTitle(title);
			// 判断表现形式,默认无
			if (st.hasMoreTokens()) {
				ci.setFormat(st.nextToken());
			}
		}
		return ci;
	}
	
	@Override
	public List<List<ColInfo>> createReflectThead(Class<?> cls) throws Exception {
		java.lang.reflect.Field[] flds = cls.getDeclaredFields();
		List<String> fldNames = new ArrayList<String>();
		for (int i = 0; i < flds.length; i++) {
			fldNames.add(flds[i].getName());
		}

		return createModelThead(cls, fldNames);
	}
	
	@Override
	public List<List<ColInfo>> createModelThead(Class<?> cls) throws Exception {
		List<List<ColInfo>> ret = Collections.emptyList();
		List<String> fldNames = getQueryFieldNamesFromModel(cls, ret);
		
		if(CollectionUtils.isNotEmpty(fldNames))
			ret = createModelThead(cls ,fldNames);
		
		return ret;
	}

	/**
	 * 从uml解析排序
	 * @param cls
	 * @return
	 * @throws Exception
     */
	public String parseModelSort(Class<?> cls) throws Exception {
		//处理排序
		return modelSqlSrv.parseModelSorter(cls.getSimpleName());
	}

	private List<String> getQueryFieldNamesFromModel(Class<?> cls,
			List<List<ColInfo>> ret) {
		List<String> names = new LinkedList<String>();
		ClassImpl ci = engine.getClass(cls);
		Operation opt = modelSqlSrv.getOperation(ci, ModelSqlService.OPERATION_LIST);
		if(opt == null)
			return names;
		EList<Parameter> queryParams = opt.getOwnedParameters();
		int size = queryParams.size();
		boolean haveId = false;
		for (int index = 0;index < size;index++) {
			Parameter param = queryParams.get(index);
			String propName = param.getName();
			if("id".equals(propName)){
				haveId = true;
			}
//			Property prop = engine.getProperty(clsName, propName);
//			EList<Comment> comments = prop.getOwnedComments();
			names.add(propName);
		}
//		if(!haveId)
//			names.add(0, "id");
		return names;
	}

	/**
	 * 更新单元格值
	 * 
	 * @param id
	 * @param tableName
	 * @param fieldName
	 * @param value
	 * @return
	 */
	// @Transactional(isolation = Isolation.DEFAULT, propagation =
	// Propagation.REQUIRED, readOnly = false)
	public boolean saveCell(String id, String tableName, String fieldName,
			String value) {
		String updateSql = " update " + tableName + " set " + fieldName
				+ " = ? where id = ? ";
		Object[] params = { value, id };
		int count = commonDao.execute(updateSql, params);
		boolean flag = false;
		if (count > 0)
			flag = true;
		return flag;
	}
	
	public List<List<ColInfo>> createSimpleThead(String... titles) {

		return createSimpleThead(Arrays.asList(titles));
	}

	public List<List<ColInfo>> createSimpleThead(Collection<String> titles) {
		List<List<ColInfo>> ret = new ArrayList<List<ColInfo>>();
		List<ColInfo> list = new ArrayList<ColInfo>();
		for (String title : titles) {
			ColInfo colInfo = new ColInfo();
			colInfo.setTitle(title);
			colInfo.setOrder((short) 0);

			list.add(colInfo);
		}
		ret.add(list);
		return ret;
	}
}
