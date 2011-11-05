/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.typesystem;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.link.ResourceLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.request.resource.ResourceStreamResource;
import org.apache.wicket.util.resource.AbstractResourceStream;
import org.apache.wicket.util.resource.ResourceStreamNotFoundException;
import org.odlabs.wiquery.ui.dialog.Dialog;
import org.odlabs.wiquery.ui.dialog.DialogJavaScriptResourceReference;

import de.lichtflut.rb.core.api.SchemaExporter;
import de.lichtflut.rb.core.api.SchemaImporter;
import de.lichtflut.rb.core.api.SchemaManager;

/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created Oct 20, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class SchemaIOPanel extends Panel {
	
	/**
	 * Constructor.
	 */
	@SuppressWarnings("rawtypes")
	public SchemaIOPanel(final String id) {
		super(id);
		
		final Dialog exportDialog = createExportDialog();
		add(exportDialog);
		
		final Link exportLink = new AjaxFallbackLink("exportLink") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				exportDialog.open(target);
			}
		};
		add(exportLink);
		
		final Dialog importDialog = createImportDialog();
		add(importDialog);
		
		final Link importLink = new AjaxFallbackLink("importLink") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				importDialog.open(target);
			}
		};
		add(importLink);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void renderHead(final IHeaderResponse response) {
		super.renderHead(response);
		response.renderJavaScriptReference(DialogJavaScriptResourceReference.get());
	}
	
	// -----------------------------------------------------
	
	protected abstract SchemaManager getSchemaManager();
	
	// -----------------------------------------------------
	
	@SuppressWarnings("rawtypes")
	private Dialog createExportDialog() {
		final Dialog dialog = new Dialog("exportDialog");
		final Form form = new Form("form");
		final IModel<String> format = new Model<String>("JSON");
		final ResourceLink download = new ResourceLink("download", new ResourceStreamResource(new SchemaExport(format)));
		download.setOutputMarkupId(true);
		form.add(download);
		form.add(new DropDownChoice<String>("format", format, getChoices()));
		form.add(new AjaxButton("exportButton", form) {
			@Override
			protected void onError(final AjaxRequestTarget target, final Form<?> form) {
			}
			@Override
			protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
				dialog.close(target);
				target.appendJavaScript("window.location.href='internal/query'");
//				RequestCycle.get().scheduleRequestHandlerAfterCurrent(
//						new ResourceStreamRequestHandler(new SchemaExport(format)));
			}
		});
		dialog.add(form); 
		return dialog;
	}
	
	@SuppressWarnings("rawtypes")
	private Dialog createImportDialog() {
		final Dialog dialog = new Dialog("importDialog");
		final Form form = new Form("form");
		final IModel<String> format = new Model<String>("JSON");
		form.add(new DropDownChoice<String>("format", format, getChoices()));

		final FileUploadField uploadField = new FileUploadField("file");
		form.add(uploadField);
		form.add(new AjaxButton("upload", form) {
			@Override
			protected void onError(final AjaxRequestTarget target, final Form<?> form) {
			}
			@Override
			protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
				importUpload(uploadField.getFileUpload(), format);
				dialog.close(target);
			}
		});
		dialog.add(form); 
		return dialog;
	}
	
	private ListModel<String> getChoices() {
		return new ListModel<String>(Arrays.asList(new String [] {"JSON", "RDF", "RSF"}));
	}
	
	// -----------------------------------------------------
	
	private void importUpload(final FileUpload upload, final IModel<String> format) {
		final SchemaImporter importer = getSchemaManager().getImporter(format.getObject());
		try {
			importer.read(upload.getInputStream());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		upload.closeStreams();
	}
	
	
	// -----------------------------------------------------
	
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
		public InputStream getInputStream()
				throws ResourceStreamNotFoundException {
			final SchemaExporter exporter = getSchemaManager().getExporter(format.getObject());
			final ByteArrayOutputStream out = new ByteArrayOutputStream();
			try {
				exporter.exportAll(out);
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
