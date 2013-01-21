package de.lichtflut.rb.core.config;

import java.util.Properties;

/**
 * <p>
 *  Extension of java.util.Properties useful for application configuration. The Properties defined here
 *  may be overridden by system properties.
 * </p>
 *
 * <p>
 *  Created 02.11.12
 * </p>
 *
 * @author Oliver Tigges
 */
public class ConfigurationProperties extends Properties {

	private final String prefix;

	// ----------------------------------------------------

	public ConfigurationProperties() {
		this("");
	}

	public ConfigurationProperties(final String prefix) {
		this.prefix = prefix;
		checkSystemProperties(prefix);
	}

	public ConfigurationProperties(final Properties properties) {
		this("", properties);
	}

	public ConfigurationProperties(final String prefix, final Properties properties) {
		this.prefix = prefix;
		putAll(properties);
		checkSystemProperties(prefix);
	}

	// ----------------------------------------------------

	private void checkSystemProperties(final String prefix) {
		Properties sysProperties = System.getProperties();
		for (String key : sysProperties.stringPropertyNames()) {
			if (key.startsWith(prefix)) {
				String newKey = key.substring(key.length());
				if (newKey.startsWith(".")) {
					newKey = newKey.substring(1);
				}
				setProperty(newKey, sysProperties.getProperty(key));
			}
		}
	}


}
