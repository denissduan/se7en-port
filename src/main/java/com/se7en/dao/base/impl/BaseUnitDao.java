package com.se7en.dao.base.impl;
import com.se7en.model.base.BaseUnit;
import org.springframework.stereotype.Repository;
import com.se7en.dao.EjbDao;
import com.se7en.dao.base.IBaseUnitDao;
/**
 * 组织 * database access obj 
 */
@Repository
public class BaseUnitDao extends EjbDao<BaseUnit> implements IBaseUnitDao {
}