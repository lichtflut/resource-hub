/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.repository.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import de.lichtflut.repository.ContentDescriptor;


/**
 * <p>
 * Test for {@link ContentDescriptorBuilder}.
 * </p>
 * Created: Jul 20, 2012
 *
 * @author Ravi Knox
 */
public class ContentDescriptorBuilderTest {

	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();

	@Test
	public void testContentDesriptorBuilder() throws IOException{
		String name = "test.jpg";
		String path = "test/folder/1/test.jpg";
		String mimeType = "jpeg";
		InputStream in = new FileInputStream(tempFolder.newFile());

		ContentDescriptor descriptor = new ContentDescriptorBuilder().name(name).path(path).mimeType(mimeType).data(in).build();
		assertThat(descriptor.getMimeType(), equalTo(mimeType));
		assertThat(descriptor.getName(), equalTo(name));
		assertThat(descriptor.getPath(), equalTo(path));
		assertThat(descriptor.getData(), equalTo(in));
	}

}
