package com.se7en.dao.base.impl;
import com.se7en.model.base.BaseMenu;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.se7en.dao.EjbDao;
import com.se7en.dao.base.IBaseMenuDao;

import java.util.List;

/**
 * 菜单 * database access obj 
 */
@Repository
public class BaseMenuDao extends EjbDao<BaseMenu> implements IBaseMenuDao {

}