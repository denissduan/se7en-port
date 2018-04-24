package com.se7en.model.base;
import com.se7en.model.base.BaseUnit;
import java.util.Date;
import com.se7en.model.base.BaseUser;
import java.util.Date;
import com.se7en.model.base.BaseUser;
import com.se7en.model.BaseObject;
/**
 * BaseRole entity
 */
public class BaseRole extends BaseObject implements java.io.Serializable {
	
	public final static BaseRole EMPTY_BASEROLE = new BaseRole();
	
	// Fields
	private String name;			//角色名称
	private BaseUnit unit;	//所属部门
    private Integer unit_id;
	// Property accessors
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BaseUnit getUnit() {
		return unit;
	}
	public void setUnit(BaseUnit unit) {
		this.unit = unit;
	}

    public Integer getUnit_id() {
        return unit_id;
    }

    public void setUnit_id(Integer unit_id) {
        this.unit_id = unit_id;
    }

    /** default constructor */
	public BaseRole() {
	}
}