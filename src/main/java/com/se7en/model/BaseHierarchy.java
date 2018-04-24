package com.se7en.model;
import com.se7en.model.Base;
/**
 * BaseHierarchy entity
 */
public class BaseHierarchy extends Base implements java.io.Serializable {
	
	public final static BaseHierarchy EMPTY_BASEHIERARCHY = new BaseHierarchy();
	
	// Fields
	private Integer pid;			//父节点
	private String name;			//名称
	private Integer lindex;			//左顺序
	private Integer rindex;			//右顺序
	private Integer level;			//层级
	// Property accessors
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getLindex() {
		return lindex;
	}
	public void setLindex(Integer lindex) {
		this.lindex = lindex;
	}	
	public Integer getRindex() {
		return rindex;
	}
	public void setRindex(Integer rindex) {
		this.rindex = rindex;
	}	
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}	
	
	/** default constructor */
	public BaseHierarchy() {
	}
}