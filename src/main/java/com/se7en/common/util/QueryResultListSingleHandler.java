package com.se7en.common.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class QueryResultListSingleHandler implements QueryResultHandler {

	public void execute(final ResultSet rs,final List<Object> result,int colNums,List<String> colNames){
		//遍历当前结果行
		try {
			Object value = rs.getObject(1);
			result.add(value);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
