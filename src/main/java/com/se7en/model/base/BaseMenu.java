package com.se7en.model.base;

import java.util.LinkedHashSet;
import java.util.Set;

import com.se7en.model.BaseHierarchy;

/**
 * BaseMenu entity
 */
public class BaseMenu extends BaseHierarchy implements java.io.Serializable {
	
	public final static BaseMenu EMPTY_BASEMENU = new BaseMenu();
	
	// Fields
	private String icon;			//图标
	private String url;			//链接
	private Integer open;			//是否展开
	private String data;			//节点数据
	private Integer sindex;			//显示顺序
	private Integer state;			//状态
	private Set<BaseMenu> menus = new LinkedHashSet<BaseMenu>();
	// Property accessors
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getOpen() {
		return open;
	}
	public void setOpen(Integer open) {
		this.open = open;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public Integer getSindex() {
		return sindex;
	}
	public void setSindex(Integer sindex) {
		this.sindex = sindex;
	}	
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}	
	
	public Set<BaseMenu> getMenus() {
		return menus;
	}
	public void setMenus(Set<BaseMenu> menus) {
		this.menus = menus;
	}
	/** default constructor */
	public BaseMenu() {
	}
}