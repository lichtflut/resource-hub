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
