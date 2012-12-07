/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.dialogs;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.context.Context;

import de.lichtflut.rb.core.io.IOReport;
import de.lichtflut.rb.core.services.DomainOrganizer;
import de.lichtflut.rb.core.services.InformationManager;
import de.lichtflut.rb.webck.components.form.RBDefaultButton;
import de.lichtflut.rb.webck.events.ModelChangeEvent;

/**
 * <p>
 *  Modal dialog for import of semantic graphs.
 * </p>
 *
 * <p>
 * 	Created Dec 14, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class InformationImportDialog extends AbstractRBDialog {

	@SpringBean
	private DomainOrganizer organizer;

	@SpringBean
	private InformationManager informationManager;

	// ----------------------------------------------------

	/**
	 * @param id The component ID.
	 */
	@SuppressWarnings("rawtypes")
	public InformationImportDialog(final String id) {
		super(id);

		final Form form = new Form("form");
		form.add(new FeedbackPanel("feedback"));

		final IModel<Context> targetContext = new Model<Context>();
		final DropDownChoice<Context> ctxChooser = new DropDownChoice<Context>("targetContext", targetContext, getAvailableContexts());
		ctxChooser.setRequired(true);
		form.add(ctxChooser);

		final IModel<String> format = new Model<String>("RDF-XML");
		form.add(new DropDownChoice<String>("format", format, getChoices()));

		final FileUploadField uploadField = new FileUploadField("file");
		uploadField.setRequired(true);
		form.add(uploadField);

		form.add(new RBDefaultButton("upload") {
			@Override
			protected void applyActions(final AjaxRequestTarget target, final Form<?> form) {
				importUpload(uploadField.getFileUploads(), format, targetContext);
				close(target);
			}
		});

		add(form);

		setModal(true);
		setWidth(600);
	}

	// ----------------------------------------------------

	private ListModel<String> getChoices() {
		return new ListModel<String>(Arrays.asList("RDF-XML"));
	}

	private void importUpload(final List<FileUpload> uploadList, final IModel<String> format, final IModel<Context> targetContext) {

		final IOReport endReport = new IOReport();

		for (FileUpload upload : uploadList) {
			final String fileName = upload.getClientFileName();
			try {
				final IOReport report = informationManager.importInformation(
						upload.getInputStream(), targetContext.getObject(), fileName);

				endReport.merge(report);

				upload.closeStreams();

			} catch (IOException e) {
				endReport.error();
				endReport.setAdditionalInfo("Technical failure while importing '" + fileName + "'.");
			}
		}
		send(getPage(), Broadcast.BREADTH, new ModelChangeEvent<IOReport>(endReport, ModelChangeEvent.IO_REPORT));
	}

	public List<Context> getAvailableContexts() {
		return organizer.getContexts();
	}
}
