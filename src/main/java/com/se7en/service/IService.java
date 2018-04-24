package com.se7en.service;

import java.util.List;
import java.util.Map;

/**
 * The Interface IService.
 * 业务层抽象接口
 */
public interface IService {

	List<Map<String,String>> comboFormat(List<Object[]> list);
	
}
