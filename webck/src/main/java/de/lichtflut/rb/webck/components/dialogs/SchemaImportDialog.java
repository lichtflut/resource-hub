/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.dialogs;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.lichtflut.rb.core.io.IOReport;
import de.lichtflut.rb.core.services.SchemaImporter;
import de.lichtflut.rb.core.services.SchemaManager;
import de.lichtflut.rb.webck.events.ModelChangeEvent;

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
public class SchemaImportDialog extends AbstractRBDialog {

	@SpringBean
	private SchemaManager schemaManager;

	private final IModel<String> format = new Model<String>("RSF");

	// ----------------------------------------------------

	/**
	 * @param id The component ID.
	 */
	public SchemaImportDialog(final String id) {
		super(id);
		setModal(true);
		setWidth(600);

		setTitle(new ResourceModel("title.schema-import-dialog"));

		final Form<?> form = new Form<Void>("form");
		form.setOutputMarkupId(true);
		form.add(new FeedbackPanel("feedback"));

		form.add(new DropDownChoice<String>("format", format, getChoices()));

		final FileUploadField uploadField = new FileUploadField("file");
		uploadField.setRequired(true);
		form.add(uploadField);
		form.add(new AjaxButton("upload", form) {
			@Override
			protected void onError(final AjaxRequestTarget target, final Form<?> form) {
				target.add(form);
			}
			@Override
			protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
				importUpload(uploadField.getFileUploads(), format);
				close(target);
			}
		});
		add(form);
	}

	// ----------------------------------------------------

	private ListModel<String> getChoices() {
		return new ListModel<String>(Arrays.asList(new String [] {"JSON", "RDF", "RSF"}));
	}

	private void importUpload(final List<FileUpload> uploadList, final IModel<String> format) {
		Boolean isFirstReport = true;
		IOReport endReport = null;

		for (FileUpload upload : uploadList) {
			final SchemaImporter importer = schemaManager.getImporter(format.getObject());
			IOReport report;
			try {
				report = importer.read(upload.getInputStream());
			} catch (IOException e) {
				report = new IOReport();
				report.setAdditionalInfo("[" +upload.getClientFileName() +"] " +e.getMessage());
				report.error();
			}
			upload.closeStreams();
			if(isFirstReport) {
				endReport = report;
				isFirstReport = false;
			} else {
				endReport.merge(report);
			}
		}
		send(getPage(), Broadcast.BREADTH, new ModelChangeEvent<IOReport>(endReport, ModelChangeEvent.IO_REPORT));
	}

}
