/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.infomanagement;

import java.io.IOException;
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
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;
import org.arastreju.sge.io.OntologyIOException;
import org.arastreju.sge.io.RdfXmlBinding;
import org.arastreju.sge.io.SemanticGraphIO;
import org.arastreju.sge.model.SemanticGraph;
import org.odlabs.wiquery.ui.dialog.Dialog;
import org.odlabs.wiquery.ui.dialog.DialogJavaScriptResourceReference;

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
		
		final Dialog exportDialog = new InformationExportDialog("exportDialog") {
			@Override
			protected ServiceProvider getServiceProvider() {
				return InformationIOPanel.this.getServiceProvider();
			}
		};
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
	private Dialog createImportDialog() {
		final Dialog dialog = new Dialog("importDialog");
		final Form form = new Form("form");
		final IModel<String> format = new Model<String>("RDF-XML");
		form.add(new DropDownChoice<String>("format", format, getChoices()));

		final FileUploadField uploadField = new FileUploadField("file");
		form.add(uploadField);
		form.add(new AjaxButton("upload", form) {
			@Override
			protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
				importUpload(uploadField.getFileUpload(), format);
				dialog.close(target);
			}
			@Override
			protected void onError(final AjaxRequestTarget target, final Form<?> form) {
				target.add(form);
			}
		});
		dialog.add(form); 
		return dialog;
	}
	
	private ListModel<String> getChoices() {
		return new ListModel<String>(Arrays.asList(new String [] {"RDF-XML", "JSON", "N3"}));
	}
	
	// -----------------------------------------------------
	
	private void importUpload(final FileUpload upload, final IModel<String> format) {
		final SemanticGraphIO io = new RdfXmlBinding();
		try {
			final SemanticGraph graph = io.read(upload.getInputStream());
			getServiceProvider().getArastejuGate().startConversation().attach(graph);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (OntologyIOException e) {
			throw new RuntimeException(e);
		}
		upload.closeStreams();
	}
	
	
}
