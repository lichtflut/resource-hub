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
package de.lichtflut.rb.core.content;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

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
		Filetype filetype = Filetype.JPEG;
		InputStream in = new FileInputStream(tempFolder.newFile());

		ContentDescriptor descriptor = new ContentDescriptorBuilder().name(name).id(path).mimeType(filetype).data(in).build();
		assertThat(descriptor.getMimeType(), equalTo(filetype));
		assertThat(descriptor.getName(), equalTo(name));
		assertThat(descriptor.getID(), equalTo(path));
		assertThat(descriptor.getData(), equalTo(in));
	}

}
