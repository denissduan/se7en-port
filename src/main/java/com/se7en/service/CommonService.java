package com.se7en.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


import org.springframework.stereotype.Service;

/**
 * The Class CommonService.
 * 通用业务
 */
@Service("commonService")
public class CommonService implements IService {
	
	/**
	 * 下拉框返回前端json数据格式化 (默认数组第一个元素value,第二个text)
	 * 
	 * @param list
	 * @return
	 */
	@Override
	public List<Map<String,String>> comboFormat(List<Object[]> list) {
		List<Map<String,String>> jsons = new LinkedList<>();
		for (Object[] objs : list) {
			String val = (String) objs[0];
			String text = (String) objs[1];
			Map<String,String> json = new HashMap<>(2);
			json.put("value", val);
			json.put("text", text);
			jsons.add(json);
		}
		return jsons;
	}

}
