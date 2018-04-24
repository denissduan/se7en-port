package com.se7en.dao.btsd.impl;
import com.se7en.model.btsd.BtsdHourLine;
import org.springframework.stereotype.Repository;
import com.se7en.dao.EjbDao;
import com.se7en.dao.btsd.IBtsdHourLineDao;

/**
 *  * database access obj 
 */
@Repository
public class BtsdHourLineDao extends EjbDao<BtsdHourLine> implements IBtsdHourLineDao {
	
}