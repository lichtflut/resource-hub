/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.dialogs;

import de.lichtflut.rb.core.io.IOReport;
import de.lichtflut.rb.core.io.ReportingStatementImporter;
import de.lichtflut.rb.core.services.DomainOrganizer;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.context.Context;
import org.arastreju.sge.io.RdfXmlBinding;
import org.arastreju.sge.io.SemanticGraphIO;
import org.arastreju.sge.io.SemanticIOException;
import org.arastreju.sge.persistence.TransactionControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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

	private static final Logger LOGGER = LoggerFactory.getLogger(InformationImportDialog.class);
	
	@SpringBean
	private ModelingConversation conversation;

    @SpringBean
    private DomainOrganizer organizer;

	// ----------------------------------------------------
	
	/**
	 * @param id
	 */
	@SuppressWarnings("rawtypes")
	public InformationImportDialog(final String id) {
		super(id);
		
		final Form form = new Form("form");

        final IModel<String> format = new Model<String>("RDF-XML");
		form.add(new DropDownChoice<String>("format", format, getChoices()));

        final IModel<Context> targetContext = new Model<Context>();
        form.add(new DropDownChoice<Context>("targetContext", targetContext, getAvailableContexts()));



        final FileUploadField uploadField = new FileUploadField("file");
		uploadField.setRequired(true);
		form.add(uploadField);

        form.add(new AjaxButton("upload", form) {
			@Override
			protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
				importUpload(uploadField.getFileUploads(), format);
				close(target);
			}
			@Override
			protected void onError(final AjaxRequestTarget target, final Form<?> form) {
				target.add(form);
			}
		});
		
		add(form); 
		
		setModal(true);
		setWidth(600);
	}
	
	// ----------------------------------------------------
	
	private ListModel<String> getChoices() {
		return new ListModel<String>(Arrays.asList("RDF-XML", "JSON", "N3"));
	}
	
	private void importUpload(final List<FileUpload> uploadList, final IModel<String> format) {
		boolean isFirstReport = true;
		IOReport endReport = null;
		
		for (FileUpload upload : uploadList) {
			final IOReport report = new IOReport();
			final SemanticGraphIO io = new RdfXmlBinding();
			
			final TransactionControl tx = conversation.beginTransaction();
			final ReportingStatementImporter importer = new ReportingStatementImporter(conversation);
			try {	
				io.read(upload.getInputStream(), importer);
				report.merge(importer.createReport());
				tx.success();
				report.success();
			} catch (IOException e) {
				LOGGER.error("Import failed.", e);
				report.setAdditionalInfo("[" +upload.getClientFileName() +"] " +e.getMessage());
				report.error();
				tx.fail();
			} catch (SemanticIOException e) {
				LOGGER.error("Import failed.", e);
				report.setAdditionalInfo("[" +upload.getClientFileName() +"] " +e.getMessage());
				report.error();
				tx.fail();
			} finally {
				tx.finish();
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

    public List<Context> getAvailableContexts() {
        return organizer.getContexts();
    }
}
