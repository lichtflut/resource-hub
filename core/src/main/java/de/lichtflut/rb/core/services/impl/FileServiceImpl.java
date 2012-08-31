/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.UUID;

import javax.jcr.AccessDeniedException;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.UnsupportedRepositoryOperationException;
import javax.jcr.security.AccessControlManager;
import javax.jcr.security.Privilege;

import org.apache.jackrabbit.api.JackrabbitSession;
import org.apache.jackrabbit.api.security.user.Authorizable;
import org.apache.jackrabbit.api.security.user.Group;
import org.apache.jackrabbit.api.security.user.User;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.jackrabbit.commons.jackrabbit.authorization.AccessControlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.core.services.FileService;
import de.lichtflut.repository.ContentDescriptor;
import de.lichtflut.repository.RepositoryDelegator;
import de.lichtflut.repository.impl.RepositoryConfigWrapper;
import de.lichtflut.repository.impl.RepositoryDelegatorImpl;

/**
 * <p>
 * Implementation for {@link FileService}
 * </p>
 * Created: Aug 3, 2012
 * 
 * @author Ravi Knox
 */
public class FileServiceImpl implements FileService {

	private final static Logger LOGGER = LoggerFactory.getLogger(FileServiceImpl.class);

	protected RepositoryDelegator delegator;

	private final Properties properties;

	// ---------------- Constructor -------------------------

	public FileServiceImpl(final String config) {
		properties = getPropertiesFile(config);
		if(null == delegator){
			initRepository();
		}
	}

	// ------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public InputStream getData(final String path) {
		LOGGER.info("Retrieving file for path: {}", path);
		return delegator.getData(path).getData();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void storeFile(final ContentDescriptor descriptor) {
		delegator.storeFile(descriptor);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean exists(final String path) {
		return delegator.exists(path);
	}

	// ------------------------------------------------------

	protected void initRepository() {
		delegator = new RepositoryDelegatorImpl(getProperty("username", "anonymous"), getProperty("password", "anonymous")) {

			@Override
			protected RepositoryConfigWrapper getConfig() {
				String home = getProperty("storage-location", "target");
				if (home == null || home.isEmpty()) {
					home = System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + UUID.randomUUID().toString();
				}
				String config = getProperty("config-file", "");
				return new RepositoryConfigWrapper(home, config);
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			protected void setUpUser(final Session session) {
				JackrabbitSession js = (JackrabbitSession) session;
				UserManager um;
				try {
					LOGGER.debug("Attempting to set up JackRabbit user...");
					um = js.getUserManager();

					Authorizable grp = um.getAuthorizable("lichtflut-rb");
					Group userGroup = null;
					if (grp == null) {
						userGroup = um.createGroup("lichtflut-rb");
					} else {
						userGroup = (Group) grp;
					}
					User user = um.createUser(getProperty("username", ""), getProperty("password", ""));
					if (user.getID().isEmpty()) {
						return;
					}
					userGroup.addMember(user);

					Node node = session.getNode("/");

					AccessControlManager acm = session.getAccessControlManager();

					Privilege[] privileges = null;
					privileges = new Privilege[] {
							acm.privilegeFromName(Privilege.JCR_ALL),
							acm.privilegeFromName(Privilege.JCR_READ),
							acm.privilegeFromName(Privilege.JCR_WRITE),
							acm.privilegeFromName(Privilege.JCR_ADD_CHILD_NODES),
					};

					AccessControlUtils.addAccessControlEntry(session, node.getPath(), user.getPrincipal(), privileges, true);

					session.save();
					LOGGER.debug("Successfully set up JackRabbit user...");
				} catch (AccessDeniedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedRepositoryOperationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RepositoryException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
	}

	// ------------------------------------------------------

	private String getProperty(final String key, final String defaultValue) {
		if (properties != null) {
			return properties.getProperty(key, defaultValue);
		}
		return "";
	}

	private Properties getPropertiesFile(final String config) {
		final Properties properties = new Properties();
		try {
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(config));
			LOGGER.debug("Propertyfile loaded from: {}", config);
		} catch (final FileNotFoundException e) {
			LOGGER.error("Could not find Propertiesfile from: {}", config);
			e.printStackTrace();
		} catch (final IOException e) {
			LOGGER.error("Error while loading file from: {}", config);
			e.printStackTrace();
		}
		return properties;
	}

}
