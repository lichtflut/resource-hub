/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.config;

import de.lichtflut.rb.core.eh.ConfigurationException;
import de.lichtflut.rb.core.security.SecurityConfiguration;
import de.lichtflut.rb.core.services.DomainValidator;
import org.arastreju.sge.ArastrejuProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * <p>
 *  Basic configuration class for an RB application.
 * </p>
 *
 * <p>
 * 	Created May 26, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class RBConfig {

	/**
	 * Arastreju store directory.
	 */
	public static final String DOMAIN_WORK_DIRECTORY = "de.lichtflut.rb.workdir";

    // ----------------------------------------------------

    private final static Logger LOGGER = LoggerFactory.getLogger(RBConfig.class);

	// ----------------------------------------------------

	private final String appName;

    private final File workDir;

    private DataStoreConfiguration dataStoreConfig;

    private EmailConfiguration emailConfiguration;

    private SecurityConfiguration securityConfiguration;

    private DomainValidator domainValidator;

	// -----------------------------------------------------

	/**
	 * Creates a new configuration object for an RB application with the given name.
     * There is an Arastreju configuration file with the name {appName}.profile expected
     * to be in the classpath.
	 * @param appName The application name.
	 */
	public RBConfig(final String appName) throws ConfigurationException {
		this.appName = appName;
        this.workDir = checkWorkDir(appName);
        this.dataStoreConfig = new DataStoreConfiguration(workDir, appName);
        LOGGER.info("Initialized {}", this);
    }

	// -----------------------------------------------------

    /**
	 * @return The Arastreju configuration properties.
	 */
	public ArastrejuProfile getArastrejuProfile(){
		return dataStoreConfig.getArastrejuProfile();
	}

	/**
	 * @return the work directory for this profile
	 */
	public String getWorkDirectory() {
		return workDir.getAbsolutePath();
	}

    public DataStoreConfiguration getDataStoreConfiguration() {
        return dataStoreConfig;
    }

    public EmailConfiguration getEmailConfiguration() {
        return emailConfiguration;
    }

    public SecurityConfiguration getSecurityConfiguration() {
        return securityConfiguration;
    }

    public DataStoreConfiguration getDataStoreConfig() {
        return dataStoreConfig;
    }

    public void setDataStoreConfig(DataStoreConfiguration dataStoreConfig) {
        this.dataStoreConfig = dataStoreConfig;
    }

    public DomainValidator getDomainValidator() {
        return domainValidator;
    }

    // ----------------------------------------------------

    public void setEmailConfiguration(EmailConfiguration emailConfiguration) {
        this.emailConfiguration = emailConfiguration;
    }

    public void setSecurityConfiguration(SecurityConfiguration securityConfiguration) {
        this.securityConfiguration = securityConfiguration;
    }

    public void setDomainValidator(DomainValidator domainValidator) {
        this.domainValidator = domainValidator;
    }

    // ----------------------------------------------------

	@Override
	public String toString() {
        StringBuilder sb = new StringBuilder("RBConfig");
        sb.append(" for application '").append(appName).append("'");
        sb.append(" using work dir '").append(workDir).append("'");
        sb.append("\n");
        sb.append(dataStoreConfig.toString());
		return sb.toString();
	}

	// -----------------------------------------------------

    private File checkWorkDir(String applicationName) throws ConfigurationException {
        String workDirPath = determineWorkDir(applicationName);
        File workDir = new File(workDirPath);
        if (!workDir.isDirectory() &&  !workDir.mkdirs()) {
            throw new ConfigurationException("Work dir does not exist and can not be created: " + workDirPath);
        }
        return  workDir;
    }

    private String determineWorkDir(String applicationName) {
        // 1st: check profile specific work directory
        if (applicationName != null) {
            final String workDir = System.getProperty(DOMAIN_WORK_DIRECTORY + "." + applicationName);
            if (workDir != null) {
                return workDir;
            }
        }
        // 2nd: check global work directory
        return System.getProperty(DOMAIN_WORK_DIRECTORY);
    }

}
