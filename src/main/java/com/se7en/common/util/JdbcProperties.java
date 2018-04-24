package com.se7en.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

public final class JdbcProperties {
	
	private JdbcProperties(){
		throw new UnsupportedOperationException("工具类不能被实例化");
	}
	
	private final static String PROPERTIES = "config.properties";

	public static Properties getPropObjFromFile() {

		Properties objProp = new Properties();

		Thread currentThread = Thread.currentThread();
		ClassLoader classLoader = currentThread.getContextClassLoader();

		URL url = classLoader.getResource(PROPERTIES);

		if (url == null) {

			classLoader = ClassLoader.getSystemClassLoader();

			url = classLoader.getResource(PROPERTIES);

		}

		final File file = new File(url.getFile());

		InputStream inStream = null;

		try {

			inStream = new FileInputStream(file);

			objProp.load(inStream);

		} catch (FileNotFoundException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (inStream != null) {

					inStream.close();

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return objProp;

	}

}
