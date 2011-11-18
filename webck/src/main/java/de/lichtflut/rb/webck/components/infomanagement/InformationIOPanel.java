/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.infomanagement;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
import org.apache.wicket.request.Response;
import org.apache.wicket.request.resource.IResource;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.io.OntologyIOException;
import org.arastreju.sge.io.RdfXmlBinding;
import org.arastreju.sge.io.SemanticGraphIO;
import org.arastreju.sge.model.DefaultSemanticGraph;
import org.arastreju.sge.model.SemanticGraph;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.views.SNClass;
import org.odlabs.wiquery.ui.dialog.Dialog;
import org.odlabs.wiquery.ui.dialog.DialogJavaScriptResourceReference;

import de.lichtflut.infra.exceptions.NotYetSupportedException;
import de.lichtflut.rb.core.services.ServiceProvider;

/**
 * <p>
 *  Panel for import/export of information of semantic graph.
 * </p>
 *
 * <p>
 * 	Created Oct 20, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class InformationIOPanel extends Panel {
	
	/**
	 * Constructor.
	 */
	@SuppressWarnings("rawtypes")
	public InformationIOPanel(final String id) {
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
	
	protected abstract ServiceProvider getServiceProvider();
	
	// -----------------------------------------------------
	
	@SuppressWarnings("rawtypes")
	private Dialog createExportDialog() {
		final Dialog dialog = new Dialog("exportDialog");
		final Form form = new Form("form");
		final IModel<String> format = new Model<String>("RDF-XML");
		final ResourceLink download = new ResourceLink("download", new ContentExport(format));
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
			}
		});
		dialog.add(form); 
		return dialog;
	}
	
	@SuppressWarnings("rawtypes")
	private Dialog createImportDialog() {
		final Dialog dialog = new Dialog("importDialog");
		final Form form = new Form("form");
		final IModel<String> format = new Model<String>("RDF-XML");
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
		return new ListModel<String>(Arrays.asList(new String [] {"RDF-XML", "JSON"}));
	}
	
	// -----------------------------------------------------
	
	private void importUpload(final FileUpload upload, final IModel<String> format) {
		upload.closeStreams();
	}
	
	
	// -----------------------------------------------------
	
	class ContentExport implements IResource {

		private final IModel<String> format;

		/**
		 * @param format
		 */
		public ContentExport(final IModel<String> format) {
			this.format = format;
		}

		/** 
		 * {@inheritDoc}
		 */
		@Override
		public void respond(final Attributes attributes) {
			final ByteArrayOutputStream out = new ByteArrayOutputStream();
			try {
				final ServiceProvider sp = getServiceProvider();
				final List<SNClass> types = sp.getTypeManager().findAll();
				
				final SemanticGraph graph = new DefaultSemanticGraph();
				for (SNClass type : types) {
					final List<ResourceNode> entities = sp.getArastejuGate().createQueryManager().findByType(type);
					for (ResourceNode entity : entities) {
						graph.merge(new DefaultSemanticGraph(entity));
					}
				}
				if (!"RDF-XML".equalsIgnoreCase(format.getObject())){
					throw new NotYetSupportedException("Only RDF-XML supported yet!");
				}
				final SemanticGraphIO io = new RdfXmlBinding();
				io.write(graph, out);
				
			} catch (IOException e) {
				throw new RuntimeException(e);
			} catch (OntologyIOException e) {
				throw new RuntimeException(e);
			}
			final Response response = attributes.getResponse();
			response.write(out.toByteArray());
		}
		
	}

}
