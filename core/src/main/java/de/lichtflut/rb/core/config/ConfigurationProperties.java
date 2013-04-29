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

	@SuppressWarnings("unused")
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
