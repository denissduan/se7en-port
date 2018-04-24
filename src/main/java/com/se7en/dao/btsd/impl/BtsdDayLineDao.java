package com.se7en.dao.btsd.impl;
import com.se7en.model.btsd.BtsdDayLine;
import org.springframework.stereotype.Repository;
import com.se7en.dao.EjbDao;
import com.se7en.dao.btsd.IBtsdDayLineDao;

/**
 *  * database access obj 
 */
@Repository
public class BtsdDayLineDao extends EjbDao<BtsdDayLine> implements IBtsdDayLineDao {
	
}