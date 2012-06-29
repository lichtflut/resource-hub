/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.dialogs;


import de.lichtflut.rb.core.services.ExcelExporter;
import de.lichtflut.rb.core.services.impl.ResourceListExcelExporter;
import de.lichtflut.rb.webck.components.listview.ColumnConfiguration;
import org.apache.wicket.IResourceListener;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
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
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.query.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Set;

/**
 * <p>
 *  Modal dialog for ResourceList-export to an excel-sheet.
 * </p>
 *
 * <p>
 * 	Created Feb 27, 2012
 * </p>
 *
 * @author Erik Aderhold
 */
public class ResourceListExportDialog extends AbstractRBDialog implements IResourceListener {

	private final Logger logger = LoggerFactory.getLogger(ResourceListExportDialog.class);
	
	private final ResourceStreamResource resource;

	private final IModel<String> selection;
	
	private IModel<QueryResult> dataModel;
	private IModel<ColumnConfiguration> configModel;
	
	// ----------------------------------------------------

	/**
	 * @param id
	 */
	@SuppressWarnings("rawtypes")
	public ResourceListExportDialog(final String id, final IModel<QueryResult> dataModel,
			final IModel<ColumnConfiguration> configModel) {
		super(id);

		this.dataModel = dataModel;
		this.configModel = configModel;
		
		selection = new Model<String>("DISPLAYED");
		resource = prepareResource();
		
		final Form form = new Form("form");
		
		form.add(new DropDownChoice<String>("selection", selection, getChoices(), selectionChoiceRenderer()));
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
		return new ListModel<String>(Arrays.asList(new String [] {"DISPLAYED", "ALL"}));
	}
	
	protected IChoiceRenderer<String> selectionChoiceRenderer() {
		return new IChoiceRenderer<String>() {
			@Override
			public Object getDisplayValue(String object) {
				try {
					String string = getString(object);
					return string;
				} catch (MissingResourceException e) {
					// return the object if resource is missing!
					logger.warn("Missing Resource String for selection: " +object);
					return object;
				}
			}
			@Override
			public String getIdValue(String object, int index) {
				return object;
			}
		};
	}
	
	protected CharSequence getDownloadUrl() {
		return urlFor(IResourceListener.INTERFACE, new PageParameters());
	}
	
	protected ResourceStreamResource prepareResource() {
		 final ResourceStreamResource resource = new ResourceStreamResource(new ContentStream());
		 resource.setContentDisposition(ContentDisposition.ATTACHMENT);
		 return resource;
	}
	
	private String getFilename() {
		final StringBuilder sb = new StringBuilder();
		sb.append("ResourceList");
		sb.append("-");
		sb.append(System.currentTimeMillis());
		sb.append(".xls");
		return sb.toString();
	}
	
	// ----------------------------------------------------
	
	class ContentStream extends AbstractResourceStream {

		private transient InputStream in;
		private Bytes length;

		/** 
		* {@inheritDoc}
		*/
		@Override
		public InputStream getInputStream() throws ResourceStreamNotFoundException {
			final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			try {
				
				List<ResourceNode> data = dataModel.getObject().toList();
				List<ResourceID> predicates;
				
				if("DISPLAYED".equalsIgnoreCase(selection.getObject())) {
					predicates = configModel.getObject().getPredicatesToDisplay();
				} else if("ALL".equalsIgnoreCase(selection.getObject())) {
					Set<ResourceID> allPredicates = new HashSet<ResourceID>();
					for (ResourceNode dataNode : data) {
						allPredicates.addAll(SNOPS.predicates(dataNode.getAssociations()));
					}
					predicates = new ArrayList<ResourceID>(allPredicates);
				} else {
					throw new IllegalArgumentException("Selection not yet supported: " + selection.getObject());
				}
				
				ExcelExporter exporter = new ResourceListExcelExporter(data, predicates, getLocale());
				exporter.export(buffer);
				
				length = Bytes.bytes(buffer.size());
				in = new ByteArrayInputStream(buffer.toByteArray());
				buffer.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
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