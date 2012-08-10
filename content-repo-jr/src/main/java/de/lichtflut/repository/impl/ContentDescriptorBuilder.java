/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.repository.impl;

import java.io.InputStream;

import de.lichtflut.repository.ContentDescriptor;

/**
 * <p>
 * Builder for a {@link ContentDescriptor}.
 * </p>
 * Created: Jul 20, 2012
 * 
 * @author Ravi Knox
 */
public class ContentDescriptorBuilder {

	private String name = "";
	private String path = "";
	private String mimeType = "";
	private InputStream in = null;

	// ---------------- Constructor -------------------------

	/**
	 * Default Constructor.
	 */
	public ContentDescriptorBuilder() {
	}

	// ------------------------------------------------------

	public ContentDescriptorBuilder name(final String name){
		this.name = name;
		return this;
	}

	public ContentDescriptorBuilder path(final String path){
		this.path= path;
		return this;
	}

	public ContentDescriptorBuilder mimeType(final String mimeType){
		this.mimeType = mimeType;
		return this;
	}

	public ContentDescriptorBuilder data(final InputStream in){
		this.in = in;
		return this;
	}

	// ------------------------------------------------------

	public ContentDescriptor build() {

		return new ContentDescriptor() {

			/**
			 * {@inheritDoc}
			 */
			@Override
			public String getPath() {
				return path;
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public String getName() {
				return name;
			}

			/**
			 * {@inheritDoc}
			 */
			@Override
			public String getMimeType() {
				return mimeType;
			}

			@Override
			public InputStream getData() {
				return in;
			}

			@Override
			public String toString(){
				StringBuilder sb = new StringBuilder();
				sb.append("Name: " + getName() + "\n");
				sb.append("MimeType: " + getMimeType() + "\n");
				sb.append("Data: " + getData().toString());
				return sb.toString();
			}
		};
	}
}
