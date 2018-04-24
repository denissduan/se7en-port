package com.se7en.service.md;

import java.util.HashMap;
import java.util.Map;

public class VariableServices {
	
	private static Map<String,Object> vars = new HashMap<String,Object>(20); 

	public void putString(String node,String val,String key){
		vars.put(key, val);
	}
	
	public String getString(String node,String key){
		return vars.get(key).toString();
	}
	
}
