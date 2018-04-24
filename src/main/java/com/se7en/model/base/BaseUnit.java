package com.se7en.model.base;
import com.se7en.model.base.BaseArea;
import com.se7en.model.BaseHierarchy;
/**
 * BaseUnit entity
 */
public class BaseUnit extends BaseHierarchy implements java.io.Serializable {
	
	public final static BaseUnit EMPTY_BASEUNIT = new BaseUnit();
	
	// Fields
	private BaseArea area;	//区域
	private Integer state;			//状态	
	private String phone;			//办公电话
	// Property accessors
	public BaseArea getArea() {
		return area;
	}
	public void setArea(BaseArea area) {
		this.area = area;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	/** default constructor */
	public BaseUnit() {
	}
}