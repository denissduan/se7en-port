package com.se7en.model.base;
import com.se7en.model.BaseHierarchy;
/**
 * BaseArea entity
 */
public class BaseArea extends BaseHierarchy implements java.io.Serializable {
	
	public final static BaseArea EMPTY_BASEAREA = new BaseArea();
	
	// Fields
	private String describ;			//描述
	// Property accessors
	public String getDescrib() {
		return describ;
	}
	public void setDescrib(String describ) {
		this.describ = describ;
	}
	
	/** default constructor */
	public BaseArea() {
	}
}