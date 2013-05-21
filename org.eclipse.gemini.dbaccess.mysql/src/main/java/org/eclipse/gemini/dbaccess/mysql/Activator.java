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

import java.util.Hashtable;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.jdbc.DataSourceFactory;

/**
 * @author carlos
 * 
 */
public class Activator implements BundleActivator {

	private ServiceRegistration clientService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext context) throws Exception {

		System.out.println("Gemini DBAccess Mysql starting");

		Hashtable<String, String> props = new Hashtable<String, String>();
		props.put(DataSourceFactory.OSGI_JDBC_DRIVER_NAME,
				DataSourceFactoryConstants.MYSQL_DRIVER_NAME);

		/* === Register the JDBC 5 drivers === */
		props.put(DataSourceFactory.OSGI_JDBC_DRIVER_VERSION,
				DataSourceFactoryConstants.JDBC_DRIVER_VERSION);

		// Register the driver
		props.put(DataSourceFactory.OSGI_JDBC_DRIVER_CLASS,
				DataSourceFactoryConstants.MYSQL_CLIENT_DRIVER_CLASS);

		clientService = context.registerService(
				DataSourceFactory.class.getName(),
				new ClientDataSourceFactory(), props);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext arg0) throws Exception {

		if (clientService != null) {
			clientService.unregister();
		}

	}

}
