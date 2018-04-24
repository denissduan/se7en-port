package com.se7en.model.base;
import com.se7en.model.base.BaseRole;
import com.se7en.model.base.BaseMenu;
import java.util.Date;
import com.se7en.model.base.BaseUser;
import java.util.Date;
import com.se7en.model.base.BaseUser;
import com.se7en.model.BaseObject;
/**
 * BaseRoleMenu entity
 */
public class BaseRoleMenu extends BaseObject implements java.io.Serializable {
	
	public final static BaseRoleMenu EMPTY_BASEROLEMENU = new BaseRoleMenu();
	
	// Fields
	private BaseRole role;	//角色
	private BaseMenu menu;	//菜单
	// Property accessors
	public BaseRole getRole() {
		return role;
	}
	public void setRole(BaseRole role) {
		this.role = role;
	}
	public BaseMenu getMenu() {
		return menu;
	}
	public void setMenu(BaseMenu menu) {
		this.menu = menu;
	}
	
	/** default constructor */
	public BaseRoleMenu() {
	}
}