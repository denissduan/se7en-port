package com.se7en.common.util;

import java.sql.ResultSet;
import java.util.List;

public interface QueryResultHandler {
	
	public final static QueryResultHandler EMPTY_HANDLER = new QueryResultHandler(){

		@Override
		public void execute(ResultSet rs, List<Object> result, int colNums,
				List<String> colNames) {
		}
		
	};

	public void execute(final ResultSet rs,final List<Object> result,int colNums,List<String> colNames);
	
}
