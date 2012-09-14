/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.repository.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.nodetype.NodeType;

import org.apache.jackrabbit.core.TransientRepository;
import org.apache.jackrabbit.core.fs.local.FileUtil;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.lichtflut.repository.ContentDescriptor;
import de.lichtflut.repository.Filetype;
import de.lichtflut.repository.RepositoryDelegator;

/**
 * <p>
 * Testclass for {@link RepositoryDelegatorImpl}.
 * </p>
 * Created: Jul 12, 2012
 * 
 * @author Ravi Knox
 */
public class RepositoryDelegatorImplTest {

	private static Repository repository;

	private static RepositoryDelegator repoDelegator;
	private Session session;

	private final static String user ="username";
	private final static String pwd = "pwd";

	private File retrieved;

	// -------------- Test ----------------------------------

	/**
	 * Test method for {@link de.lichtflut.repository.impl.RepositoryImpl#getData(java.lang.String)}
	 * .
	 */
	@Test
	public void testGetData() throws RepositoryException, IOException {
		String fileName = "test-content";
		String path = "lichtflut1/common/person/alice/hasDocument/" + fileName;
		Filetype filetype = Filetype.TXT;
		File file = new File("src/test/resources/" + fileName + "." + filetype);
		InputStream in = new FileInputStream(file);

		ContentDescriptor descriptor = new ContentDescriptorBuilder().path(path).mimeType(filetype).data(in).build();
		startSession();
		putFileInRepo(descriptor, session.getRootNode());

		ContentDescriptor retrievedDescriptor = repoDelegator.getData(path);
		retrieved = getFileFromStream(retrievedDescriptor.getData());

		assertThat(retrieved, notNullValue());
		assertThat(retrieved.length(), equalTo(file.length()));

		assertThat(retrievedDescriptor.getName(), equalTo(fileName));
		assertThat(retrievedDescriptor.getMimeType(), equalTo(filetype));
		assertThat(retrievedDescriptor.getPath(), equalTo(path));
	}

	/**
	 * Test method for
	 * {@link de.lichtflut.repository.impl.RepositoryImpl#storeFile(java.lang.String)}.
	 */
	@Test
	public void testStoreFile() throws IOException, RepositoryException {
		String fileName = "test-content.txt";
		String path = "lichtflut2/common/person/alice/hasDocument/" + fileName;
		Filetype filetype = Filetype.PNG;
		File file = new File("src/test/resources/" + fileName);
		InputStream in = new FileInputStream(file);

		assertThat(file, notNullValue());
		assertThat(repoDelegator, notNullValue());

		repoDelegator.storeFile(new ContentDescriptorBuilder().path(path).mimeType(filetype).data(in).build());
		startSession();

		Node node = session.getRootNode().getNode(path);
		assertThat(node, notNullValue());
		Node resNode = node.getNode(RepositoryDelegatorImpl.CONTENT_NODE).getNode(Node.JCR_CONTENT);

		InputStream retrievedStream = resNode.getProperty(Property.JCR_DATA).getBinary().getStream();
		retrieved = getFileFromStream(retrievedStream);

		assertThat(node.getName(), equalTo(fileName));
		assertThat(resNode.getProperty(Property.JCR_MIMETYPE).getString(), equalTo(filetype.name()));
		assertThat(retrieved.length(), is(file.length()));
	}

	/**
	 * Test method for {@link de.lichtflut.repository.impl.RepositoryImpl#exists(java.lang.String)}.
	 */
	@Test
	public void testExists() throws FileNotFoundException, LoginException, RepositoryException {
		String path = "lichtflut3/common/person/alice/hasDocument/file3.txt";
		String name = "test-content";
		Filetype filetype = Filetype.TXT;
		boolean exists = repoDelegator.exists(path);

		assertThat(exists, equalTo(false));

		File file = new File("src/test/resources/" + name + "." + filetype.name().toLowerCase());
		InputStream in = new FileInputStream(file);

		ContentDescriptor descriptor = new ContentDescriptorBuilder().path(path).mimeType(filetype).data(in).build();
		startSession();
		putFileInRepo(descriptor, session.getRootNode());

		exists = repoDelegator.exists(path);

		assertThat(exists, equalTo(true));
	}

	// ------------- SetUp & TearDown -----------------------

	@BeforeClass
	public static void setUpBeforeClass() {
		initRepository();
	}

	@AfterClass
	public static void tearDownAfterClass() throws IOException {
		((TransientRepository) repository).shutdown();
		FileUtil.delete(new File("./target/testdata/jackrabbittest"));
	}

	@Before
	public void setUp() throws Exception {
		startSession();
	}

	@After
	public void tearDown() throws Exception {
		if (session != null && session.isLive()) {
			try {
				session.save();
				session.logout();
			} finally {
				session = null;
			}
		}
		if (retrieved != null) {
			retrieved.delete();
		}
	}

	// ------------------------------------------------------

	/**
	 * Initializes a Repository.
	 * @param cs
	 * @param string
	 */
	private static void initRepository() {
		repository = new TransientRepository("./src/test/resources/repository.xml", "./target");
		repoDelegator = new RepositoryDelegatorImpl(user, pwd) {
			@Override
			protected Repository getRepository() {
				return repository;
			}

			@Override
			protected void closeSession() {
				// do not close
			}
		};
	}

	private void startSession() throws LoginException, RepositoryException {
		session = repository.login(new SimpleCredentials(user, pwd.toCharArray()));
	}

	private void putFileInRepo(final ContentDescriptor descriptor, final Node parent) {
		List<String> fragments = Arrays.asList(descriptor.getPath().split("/"));
		Node child = null;
		try {
			child = parent.addNode(fragments.get(0));
			for (int i = 1; i < fragments.size(); i++) {
				child = child.addNode(fragments.get(i), NodeType.NT_FOLDER);
			}
			Node fileNode = child.addNode(RepositoryDelegatorImpl.CONTENT_NODE, NodeType.NT_FILE);

			Node resNode = fileNode.addNode(Property.JCR_CONTENT, NodeType.NT_RESOURCE);
			resNode.setProperty(Property.JCR_MIMETYPE, descriptor.getMimeType().name());
			resNode.setProperty(Property.JCR_DATA, parent.getSession().getValueFactory().createBinary(descriptor.getData()));
			resNode.setProperty(Property.JCR_LAST_MODIFIED, Calendar.getInstance().getTimeInMillis());
			session.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private File getFileFromStream(final InputStream inputStream) throws IOException, FileNotFoundException {
		retrieved = new File("./target/testdata/jackrabbittest/" + UUID.randomUUID().toString());
		// write the inputStream to a FileOutputStream
		OutputStream out = new FileOutputStream(retrieved);

		int read = 0;
		byte[] bytes = new byte[1024];

		while ((read = inputStream.read(bytes)) != -1) {
			out.write(bytes, 0, read);
		}

		inputStream.close();
		out.flush();
		out.close();
		return retrieved;
	}
}
