/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.dialogs;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.IResourceListener;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.resource.ContentDisposition;
import org.apache.wicket.request.resource.IResource.Attributes;
import org.apache.wicket.request.resource.ResourceStreamResource;
import org.apache.wicket.util.lang.Bytes;
import org.apache.wicket.util.resource.AbstractResourceStream;
import org.apache.wicket.util.resource.ResourceStreamNotFoundException;
import org.arastreju.sge.io.JsonBinding;
import org.arastreju.sge.io.N3Binding;
import org.arastreju.sge.io.OntologyIOException;
import org.arastreju.sge.io.RdfXmlBinding;
import org.arastreju.sge.model.DefaultSemanticGraph;
import org.arastreju.sge.model.SemanticGraph;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.views.SNClass;

import de.lichtflut.rb.core.services.ServiceProvider;

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
public abstract class InformationExportDialog extends AbstractRBDialog implements IResourceListener {

	private ResourceStreamResource resource;
	
	private IModel<String> format;
	
	// ----------------------------------------------------

	/**
	 * @param id
	 */
	@SuppressWarnings("rawtypes")
	public InformationExportDialog(final String id) {
		super(id);
		
		format = new Model<String>("RDF-XML");
		resource = prepareResource(format);
		
		final Form form = new Form("form");
		form.add(new DropDownChoice<String>("format", format, getChoices()));
		form.add(new AjaxButton("exportButton", form) {
			@Override
			protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
				final String uid = "&uid" + System.nanoTime();
				target.appendJavaScript("window.location.href='" +  getDownloadUrl() + uid + "'");
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
		resource.setFileName("export-" + System.currentTimeMillis() + "." + format.getObject().toLowerCase());
		resource.respond(a);
	}
	
	// ----------------------------------------------------
	
	protected abstract ServiceProvider getServiceProvider();
	
	// ----------------------------------------------------
	
	protected ListModel<String> getChoices() {
		return new ListModel<String>(Arrays.asList(new String [] {"RDF-XML", "JSON", "N3"}));
	}
	
	protected CharSequence getDownloadUrl() {
		return urlFor(IResourceListener.INTERFACE);
	}
	
	protected ResourceStreamResource prepareResource(final IModel<String> format) {
		 final ResourceStreamResource resource = new ResourceStreamResource(new ContentStream(format));
		 resource.setContentDisposition(ContentDisposition.ATTACHMENT);
		 return resource;
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
			final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			try {
				final ServiceProvider sp = getServiceProvider();
				final List<SNClass> types = sp.getTypeManager().findAllTypes();
				
				final SemanticGraph graph = new DefaultSemanticGraph();
				for (SNClass type : types) {
					final List<ResourceNode> entities = sp.getArastejuGate().createQueryManager().findByType(type);
					for (ResourceNode entity : entities) {
						graph.merge(new DefaultSemanticGraph(entity));
					}
				}
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
			} catch (IOException e) {
				throw new RuntimeException(e);
			} catch (OntologyIOException e) {
				throw new RuntimeException(e);
			}
			in = new ByteArrayInputStream(buffer.toByteArray());
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
