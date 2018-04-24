package com.se7en.common.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryResultListMapHandler implements QueryResultHandler {

	@Override
	public void execute(ResultSet rs, List<Object> result, int colNums,List<String> colNames) {
		Map<String,Object> row = Collections.emptyMap();
		//遍历当前结果行
		try {
			if(colNums > 0)
				row = new HashMap<String,Object>();
			for(int index = 1;index <= colNums;index++){
				Object value = rs.getObject(index);
				row.put(colNames.get(index - 1), value);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		result.add(row);
	}

}
