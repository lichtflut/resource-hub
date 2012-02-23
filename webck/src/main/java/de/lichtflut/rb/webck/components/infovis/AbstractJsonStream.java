/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.infovis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

import org.apache.wicket.util.lang.Bytes;
import org.apache.wicket.util.resource.AbstractResourceStream;
import org.apache.wicket.util.resource.ResourceStreamNotFoundException;
import org.apache.wicket.util.time.Time;

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
