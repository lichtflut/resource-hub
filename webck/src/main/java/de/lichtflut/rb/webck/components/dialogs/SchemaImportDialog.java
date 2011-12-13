/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.dialogs;

import java.io.IOException;
import java.util.Arrays;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;

import de.lichtflut.rb.core.api.SchemaImporter;
import de.lichtflut.rb.core.api.SchemaManager;

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
public abstract class SchemaImportDialog extends AbstractRBDialog {
	
	private IModel<String> format = new Model<String>("JSON");
	
	// ----------------------------------------------------

	/**
	 * @param id
	 */
	@SuppressWarnings("rawtypes")
	public SchemaImportDialog(String id) {
		super(id);
		setModal(true);
		setWidth(600);
		
		final Form form = new Form("form");
		form.add(new FeedbackPanel("feedback"));
		
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
				close(target);
			}
		});
		add(form); 
	}
	
	// ----------------------------------------------------

	public abstract SchemaManager getSchemaManager();
	
	// ----------------------------------------------------
	
	private ListModel<String> getChoices() {
		return new ListModel<String>(Arrays.asList(new String [] {"JSON", "RDF", "RSF"}));
	}
	
	private void importUpload(final FileUpload upload, final IModel<String> format) {
		final SchemaImporter importer = getSchemaManager().getImporter(format.getObject());
		try {
			importer.read(upload.getInputStream());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		upload.closeStreams();
	}
	
}
