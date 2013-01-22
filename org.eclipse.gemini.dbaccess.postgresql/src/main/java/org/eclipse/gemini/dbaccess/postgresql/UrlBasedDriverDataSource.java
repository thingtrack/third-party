/*
 * Copyright 2011 Thingtrack, S.L.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.eclipse.gemini.dbaccess.postgresql;

import java.io.PrintWriter;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Driver;

import static org.osgi.service.jdbc.DataSourceFactory.*;

/**
 * An abbreviated/simplified DataSource impl that takes a URL from the client
 * and just returns a thin data source wrapper around the basic JDBC driver.
 */
class UrlBasedDriverDataSource implements javax.sql.DataSource {

	Driver driver;
	Properties properties = null;
	String url = null;

	/**
	 * @param properties
	 *            The properties to use for operations on the driver
	 */
	public UrlBasedDriverDataSource(Properties properties) {

		this.driver = new org.postgresql.Driver();
		this.properties = (Properties) properties.clone();
		this.url = properties.getProperty(JDBC_URL);
	}

	public Connection getConnection() throws java.sql.SQLException {
		return driver.connect(url, properties);
	}

	public Connection getConnection(String user, String password)
			throws java.sql.SQLException {
		Properties localProps = (Properties) properties.clone();
		localProps.put(JDBC_USER, user);
		localProps.put(JDBC_PASSWORD, password);
		return driver.connect(url, localProps);
	}

	public boolean isWrapperFor(Class<?> cls) {
		return (cls == org.postgresql.Driver.class);
	}

	public <T> T unwrap(Class<T> cls) {
		return (isWrapperFor(cls)) ? (T) driver : null;
	}

	public PrintWriter getLogWriter() throws SQLException {
		return DriverManager.getLogWriter();
	}

	public int getLoginTimeout() throws SQLException {
		return DriverManager.getLoginTimeout();
	}

	// Don't support setting log writer or timeout

	public void setLogWriter(PrintWriter writer) throws SQLException {
		throw new SQLException("Can't set Log Writer on URL data source");
	}

	public void setLoginTimeout(int timeout) throws SQLException {
		throw new SQLException("Can't set Login Timeout on URL data source");
	}
}