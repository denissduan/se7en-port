package com.se7en.dao.sys.impl;
import com.se7en.model.sys.SysParam;
import org.springframework.stereotype.Repository;
import com.se7en.dao.EjbDao;
import com.se7en.dao.sys.ISysParamDao;
/**
 * 系统参数 * database access obj 
 */
@Repository
public class SysParamDao extends EjbDao<SysParam> implements ISysParamDao {
}