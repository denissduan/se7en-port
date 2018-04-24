package com.se7en.common.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class DBConnection {

	private static DBConnection instance;

	private static String driverClassName;

	private static String connectionUrl;

	private static String userName;

	private static String password;

	private static Connection conn;

	private static Properties jdbcProp;

	private final static String DRIVER_CLASS_NAME = "jdbc.driverClassName";

	private final static String CONNECTION_URL = "jdbc.databaseURL";

	private final static String DB_USER_NAME = "jdbc.username";

	private final static String DB_USER_PASSWORD = "jdbc.password";

	private DBConnection() {

	}

	private static Properties getConfigFromPropertiesFile() {

		final Properties prop = JdbcProperties.getPropObjFromFile();

		return prop;

	}

	private static void initJdbcParameters(final Properties prop) {

		driverClassName = prop.getProperty(DRIVER_CLASS_NAME);

		connectionUrl = prop.getProperty(CONNECTION_URL);

		userName = prop.getProperty(DB_USER_NAME);

		password = prop.getProperty(DB_USER_PASSWORD);

	}

	private static void createConnection() throws ClassNotFoundException, SQLException{

		Class.forName(driverClassName);

		conn = DriverManager.getConnection(connectionUrl, userName, password);

	}

	public static Connection getConnection() {

		return conn;

	}

	public synchronized static DBConnection getInstance() throws ClassNotFoundException, SQLException {

		if (instance == null) {

			jdbcProp = getConfigFromPropertiesFile();

			instance = new DBConnection();

		}

		initJdbcParameters(jdbcProp);

		createConnection();

		return instance;

	}

}
