package com.se7en.dao.base.impl;
import com.se7en.model.base.BaseUser;
import org.springframework.stereotype.Repository;
import com.se7en.dao.EjbDao;
import com.se7en.dao.base.IBaseUserDao;
/**
 *  * database access obj 
 */
@Repository
public class BaseUserDao extends EjbDao<BaseUser> implements IBaseUserDao {
}