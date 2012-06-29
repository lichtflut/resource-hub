/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.dialogs;

import de.lichtflut.rb.core.io.IOReport;
import de.lichtflut.rb.core.services.SchemaExporter;
import de.lichtflut.rb.core.services.SchemaManager;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.request.resource.ContentDisposition;
import org.apache.wicket.request.resource.ResourceStreamResource;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.resource.AbstractResourceStream;
import org.apache.wicket.util.resource.ResourceStreamNotFoundException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * <p>
 *  Modal dialog for exporting Schemas.
 * </p>
 *
 * <p>
 * 	Created Dec 13, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class SchemaExportDialog extends AbstractExportDialog {
	
	@SpringBean
	private SchemaManager schemaManager;
	
	private final IModel<String> format = new Model<String>("JSON");
	private IOReport report;
	
	// ----------------------------------------------------

	/**
	 * @param id
	 */
	@SuppressWarnings("rawtypes")
	public SchemaExportDialog(String id) {
		super(id);
		setModal(true);
		setWidth(600);
		
		final Form form = new Form("form");
		form.add(new FeedbackPanel("feedback"));
		
		form.add(new DropDownChoice<String>("format", format, getChoices()));
		
		form.add(new AjaxButton("exportButton", form) {
			@Override
			protected void onError(final AjaxRequestTarget target, final Form<?> form) {
				target.add(form);
			}
			@Override
			protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
				final String uid = "&uid" + System.nanoTime();
				target.appendJavaScript("window.location.href='" +  getDownloadUrl() + uid + "'");
				send(getPage(), Broadcast.BREADTH, new ModelChangeEvent<Void>(ModelChangeEvent.START_TIMER_BEHAVIOR));
				close(target);
			}
		});
		add(form); 
	}
	
	// ----------------------------------------------------
	
	@Override
	protected ResourceStreamResource prepareResource() {
		 final ResourceStreamResource resource = new ResourceStreamResource(new SchemaExport(format));
		 resource.setContentDisposition(ContentDisposition.ATTACHMENT);
		 resource.setFileName("export-" + System.currentTimeMillis() + "." + format.getObject().toLowerCase());
		 return resource;
	}
	
	private ListModel<String> getChoices() {
		return new ListModel<String>(Arrays.asList(new String [] {"JSON", "RDF", "RSF"}));
	}
	
	// ----------------------------------------------------
	
	class SchemaExport extends AbstractResourceStream {

		private final IModel<String> format;
		private ByteArrayInputStream in;

		/**
		 * @param format
		 */
		public SchemaExport(final IModel<String> format) {
			this.format = format;
		}

		/** 
		 * {@inheritDoc}
		 */
		@Override
		public InputStream getInputStream()	throws ResourceStreamNotFoundException {
			final SchemaExporter exporter = schemaManager.getExporter(format.getObject());
			final ByteArrayOutputStream out = new ByteArrayOutputStream();
			try {
				report = exporter.exportAll(out);
				send(SchemaExportDialog.this.getPage(), Broadcast.BREADTH,
						new ModelChangeEvent<IOReport>(report, ModelChangeEvent.IO_REPORT));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			in = new ByteArrayInputStream(out.toByteArray());
			return in;
		}

		/** 
		 * {@inheritDoc}
		 */
		@Override
		public void close() throws IOException {
			if (in != null) {
				in.close();
			}
		}

	}
	
}
