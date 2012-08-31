/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.lichtflut.rb.core.services.FileService;
import de.lichtflut.repository.ContentDescriptor;
import de.lichtflut.repository.RepositoryDelegator;
import de.lichtflut.repository.impl.ContentDescriptorBuilder;

/**
 * <p>
 * 
 * </p>
 * Created: Aug 5, 2012
 *
 * @author Ravi Knox
 */
@RunWith(MockitoJUnitRunner.class)
public class FileServiceImplTest {

	@Mock
	private RepositoryDelegator mockDelegator;

	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();

	private FileService fileService;

	private final String propertiesFilePath = "fileService/test-repo-config.properties";
	private final String path = "lichtflut/test/person/mueller";

	/**
	 * Test method for {@link de.lichtflut.rb.core.services.impl.FileServiceImpl#FileServiceImpl(java.lang.String)}.
	 */
	@Test
	public void testFileServiceImpl() {
		fileService = new FileServiceImpl(propertiesFilePath){
			@Override
			protected void initRepository(){
			}
		};
		assertThat(fileService, notNullValue());
	}

	/**
	 * Test method for {@link de.lichtflut.rb.core.services.impl.FileServiceImpl#getData(java.lang.String)}.
	 * @throws IOException
	 */
	@Test
	public void testGetData() throws IOException {
		File original = tempFolder.newFile("test");
		filllFile(original);
		InputStream in = new FileInputStream(original);
		ContentDescriptor descriptor = new ContentDescriptorBuilder().data(in).mimeType("properties").build();

		startMockRepositoryDelegator();
		when(mockDelegator.getData(path)).thenReturn(descriptor);

		fileService.getData(path);
		verify(mockDelegator, times(01)).getData(path);
		original.deleteOnExit();
	}

	/**
	 * Test method for {@link de.lichtflut.rb.core.services.impl.FileServiceImpl#storeFile(ContentDescriptor)}.
	 * @throws IOException
	 */
	@Test
	public void testStoreFile() throws IOException {
		File original = tempFolder.newFile("test.doc");
		ContentDescriptor descriptor = new ContentDescriptorBuilder().data(new FileInputStream(original)).build();
		filllFile(original);

		startMockRepositoryDelegator();

		fileService.storeFile(descriptor);
		original.deleteOnExit();
	}

	/**
	 * Test method for {@link de.lichtflut.rb.core.services.impl.FileServiceImpl#exists(java.lang.String)}.
	 */
	@Test
	public void testExists() {
		boolean expected = true;
		boolean exists = true;
		startMockRepositoryDelegator();

		when(mockDelegator.exists(path)).thenReturn(expected);
		exists = fileService.exists(path);

		verify(mockDelegator, times(1)).exists(path);

		assertThat(exists, equalTo(expected));
	}

	// ------------------------------------------------------

	private void startMockRepositoryDelegator() {
		mock(RepositoryDelegator.class);

		fileService = new FileServiceImpl(propertiesFilePath){
			@Override
			protected void initRepository(){
				this.delegator = mockDelegator;
			}
		};
	}


	/**
	 * @param file
	 * @throws IOException
	 * 
	 */
	private void filllFile(final File file) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		for (int i = 0; i < 10; i++) {
			bw.write(UUID.randomUUID().toString() + i);
		}
		bw.close();
	}

}
