package com.se7en.dao;

public enum Order {
	ASC("asc"),
	DESC("desc");
	
	private String name;
	
	private Order(String name){
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
