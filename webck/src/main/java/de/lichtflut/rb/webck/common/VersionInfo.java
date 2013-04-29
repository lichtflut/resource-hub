/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.lichtflut.rb.webck.common;

import de.lichtflut.rb.webck.models.infra.VersionInfoModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * <p>
 *  Information about the current version, read from "buildinfo.properties".
 * </p>
 *
 * <p>
 * 	Created Mar 2, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class VersionInfo {
	
	private static final Logger logger = LoggerFactory.getLogger(VersionInfoModel.class);

	private final Properties properties = new Properties();
	
	// ----------------------------------------------------
	
	/**
	 * Constructor that reads version info from file in classpath.
	 */
	public VersionInfo() {
		final ClassLoader cl = Thread.currentThread().getContextClassLoader();
		final InputStream in = cl.getResourceAsStream("buildinfo.properties");
		if (in != null) {
			try {
				properties.load(in);
				in.close();
			} catch (IOException e) {
				logger.error("Could not read version info.", e);
			}	
		} else {
			logger.warn("File 'buildinfo.properties' not found.");
		}
	}
	
	// ----------------------------------------------------
	
	/**
	 * @return the buildTimestamp
	 */
	public String getBuildTimestamp() {
		return properties.getProperty("build.timestamp", "unknown");
	}
	
	/**
	 * @return the version
	 */
	public String getVersion() {
		return properties.getProperty("version", "unknown");
	}

}
