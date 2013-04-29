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
 *  Configuration interface for file service.
 * </p>
 *
 * <p>
 *  Created 09.11.12
 * </p>
 *
 * @author Oliver Tigges
 */
public class FileServiceConfiguration {

    private Class<FileServiceConfiguration> implementingClass;

    private Properties properties;

    // ----------------------------------------------------

    public FileServiceConfiguration() {
    }

    public FileServiceConfiguration(Class<FileServiceConfiguration> implementingClass, Properties properties) {
        this.implementingClass = implementingClass;
        this.properties = properties;
    }

    // ----------------------------------------------------

    public Class<FileServiceConfiguration> getImplementingClass() {
        return implementingClass;
    }

    public void setImplementingClass(Class<FileServiceConfiguration> implementingClass) {
        this.implementingClass = implementingClass;
    }

    public Properties getProperties() {
        return properties;
    }

    public String getProperty(String key) {
        if (properties == null) {
            return null;
        }
        return properties.getProperty(key);
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    // ----------------------------------------------------

    @Override
    public String toString() {
        return "FileServiceConfiguration{" +
                "implementingClass=" + implementingClass +
                ", properties=" + properties +
                '}';
    }

}
