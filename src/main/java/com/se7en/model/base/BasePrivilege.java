package com.se7en.model.base;
import java.util.Date;
import java.util.Date;
import com.se7en.model.BaseObject;
/**
 * BasePrivilege entity
 */
public class BasePrivilege extends BaseObject implements java.io.Serializable {
	
	public final static BasePrivilege EMPTY_BASEPRIVILEGE = new BasePrivilege();
	
	// Fields
	private String code;			//代码
	private String name;			//权限名称
	private String icon;			//图标
	private String css;			//样式
	// Property accessors
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getCss() {
		return css;
	}
	public void setCss(String css) {
		this.css = css;
	}
	
	/** default constructor */
	public BasePrivilege() {
	}
}