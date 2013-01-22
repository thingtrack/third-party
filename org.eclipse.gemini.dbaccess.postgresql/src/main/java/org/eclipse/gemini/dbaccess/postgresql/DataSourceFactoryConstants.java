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

/**
 * Contants for PostgreSQL data source factory registration.
 * 
 * @author carlos
 */
public class DataSourceFactoryConstants {

	// Register a service under each of the following driver class name
	public static final String POSTGRESQL_CLIENT_DRIVER_CLASS = "org.postgresql.Driver";

	// Register all Derby factory services under this driver name
	public static final String POSTGRESQL_DRIVER_NAME = "PostgreSQL";

	// Register under the JDBC version the driver supports
	public static final String JDBC_DRIVER_VERSION = "4.0";
}
