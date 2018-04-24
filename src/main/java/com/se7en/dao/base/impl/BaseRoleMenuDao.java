package com.se7en.dao.base.impl;
import com.se7en.model.base.BaseRoleMenu;
import org.springframework.stereotype.Repository;
import com.se7en.dao.EjbDao;
import com.se7en.dao.base.IBaseRoleMenuDao;
/**
 * 角色菜单 * database access obj 
 */
@Repository
public class BaseRoleMenuDao extends EjbDao<BaseRoleMenu> implements IBaseRoleMenuDao {
}