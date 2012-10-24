/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.repository.impl;

import java.io.InputStream;
import java.io.Serializable;

import de.lichtflut.repository.ContentDescriptor;
import de.lichtflut.repository.Filetype;

/**
 * <p>
 * Builder for a {@link ContentDescriptor}.
 * </p>
 * Created: Jul 20, 2012
 * 
 * @author Ravi Knox
 */
public class ContentDescriptorBuilder implements Serializable {

	private String id = "";
	private Filetype filetype = Filetype.OTHER;
	private String name = "";
	private InputStream in = null;

	// ---------------- Constructor -------------------------

	/**
	 * Default Constructor.
	 */
	public ContentDescriptorBuilder() {
	}

	// ------------------------------------------------------

	public ContentDescriptorBuilder id(final String id){
		this.id = id;
		return this;
	}

	public ContentDescriptorBuilder mimeType(final Filetype filetype){
		this.filetype = filetype;
		return this;
	}

	public ContentDescriptorBuilder data(final InputStream in){
		this.in = in;
		return this;
	}

	public ContentDescriptorBuilder name(final String name){
		this.name = name;
		return this;
	}

	// ------------------------------------------------------

	public ContentDescriptor build() {

		return new ContentDescriptor() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public String getID() {
				return id;
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public Filetype getMimeType() {
				return filetype;
			}

			@Override
			public InputStream getData() {
				return in;
			}

			@Override
			public String getName() {
				return name;
			}

			@Override
			public String toString(){
				StringBuilder sb = new StringBuilder();
				sb.append("MimeType: " + getMimeType() + "\n");
				if(null != getData()){
					sb.append("Data: " + getData().toString());
				} else{
					sb.append("Data: NO-DATA!");
				}
				return sb.toString();
			}
		};
	}
}
