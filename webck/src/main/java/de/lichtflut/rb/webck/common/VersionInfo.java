/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.webck.models.infra.VersionInfoModel;

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
