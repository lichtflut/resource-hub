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
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.io.SemanticIOException;
import org.arastreju.sge.io.RdfXmlBinding;
import org.arastreju.sge.io.SemanticGraphIO;
import org.arastreju.sge.model.SemanticGraph;

import de.lichtflut.rb.core.reporting.IOReport;
import de.lichtflut.rb.core.services.ServiceProvider;
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
	private ServiceProvider provider;
	
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
		return new ListModel<String>(Arrays.asList(new String [] {"RDF-XML", "JSON", "N3"}));
	}
	
	private void importUpload(final List<FileUpload> uploadList, final IModel<String> format) {
		Boolean isFirstReport = true;
		IOReport endReport = null;
		
		for (FileUpload upload : uploadList) {
			IOReport report = new IOReport();
			final SemanticGraphIO io = new RdfXmlBinding();
			try {
				final SemanticGraph graph = io.read(upload.getInputStream());
				provider.getArastejuGate().startConversation().attach(graph);
				
				report.add("Namespaces", graph.getNamespaces().size());
				report.add("Nodes", graph.getNodes().size());
				report.add("Subjects", graph.getSubjects().size());
				report.add("Statements", graph.getStatements().size());
				report.success();
			} catch (IOException e) {
//				throw new RuntimeException(e);
				report.setAdditionalInfo("[" +upload.getClientFileName() +"] " +e.getMessage());
				report.error();
			} catch (SemanticIOException e) {
//				throw new RuntimeException(e);
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
