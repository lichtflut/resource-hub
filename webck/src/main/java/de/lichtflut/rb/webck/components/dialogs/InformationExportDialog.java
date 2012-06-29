/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.dialogs;

import de.lichtflut.rb.core.io.IOReport;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import org.apache.wicket.IResourceListener;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.ContentDisposition;
import org.apache.wicket.request.resource.IResource.Attributes;
import org.apache.wicket.request.resource.ResourceStreamResource;
import org.apache.wicket.util.lang.Bytes;
import org.apache.wicket.util.resource.AbstractResourceStream;
import org.apache.wicket.util.resource.ResourceStreamNotFoundException;
import org.arastreju.sge.io.JsonBinding;
import org.arastreju.sge.io.N3Binding;
import org.arastreju.sge.io.RdfXmlBinding;
import org.arastreju.sge.io.SemanticIOException;
import org.arastreju.sge.model.SemanticGraph;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * <p>
 *  Modal dialog for export of semantic graphs.
 * </p>
 *
 * <p>
 * 	Created Dec 7, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class InformationExportDialog extends AbstractRBDialog implements IResourceListener {

	private final ResourceStreamResource resource;
	
	private final IModel<String> format;

	private final IModel<SemanticGraph> graphModel;
	
	// ----------------------------------------------------

	/**
	 * @param id
	 */
	@SuppressWarnings("rawtypes")
	public InformationExportDialog(final String id, final IModel<SemanticGraph> model) {
		super(id);
		this.graphModel = model;
		
		format = new Model<String>("RDF-XML");
		resource = prepareResource(format);
		
		final Form form = new Form("form");
		form.add(new DropDownChoice<String>("format", format, getChoices()));
		form.add(new AjaxButton("exportButton", form) {
			@Override
			protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
				final String uid = "&uid" + System.nanoTime();
				target.appendJavaScript("window.location.href='" +  getDownloadUrl() + uid + "'");
				send(getPage(), Broadcast.BREADTH, new ModelChangeEvent<Void>(ModelChangeEvent.START_TIMER_BEHAVIOR));
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
	
	protected ListModel<String> getChoices() {
		return new ListModel<String>(Arrays.asList(new String [] {"RDF-XML", "JSON", "N3"}));
	}
	
	protected CharSequence getDownloadUrl() {
		return urlFor(IResourceListener.INTERFACE, new PageParameters());
	}
	
	protected ResourceStreamResource prepareResource(final IModel<String> format) {
		 final ResourceStreamResource resource = new ResourceStreamResource(new ContentStream(format));
		 resource.setContentDisposition(ContentDisposition.ATTACHMENT);
		 return resource;
	}
	
	private String getFilename() {
		final StringBuilder sb = new StringBuilder(128);
		sb.append("export-");
		sb.append(System.currentTimeMillis());
		sb.append(".");
		if ("RDF-XML".equals(format.getObject())) {
			sb.append("rdf.xml");
		} else {
			sb.append(format.getObject().toLowerCase());
		}
		return sb.toString();
	}
	
	// ----------------------------------------------------
	
	class ContentStream extends AbstractResourceStream {

		private final IModel<String> format;
		private transient InputStream in;
		private Bytes length;

		/**
		 * @param format
		 */
		public ContentStream(final IModel<String> format) {
			this.format = format;
		}
		
		/** 
		* {@inheritDoc}
		*/
		@Override
		public InputStream getInputStream() throws ResourceStreamNotFoundException {
			IOReport report = new IOReport();
			final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			try {
				final SemanticGraph graph = graphModel.getObject();
				
				if ("RDF-XML".equalsIgnoreCase(format.getObject())){
					new RdfXmlBinding().write(graph, buffer);
				} else if ("JSON".equals(format.getObject())) {
					new JsonBinding().write(graph, buffer);
				} else if ("N3".equals(format.getObject())) {
					new N3Binding().write(graph, buffer);
				} else {
					throw new IllegalArgumentException("Format not yet supported: " + format.getObject());
				}
				
				length = Bytes.bytes(buffer.size());
				in = new ByteArrayInputStream(buffer.toByteArray());
				
				report.add("Namespaces", graph.getNamespaces().size());
				report.add("Nodes", graph.getNodes().size());
				report.add("Subjects", graph.getSubjects().size());
				report.add("Statements", graph.getStatements().size());
				report.success();
				buffer.close();
			} catch (IOException e) {
//				throw new RuntimeException(e);
				report.setAdditionalInfo(e.getMessage());
				report.error();
			} catch (SemanticIOException e) {
//				throw new RuntimeException(e);
				report.setAdditionalInfo(e.getMessage());
				report.error();
			}
			send(InformationExportDialog.this.getPage(), Broadcast.BREADTH,
					new ModelChangeEvent<IOReport>(report, ModelChangeEvent.IO_REPORT));
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
