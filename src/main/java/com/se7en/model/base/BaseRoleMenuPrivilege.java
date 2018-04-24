package com.se7en.model.base;
import com.se7en.model.base.BaseRoleMenu;
import com.se7en.model.base.BasePrivilege;
import java.util.Date;
import com.se7en.model.base.BaseUser;
import java.util.Date;
import com.se7en.model.base.BaseUser;
import com.se7en.model.BaseObject;
/**
 * BaseRoleMenuPrivilege entity
 */
public class BaseRoleMenuPrivilege extends BaseObject implements java.io.Serializable {
	
	public final static BaseRoleMenuPrivilege EMPTY_BASEROLEMENUPRIVILEGE = new BaseRoleMenuPrivilege();
	
	// Fields
	private BaseRoleMenu roleMenu;	//角色菜单
	private BasePrivilege privilege;	//权限
	// Property accessors
	public BaseRoleMenu getRoleMenu() {
		return roleMenu;
	}
	public void setRoleMenu(BaseRoleMenu roleMenu) {
		this.roleMenu = roleMenu;
	}
	public BasePrivilege getPrivilege() {
		return privilege;
	}
	public void setPrivilege(BasePrivilege privilege) {
		this.privilege = privilege;
	}
	
	/** default constructor */
	public BaseRoleMenuPrivilege() {
	}
}