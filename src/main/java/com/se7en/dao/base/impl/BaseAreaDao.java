package com.se7en.dao.base.impl;
import com.se7en.model.base.BaseArea;
import org.springframework.stereotype.Repository;
import com.se7en.dao.EjbDao;
import com.se7en.dao.base.IBaseAreaDao;
/**
 *  * database access obj 
 */
@Repository
public class BaseAreaDao extends EjbDao<BaseArea> implements IBaseAreaDao {
}