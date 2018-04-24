package com.se7en.model.base;
import com.se7en.model.base.BaseUnit;
import com.se7en.model.Base;
/**
 * BaseUser entity
 */
public class BaseUser extends Base implements java.io.Serializable {
	
	public final static BaseUnit EMPTY_BASEUNIT = new BaseUnit();
	
	// Fields
	private String name;			//用户名
	private String password;			//登录密码
	private String phone;			//号码
	private String number;			//编号
	private BaseUnit unit;	//组织
	// Property accessors
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public BaseUnit getUnit() {
		return unit;
	}
	public void setUnit(BaseUnit unit) {
		this.unit = unit;
	}
	
	/** default constructor */
	public BaseUser() {
	}
}