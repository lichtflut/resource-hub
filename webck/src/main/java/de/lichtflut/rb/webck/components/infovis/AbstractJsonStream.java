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
package de.lichtflut.rb.webck.components.infovis;

import org.apache.wicket.util.lang.Bytes;
import org.apache.wicket.util.resource.AbstractResourceStream;
import org.apache.wicket.util.resource.ResourceStreamNotFoundException;
import org.apache.wicket.util.time.Time;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

/**
 * <p>
 *  Abstract JSON stream.
 * </p>
 *
 * <p>
 * 	Created Feb 15, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class AbstractJsonStream extends AbstractResourceStream {
	
	private transient InputStream in;
	
	private Bytes length;
	
	// ----------------------------------------------------

	/**
	 * @param stream
	 */
	public AbstractJsonStream() {
		super();
	}
	
	// ----------------------------------------------------
	
	public abstract void write(OutputStreamWriter writer) throws IOException;
	
	// ----------------------------------------------------

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public InputStream getInputStream() throws ResourceStreamNotFoundException {
		final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		final OutputStreamWriter writer = new OutputStreamWriter(buffer);
		try {
			write(writer);
			writer.flush();
			length = Bytes.bytes(buffer.size());
			in = new ByteArrayInputStream(buffer.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return in;
	}
	
	/** 
	* {@inheritDoc}
	*/
	@Override
	public void close() throws IOException {
		in.close();
		in = null;
	}
	
	/** 
	* {@inheritDoc}
	*/
	@Override
	public Bytes length() {
		return length;
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public Time lastModifiedTime() {
		return Time.now();
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public String getContentType() {
		return "application/json";
	}
	
}
