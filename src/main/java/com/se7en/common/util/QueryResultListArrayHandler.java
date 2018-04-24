package com.se7en.common.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QueryResultListArrayHandler implements QueryResultHandler {

	public void execute(final ResultSet rs,final List<Object> result,int colNums,List<String> colNames){
		List<Object> row = new ArrayList<Object>(10);
		//遍历当前结果行
		try {
			for(int index = 1;index <= colNums;index++){
				Object value = rs.getObject(index);
				row.add(value);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Object[] values = row.toArray();
		result.add(values);
	}
	
}
