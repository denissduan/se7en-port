package com.se7en.dao.btsd.impl;
import com.se7en.model.btsd.BtsdCoin;
import org.springframework.stereotype.Repository;
import com.se7en.dao.EjbDao;
import com.se7en.dao.btsd.IBtsdCoinDao;
/**
 *  * database access obj 
 */
@Repository
public class BtsdCoinDao extends EjbDao<BtsdCoin> implements IBtsdCoinDao {
}