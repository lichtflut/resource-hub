/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models.fields;

import java.io.IOException;
import java.util.List;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.core.services.EntityManager;
import de.lichtflut.rb.core.services.FileService;
import de.lichtflut.rb.core.services.impl.FileServiceImpl;
import de.lichtflut.rb.core.services.impl.LinkProvider;
import de.lichtflut.rb.webck.common.RBWebSession;
import de.lichtflut.rb.webck.components.fields.AjaxEditableUploadField;
import de.lichtflut.repository.ContentDescriptor;
import de.lichtflut.repository.impl.ContentDescriptorBuilder;

/**
 * <p>
 * Model for {@link AjaxEditableUploadField}.
 * </p>
 * Created: Sep 10, 2012
 *
 * @author Ravi Knox
 */
public class FileUploadModel implements IModel<Object>{

	@SpringBean
	private FileService fileService;

	@SpringBean
	private EntityManager entityManager;

	private final ResourceID rbField;
	private final IModel<Object> original;

	// ---------------- Constructor -------------------------

	public FileUploadModel(final IModel<Object> model, final RBField rbField){
		this.original = model;
		this.rbField = rbField.getPredicate();
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
		return FileServiceImpl.getSimpleName(original.getObject().toString());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setObject(final Object object) {
		if(null != object){
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
		return descriptor.getPath();
	}

	private ContentDescriptor buildContentDescriptorFor(final FileUpload upload) {
		ResourceID id = RBWebSession.get().getHistory().getCurrentStep().getHandle().getId();
		RBEntity entity = entityManager.find(id);
		String path = LinkProvider.buildRepositoryStructureFor(entity, rbField.getQualifiedName(), upload.getClientFileName());
		ContentDescriptor descriptor;
		try {
			descriptor = new ContentDescriptorBuilder().name(upload.getClientFileName())
					.mimeType(upload.getContentType()).path(path).data(upload.getInputStream()).build();
		} catch (IOException e) {
			throw new IllegalStateException("Error while getting InputStream", e);
		}
		return descriptor;
	}

}
