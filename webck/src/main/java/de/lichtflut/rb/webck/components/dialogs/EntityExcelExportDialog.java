/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.dialogs;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.wicket.IResourceListener;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.ContentDisposition;
import org.apache.wicket.request.resource.IResource.Attributes;
import org.apache.wicket.request.resource.ResourceStreamResource;
import org.apache.wicket.util.lang.Bytes;
import org.apache.wicket.util.resource.AbstractResourceStream;
import org.apache.wicket.util.resource.ResourceStreamNotFoundException;

import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.services.ExcelExporter;
import de.lichtflut.rb.core.services.impl.EntityExcelExporter;

/**
 * <p>
 *  Modal dialog for Entity-export to an excel-sheet.
 * </p>
 *
 * <p>
 * 	Created 01.03.2012
 * </p>
 *
 * @author Erik Aderhold
 */
public class EntityExcelExportDialog extends AbstractRBDialog implements IResourceListener {

	private final ResourceStreamResource resource;

	private IModel<RBEntity> entityModel;
	
	// ----------------------------------------------------

	/**
	 * @param id
	 */
	@SuppressWarnings("rawtypes")
	public EntityExcelExportDialog(final String id, final IModel<RBEntity> entityModel) {
		super(id);

		this.entityModel = entityModel;
		
		resource = prepareResource();
		
		final Form form = new Form("form");
		
		form.add(new AjaxButton("exportButton", form) {
			@Override
			protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
				final String uid = "&uid" + System.nanoTime();
				target.appendJavaScript("window.location.href='" +  getDownloadUrl() + uid + "'");
				close(target);
			}
			@Override
			protected void onError(final AjaxRequestTarget target, final Form<?> form) {
				target.add(form);
			}
			
		});
		
		add(form); 
		
		setModal(true);
	}

	// -- IResourceListener -------------------------------

	/** 
	* {@inheritDoc}
	*/
	@Override
	public void onResourceRequested() {
		final RequestCycle cycle = RequestCycle.get();
		final Attributes a = new Attributes(cycle.getRequest(), cycle.getResponse(), null);
		resource.setFileName(getFilename());
		resource.respond(a);
	}
	
	// ----------------------------------------------------
	
	protected CharSequence getDownloadUrl() {
		return urlFor(IResourceListener.INTERFACE, new PageParameters());
	}
	
	protected ResourceStreamResource prepareResource() {
		 final ResourceStreamResource resource = new ResourceStreamResource(new ContentStream());
		 resource.setContentDisposition(ContentDisposition.ATTACHMENT);
		 return resource;
	}
	
	private String getFilename() {
		final StringBuilder sb = new StringBuilder();
		sb.append("export-");
		sb.append(System.currentTimeMillis());
		sb.append(".xls");
		return sb.toString();
	}
	
	// ----------------------------------------------------
	
	class ContentStream extends AbstractResourceStream {

		private transient InputStream in;
		private Bytes length;

		/** 
		* {@inheritDoc}
		*/
		@Override
		public InputStream getInputStream() throws ResourceStreamNotFoundException {
			final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			try {
				ExcelExporter exporter = new EntityExcelExporter(entityModel.getObject().getNode(), getLocale());
				exporter.export(buffer);
				
				length = Bytes.bytes(buffer.size());
				in = new ByteArrayInputStream(buffer.toByteArray());
				buffer.close();
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
		
	}
}