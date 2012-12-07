/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.repository.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.jcr.Binary;
import javax.jcr.Credentials;
import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.nodetype.NodeType;

import org.apache.jackrabbit.core.RepositoryImpl;
import org.apache.jackrabbit.core.config.ConfigurationException;
import org.apache.jackrabbit.core.config.RepositoryConfig;
import org.apache.jackrabbit.value.BinaryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.repository.ContentDescriptor;
import de.lichtflut.repository.Filetype;
import de.lichtflut.repository.RepositoryDelegator;

/**
 * <p>
 * Implementation of {@link RepositoryDelegator}.
 * </p>
 * Created: Jul 12, 2012
 * 
 * @author Ravi Knox
 */
public abstract class RepositoryDelegatorImpl implements RepositoryDelegator {

	protected static final String CONTENT_NODE = "lichtflut.content-repository.contentNode";
	protected static final String NODE_NAME = "lichtflut.content-repository.nodeName";

	private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryDelegator.class);

	private Session session;

	// ---------------- Constructor -------------------------

	public RepositoryDelegatorImpl(final String name, final String password) {
		if(null == session || session.isLive()){
			loginToRepository(new SimpleCredentials(name, password.toCharArray()));
			setUpUser(session);
		}
	}


	// ------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ContentDescriptor getData(final String id) {
		if(!exists(id)){
			return null;
		}
		Node node;
		InputStream retrievedStream = null;
		String mimeType = "";
		String name  = "";
		try {
			node = getRoot().getNode(id);
			Node resNode = node.getNode(CONTENT_NODE).getNode(Node.JCR_CONTENT);

			name = node.getName();
			mimeType = resNode.getProperty(Property.JCR_MIMETYPE).getString();
			Binary binary = resNode.getProperty(Property.JCR_DATA).getBinary();
			retrievedStream = binary.getStream();
			binary.dispose();
		} catch (PathNotFoundException e) {
			e.printStackTrace();
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
		LOGGER.info("Retrieved Data: {}", id);
		Filetype filetype = Filetype.getCorrespondingFiletypeFor(mimeType);
		return new ContentDescriptorBuilder().data(retrievedStream).id(id).mimeType(filetype).name(name).build();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void storeFile(final ContentDescriptor descriptor) {
		Node root = getRoot();
		try {
			Node childNode = cascadeChildNodes(descriptor.getID(), root);
			addFileToNode(descriptor, childNode);
			save();
			LOGGER.info("Stored File: {}", childNode.getPath());
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean exists(final String id) {
		if(null == id){
			return false;
		}
		try {
			return getRoot().hasNode(id);
		} catch (RepositoryException e) {
			LOGGER.error("Error while checking if id {} exists.", id);
			throwRuntimeException(e);
		}
		return false;
	}

	// ------------------------------------------------------

	/**
	 * @return the {@link RepositoryConfig}
	 */
	protected RepositoryConfigWrapper getConfig() {
		return null;
	}

	/**
	 * @return the Repository in which the files will be stored.
	 * @throws RepositoryException
	 */
	protected Repository getRepository() throws RepositoryException{
		try {
			InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(getConfig().getConfigPath());
			RepositoryConfig repoConfig = RepositoryConfig.create(in, getConfig().getPath());
			return RepositoryImpl.create(repoConfig);
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected void setUpUser(final Session session) {

	}

	/**
	 * Logs out from the current session. All unsaved data will be lost.
	 */
	protected void closeSession() {
		session.logout();
	}

	/**
	 * @param in - any {@link InputStream}
	 * @return {@link InputStream} as a {@link Binary}.
	 */
	protected Binary getAsBinary(final InputStream in) {
		Binary binary = null;
		try {
			binary = new BinaryImpl(in);
		} catch (IOException e) {
			LOGGER.error("Can not get Binary from Inputstream.");
			throwRuntimeException(e);
		}
		return binary;
	}


	// ------------------------------------------------------

	/**
	 * @param credentials - Login {@link Credentials}
	 */
	private void loginToRepository(final Credentials credentials) {
		try {
			session = getRepository().login(credentials);
		} catch (LoginException e) {
			LOGGER.error("Could not login to Repository.");
			throwRuntimeException(e);
		} catch (RepositoryException e) {
			LOGGER.error("Could not login to Repository.");
			throwRuntimeException(e);
		}
	}

	/**
	 * @return the root {@link Node}.
	 */
	private Node getRoot() {
		try {
			return session.getRootNode();
		} catch (RepositoryException e) {
			LOGGER.error("Could not retrieve root-node from Repository.");
			throwRuntimeException(e);
		}
		return null;
	}

	/**
	 * Add a {@link InputStream} to a {@link Node}.
	 * @param descriptor - {@link ContentDescriptor}
	 * @param in - {@link InputStream} to store
	 * @param parent - parent {@link Node}
	 */
	private void addFileToNode(final ContentDescriptor descriptor, final Node node) {
		try {
			if(node.hasNode(CONTENT_NODE)){
				node.getNode(CONTENT_NODE).remove();
			}
			Node fileNode = node.addNode(CONTENT_NODE, NodeType.NT_FILE);

			Node resNode = fileNode.addNode(Property.JCR_CONTENT, NodeType.NT_RESOURCE);
			resNode.setProperty(Property.JCR_MIMETYPE, descriptor.getMimeType().name());
			resNode.setProperty(Property.JCR_LAST_MODIFIED, Calendar.getInstance().getTimeInMillis());

			Binary binary = node.getSession().getValueFactory().createBinary(descriptor.getData());
			resNode.setProperty(Property.JCR_DATA, binary);
			binary.dispose();

		} catch (RepositoryException e) {
			LOGGER.error("Error while communicating with the Node");
			throwRuntimeException(e);
		}
	}

	/**
	 * @param path - a String that represents the hierarchy
	 * @param node - parent {@link Node}
	 * @return the bottom most child Node
	 */
	private Node cascadeChildNodes(final String path, final Node parent) {
		List<String> fragments = Arrays.asList(path.split("/"));
		Node node = parent;
		try {
			for (int index = 0; index < fragments.size(); index++) {
				if(!node.hasNode(fragments.get(index))){
					node = node.addNode(fragments.get(index), NodeType.NT_FOLDER);
				} else{
					node = node.getNode(fragments.get(index));
				}
			}
		} catch (Exception e) {
			LOGGER.error("Error while adding node {} to id {}.", node, path);
			throwRuntimeException(e);
		}
		return node;
	}

	/**
	 * Saves the current {@link Session}.
	 */
	private void save() {
		try {
			session.save();
		} catch (Exception e) {
			LOGGER.error("Error while saving current session.");
			throwRuntimeException(e);
		}
	}

	/**
	 * Throws a {@link RuntimeException}
	 * @param e - {@link Exception}
	 */
	private void throwRuntimeException(final Exception e) {
		throw new RuntimeException(e);
	}
}
