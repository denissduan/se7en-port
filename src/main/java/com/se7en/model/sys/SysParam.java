package com.se7en.model.sys;
import com.se7en.model.Base;
/**
 * SysParam entity
 */
public class SysParam extends Base implements java.io.Serializable {
	
	public final static SysParam EMPTY_SYSPARAM = new SysParam();
	
	// Fields
	private String module;			//子系统
	private String pname;			//参数名
	private String pvalue;			//参数值
	private String describ;			//描述
	private Integer pindex;			//顺序
	// Property accessors
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getPvalue() {
		return pvalue;
	}
	public void setPvalue(String pvalue) {
		this.pvalue = pvalue;
	}
	public String getDescrib() {
		return describ;
	}
	public void setDescrib(String describ) {
		this.describ = describ;
	}
	public Integer getPindex() {
		return pindex;
	}
	public void setPindex(Integer pindex) {
		this.pindex = pindex;
	}	
	
	/** default constructor */
	public SysParam() {
	}
}