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

package org.eclipse.gemini.dbaccess.mysql;

import java.util.Properties;

import java.sql.Driver;
import java.sql.SQLException;

import javax.sql.DataSource;
import javax.sql.ConnectionPoolDataSource;
import javax.sql.XADataSource;

import org.osgi.service.jdbc.DataSourceFactory;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;

/**
 * A factory for creating MySQL network data sources. The properties specified
 * in the create methods determine how the created object is configured.
 * 
 * Sample code for obtaining a MySQL network data source:
 * 
 * ServiceTracker tracker = new ServiceTracker(context,
 * DataSourceFactory.class.getName(), null); tracker.open(); DataSourceFactory
 * dsf = (DataSourceFactory) tracker.getService(); Properties props = new
 * Properties(); props.put(DataSourceFactory.JDBC_SERVER_NAME, "localhost");
 * props.put(DataSourceFactory.JDBC_PORT_NUMBER, "3306");
 * props.put(DataSourceFactory.JDBC_DATABASE_NAME, "myDB");
 * props.put(DataSourceFactory.JDBC_USER, "mike");
 * props.put(DataSourceFactory.JDBC_PASSWORD, "password"); DataSource ds =
 * dsf.createDataSource(props);
 * 
 * This service also supports a URL-based data source. The following 3
 * properties can be provided instead of the 5 properties above:
 * 
 * props.put(DataSourceFactory.JDBC_URL, "jdbc:mysql://localhost:3306/myDB");
 * props.put(DataSourceFactory.JDBC_USER, "mike");
 * props.put(DataSourceFactory.JDBC_PASSWORD, "password");
 */
public class ClientDataSourceFactory extends AbstractDataSourceFactory {

	public ClientDataSourceFactory() {
	}

	/**
	 * Create a PostgreSQL DataSource object.
	 * 
	 * @param props
	 *            The properties that define the DataSource implementation to
	 *            create and how the DataSource is configured.
	 * @return The configured DataSource.
	 * @throws SQLException
	 * @see org.osgi.service.jdbc.DataSourceFactory#createDataSource(java.util.Properties)
	 */
	public DataSource createDataSource(Properties props) throws SQLException {
		if (props == null)
			props = new Properties();
		if (props.get(DataSourceFactory.JDBC_URL) != null) {
			return new UrlBasedDriverDataSource(props);
		} else {
			DataSource dataSource = new MysqlDataSource();

			setDataSourceProperties(dataSource, props);
			return dataSource;
		}
	}

	/**
	 * Create a PostgreSQL ConnectionPoolDataSource object.
	 * 
	 * @param props
	 *            The properties that define the ConnectionPoolDataSource
	 *            implementation to create and how the ConnectionPoolDataSource
	 *            is configured.
	 * @return The configured ConnectionPoolDataSource.
	 * @throws SQLException
	 * @see org.osgi.service.jdbc.DataSourceFactory#createConnectionPoolDataSource(java.util.Properties)
	 */
	public ConnectionPoolDataSource createConnectionPoolDataSource(
			Properties props) throws SQLException {
		if (props == null)
			props = new Properties();
		ConnectionPoolDataSource dataSource = new MysqlConnectionPoolDataSource();

		setDataSourceProperties(dataSource, props);
		return dataSource;
	}

	/**
	 * Create a PostgreSQL XADataSource object.
	 * 
	 * @param props
	 *            The properties that define the XADataSource implementation to
	 *            create and how the XADataSource is configured.
	 * @return The configured XADataSource.
	 * @throws SQLException
	 * @see org.osgi.service.jdbc.DataSourceFactory#createXADataSource(java.util.Properties)
	 */
	public XADataSource createXADataSource(Properties props)
			throws SQLException {
		if (props == null)
			props = new Properties();
		XADataSource dataSource = new MysqlXADataSource();

		setDataSourceProperties(dataSource, props);
		return dataSource;
	}

	/**
	 * Create a new org.postgresql.Driver.
	 * 
	 * @param props
	 *            The properties used to configure the Driver. Null indicates no
	 *            properties. If the property cannot be set on the Driver being
	 *            created then a SQLException must be thrown.
	 * @return A configured org.apache.derby.jdbc.EmbeddedDriver.
	 * @throws SQLException
	 *             If the org.postgresql.Driver cannot be created.
	 */
	public Driver createDriver(Properties props) throws SQLException {
		// Properties not used when accessing the raw driver.
		Driver driver = new com.mysql.jdbc.Driver();
		setDataSourceProperties(driver, props);
		return driver;
	}
}