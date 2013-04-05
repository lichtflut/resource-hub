/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models.fields;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.lichtflut.rb.core.services.EntityManager;
import de.lichtflut.rb.core.services.FileService;
import de.lichtflut.rb.core.services.impl.LinkProvider;
import de.lichtflut.rb.webck.components.fields.AjaxEditableUploadField;
import de.lichtflut.repository.ContentDescriptor;
import de.lichtflut.repository.Filetype;
import de.lichtflut.repository.impl.ContentDescriptorBuilder;

/**
 * <p>
 * Model for {@link AjaxEditableUploadField}.
 * </p>
 * Created: Sep 10, 2012
 *
 * @author Ravi Knox
 */
// TODO rethink data structure
public class FileUploadModel implements IModel<Object>{

	@SpringBean
	private FileService fileService;

	@SpringBean
	private EntityManager entityManager;

	private final IModel<Object> original;
	private final IModel<String> prefix;

	// ---------------- Constructor -------------------------

	public FileUploadModel(final IModel<Object> model, final IModel<String> pathPrefix){
		this.original = model;
		this.prefix = pathPrefix;
		Injector.get().inject(this);
	}

	// ------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getObject() {
		if(null == original.getObject()){
			return "";
		}
		return LinkProvider.getSimpleNameFor(original.getObject().toString());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setObject(final Object object) {
		if(object != null){
			@SuppressWarnings("unchecked")
			FileUpload upload = ((List<FileUpload>) object).get(0);
			String path = saveFileToRepository(upload);
			original.setObject(path);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void detach() {

	}

	// ------------------------------------------------------

	private String saveFileToRepository(final FileUpload upload) {
		ContentDescriptor descriptor = null;
		descriptor = buildContentDescriptorFor(upload);
		fileService.storeFile(descriptor);
		return descriptor.getID();
	}

	private ContentDescriptor buildContentDescriptorFor(final FileUpload upload) {
		// TODO added uuid to get unique path. Remove when data structure is clear.
		String random = UUID.randomUUID().toString();
		String path = random + prefix.getObject() + "/" + upload.getClientFileName();
		ContentDescriptor descriptor;
		try {
			Filetype filetype = getFiletype(upload);
			descriptor = new ContentDescriptorBuilder().name(upload.getClientFileName())
					.mimeType(filetype).id(path).data(upload.getInputStream()).build();
		} catch (IOException e) {
			throw new IllegalStateException("Error while getting InputStream", e);
		}
		return descriptor;
	}

	/**
	 * @param upload
	 * @return
	 */
	private Filetype getFiletype(final FileUpload upload) {
		String type = upload.getContentType();
		type = type.substring(type.lastIndexOf("/")+1);
		return Filetype.getCorrespondingFiletypeFor(type);
	}

}
