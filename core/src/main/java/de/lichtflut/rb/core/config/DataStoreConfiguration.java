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
