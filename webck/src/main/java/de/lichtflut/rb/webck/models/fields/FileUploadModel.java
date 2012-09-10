/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models.fields;

import java.io.IOException;
import java.util.List;

import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import de.lichtflut.repository.ContentDescriptor;
import de.lichtflut.repository.impl.ContentDescriptorBuilder;

/**
 * <p>
 * [TODO Insert description here.]
 * </p>
 * Created: Sep 10, 2012
 *
 * @author Ravi Knox
 */
public class FileUploadModel implements IModel<Object>{

	private final IModel<ContentDescriptor> original;

	// ---------------- Constructor -------------------------

	public FileUploadModel(final IModel<Object> model){
		if(null != model.getObject()){
			original = new Model<ContentDescriptor>(new ContentDescriptorBuilder().name(model.getObject().toString()).build());
		}else{
			original = new Model<ContentDescriptor>(new ContentDescriptorBuilder().build());
		}
		model.setObject(original);
	}

	// ------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getObject() {
		return original.getObject().getName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setObject(final Object object) {
		if(null != object){
			@SuppressWarnings("unchecked")
			FileUpload upload = ((List<FileUpload>) object).get(0);
			ContentDescriptor descriptor = null;
			try {
				descriptor = new ContentDescriptorBuilder().name(upload.getClientFileName()).data(upload.getInputStream()).mimeType(upload.getContentType()).path(upload.getClientFileName()).build();
			} catch (IOException e) {
				throw new IllegalStateException("Error while getting InputStream", e);
			}
			this.original.setObject(descriptor);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void detach() {

	}

}
