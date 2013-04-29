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

import org.arastreju.sge.ArastrejuProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * <p>
 *  Configuration of the datastore to be used by an RB Application.
 * </p>
 *
 * <p>
 *  Created 26.10.12
 * </p>
 *
 * @author Oliver Tigges
 */
public class DataStoreConfiguration {

    public static final String VIRTUAL_DOMAINS = "de.lichtflut.rb.virtual-domains";

    // ----------------------------------------------------

    private final static Logger LOGGER = LoggerFactory.getLogger(DataStoreConfiguration.class);

    // ----------------------------------------------------

    private final File workDirectory;

    private final String profileName;

    /**
     * Profile.
     */
    private ArastrejuProfile profile;

    // -----------------------------------------------------

    /**
     * Creates a new data store config object.
     */
    public DataStoreConfiguration(File workDirectory, String profileName) {
        this.workDirectory = workDirectory;
        this.profileName = profileName;
    }

    // -----------------------------------------------------

    /**
     * @return The Arastreju configuration properties.
     */
    public ArastrejuProfile getArastrejuProfile(){
        if (profile == null) {
            initProfile();
        }
        return profile;
    }

    // ----------------------------------------------------

    @Override
    public String toString() {
        return "DataStoreConfiguration('" +  profileName + "')";
    }

    // -----------------------------------------------------

    /**
     * Initializes a profile.
     */
    private void initProfile(){
        if (profileName == null) {
            LOGGER.info("Initialising Arastreju default profile");
            profile = ArastrejuProfile.read();
        } else {
            LOGGER.info("Initialising Arastreju profile with name " + profileName);
            profile = ArastrejuProfile.read(profileName);
        }
        checkWorkDir();
        checkVirtualDomains();
    }

    private void checkWorkDir() {
        if (workDirectory != null) {
            LOGGER.info("Using work directory {} for profile {}.", workDirectory, profileName);
            profile.setProperty(ArastrejuProfile.ARAS_STORE_DIRECTORY, workDirectory.getAbsolutePath());
        } else {
            LOGGER.info("Using default directory for profile {}.", profileName);
        }
    }

    private void checkVirtualDomains() {
        String vd = System.getProperty(VIRTUAL_DOMAINS, "off");
        if ("default".equals(vd)) {
            LOGGER.info("Enabling virtual domains for profile {}. ", profileName);
            profile.setProperty(ArastrejuProfile.ENABLE_VIRTUAL_DOMAINS, "yes");
        }
    }

}
