package com.se7en.model;
/**
 * Base entity
 */
public class Base implements java.io.Serializable {
	// Fields
	private Integer id;			//主键
	// Property accessors
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}	
	
	/** default constructor */
	public Base() {
	}
}