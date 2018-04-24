package com.se7en.common.util;

import java.sql.Connection;
import java.sql.SQLException;

public final class ConnectionManager {
	
	private static ConnectionManager entity;

	private static ThreadLocal<Connection> tl = new ThreadLocal<Connection>();

	private static Connection conn = null;
	
	private ConnectionManager(){
	}
	
	public ConnectionManager getInstance(){
		ConnectionManager cm = entity;
		if(cm == null){
			synchronized (ConnectionManager.class) {
				if(cm == null){
					entity = cm = new ConnectionManager();
				}
			}
		}
		
		return entity;
	}

	public void beginTrans(boolean beginTrans) {
		try {
			if (tl.get() == null || ((Connection) tl.get()).isClosed()) {
				conn = DBConnection.getInstance().getConnection();
				if (beginTrans) {
					conn.setAutoCommit(false);
				}
				tl.set(conn);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() {

		return tl.get();
	}

	public void close() throws SQLException {
		tl.get().setAutoCommit(true);
		tl.get().close();
		tl.set(null);
	}

	public void commit() throws SQLException {

		try {

			tl.get().commit();

		} catch (Exception e) {

		}

		try {

			tl.get().setAutoCommit(true);

		} catch (Exception e) {

		}

	}

	public void rollback() throws SQLException {

		try {

			tl.get().rollback();

		} catch (Exception e) {

		}

		try {

			tl.get().setAutoCommit(true);

		} catch (Exception e) {

		}

	}

}
