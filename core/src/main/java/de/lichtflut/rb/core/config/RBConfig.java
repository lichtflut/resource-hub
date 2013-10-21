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

import de.lichtflut.rb.core.eh.ConfigurationException;
import de.lichtflut.rb.core.system.DomainSupervisor;
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

    /**
     * Name of HTTP-Header field containing authenticated user name for single sign on.
     */
    public static final String TRUSTED_AUTH_HEADER = "de.lichtflut.rb.trusted-auth-header";

    // ----------------------------------------------------

    private final static Logger LOGGER = LoggerFactory.getLogger(RBConfig.class);

    // ----------------------------------------------------

	private final String appName;

    private final File workDir;

    private DataStoreConfiguration dataStoreConfig;

    private EmailConfiguration emailConfiguration;

    private FileServiceConfiguration fileServiceConfiguration;

    private DomainSupervisor domainSupervisor;

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

    public DataStoreConfiguration getDataStoreConfig() {
        return dataStoreConfig;
    }

    public FileServiceConfiguration getFileServiceConfiguration() {
        return fileServiceConfiguration;
    }

    public DomainSupervisor getDomainSupervisor() {
        return domainSupervisor;
    }

    // ----------------------------------------------------

    public void setEmailConfiguration(EmailConfiguration emailConfiguration) {
        this.emailConfiguration = emailConfiguration;
    }
    
    public void setDataStoreConfig(DataStoreConfiguration dataStoreConfig) {
        this.dataStoreConfig = dataStoreConfig;
    }

    public void setDomainSupervisor(DomainSupervisor domainSupervisor) {
        this.domainSupervisor = domainSupervisor;
    }

    public void setFileServiceConfiguration(FileServiceConfiguration fileServiceConfiguration) {
        this.fileServiceConfiguration = fileServiceConfiguration;
    }

    // ----------------------------------------------------

    public void ready() throws ConfigurationException {
        LOGGER.info("Initialized {}", this);
        if (domainSupervisor == null) {
            domainSupervisor = new DomainSupervisor();
        }
        domainSupervisor.init(this);
    }

    // ----------------------------------------------------

	@Override
	public String toString() {
        StringBuilder sb = new StringBuilder("RBConfig");
        sb.append(" for application '").append(appName).append("'");
        sb.append(" in current dir '").append(System.getProperty("user.dir")).append("'");
        sb.append(" using work dir '").append(workDir).append("'");
        sb.append("\n");
        sb.append(dataStoreConfig.toString());
        if (emailConfiguration != null) {
            sb.append("\n");
            sb.append(emailConfiguration.toString());
        }
        if (fileServiceConfiguration != null) {
            sb.append("\n");
            sb.append(fileServiceConfiguration.toString());
        }

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
        String workDir = getSystemProperty(
                DOMAIN_WORK_DIRECTORY + "." + applicationName,
                DOMAIN_WORK_DIRECTORY
        );
        if (workDir == null) {
            workDir = System.getProperty("java.io.tmpdir");
            LOGGER.warn("No domain working directory set. Will use java.io.tmpdir '{}'.", workDir);

        }
        return workDir;
    }

    private String getSystemProperty(String... keys) {
        for (String current : keys) {
            final String workDir = System.getProperty(current);
            if (workDir != null) {
                return workDir;
            }
        }
        return null;
    }

}
