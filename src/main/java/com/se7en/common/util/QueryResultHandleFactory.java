package com.se7en.common.util;


public final class QueryResultHandleFactory {

	private QueryResultHandleFactory(){
		throw new UnsupportedOperationException("工具类不能被实例化");
	}
	
	public static QueryResultHandler create(int type, int colNums){
		QueryResultHandler ret = QueryResultHandler.EMPTY_HANDLER;
		if(type == DBUtil.RT_LIST_ARRAY && colNums > 1){
			ret = new QueryResultListArrayHandler();
		}else if(type == DBUtil.RT_LIST_ARRAY && colNums == 1){
			ret = new QueryResultListSingleHandler();
		}else if(type == DBUtil.RT_LIST_MAP){
			ret = new QueryResultListMapHandler();
		}
		
		return ret;
	}
	
}
