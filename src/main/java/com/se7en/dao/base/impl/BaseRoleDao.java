package com.se7en.dao.base.impl;
import com.se7en.model.base.BaseRole;
import org.springframework.stereotype.Repository;
import com.se7en.dao.EjbDao;
import com.se7en.dao.base.IBaseRoleDao;
/**
 * 用户角色 * database access obj 
 */
@Repository
public class BaseRoleDao extends EjbDao<BaseRole> implements IBaseRoleDao {
}