package com.se7en.model;

import java.util.HashMap;
import java.util.Map;

public class AjaxResult {
	
	private boolean sign;

	private String message;
	
	private Map<String,Object> data = new HashMap<String,Object>(2);

	public boolean isSign() {
		return sign;
	}

	public void setSign(boolean sign) {
		this.sign = sign;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public AjaxResult() {
	}
	
	public AjaxResult(boolean success) {
	}
	
}
