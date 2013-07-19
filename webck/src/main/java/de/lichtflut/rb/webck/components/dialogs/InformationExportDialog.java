/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.ContentDisposition;
import org.apache.wicket.request.resource.IResource.Attributes;
import org.apache.wicket.request.resource.ResourceStreamResource;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.lang.Bytes;
import org.apache.wicket.util.resource.AbstractResourceStream;
import org.apache.wicket.util.resource.ResourceStreamNotFoundException;
import org.arastreju.sge.context.Context;
import org.arastreju.sge.io.JsonBinding;
import org.arastreju.sge.io.N3Binding;
import org.arastreju.sge.io.RdfXmlBinding;
import org.arastreju.sge.io.SemanticIOException;
import org.arastreju.sge.io.StatementContainer;

import de.lichtflut.rb.core.io.IOReport;
import de.lichtflut.rb.core.services.DomainOrganizer;
import de.lichtflut.rb.core.services.InformationManager;
import de.lichtflut.rb.webck.events.ModelChangeEvent;

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

	private final IModel<Context> srcContext = new Model<Context>();

	private final IModel<String> format = new Model<String>("RDF-XML");

	private final ResourceStreamResource resource;

	private final IModel<? extends StatementContainer> graphModel;

	// ----------------------------------------------------

	@SpringBean
	private DomainOrganizer organizer;

	@SpringBean
	private InformationManager informationManager;

	// ----------------------------------------------------

	/**
	 * Constructor for export dialog with an predefined export set.
	 * @param id The wicket ID.
	 * @param model The model to export.
	 */
	@SuppressWarnings("rawtypes")
	public InformationExportDialog(final String id, final IModel<? extends StatementContainer> model) {
		super(id);
		this.graphModel = model;

		resource = prepareResource(format);

		final Form form = createForm(true);
		add(form);

		setModal(true);
		setWidth(600);
	}

	/**
	 * Creates an export dialog, where the user chooses the context to export.
	 * @param id The wicket ID.
	 */
	@SuppressWarnings("rawtypes")
	public InformationExportDialog(final String id) {
		super(id);
		this.graphModel = createContextExportModel();

		resource = prepareResource(format);

		final Form form = createForm(false);
		add(form);

		setModal(true);
		setWidth(600);
	}

	// -- IResourceListener -------------------------------

	@Override
	public void onResourceRequested() {
		final RequestCycle cycle = RequestCycle.get();
		final Attributes a = new Attributes(cycle.getRequest(), cycle.getResponse(), null);
		resource.setFileName(getFilename());
		resource.respond(a);
	}

	// ----------------------------------------------------

	public List<Context> getAvailableContexts() {
		return organizer.getContexts();
	}

	protected ListModel<String> getChoices() {
		return new ListModel<String>(Arrays.asList("RDF-XML", "JSON", "N3"));
	}

	protected CharSequence getDownloadUrl() {
		return urlFor(IResourceListener.INTERFACE, new PageParameters());
	}

	protected ResourceStreamResource prepareResource(final IModel<String> format) {
		final ResourceStreamResource resource = new ResourceStreamResource(new ContentStream(format));
		resource.setContentDisposition(ContentDisposition.ATTACHMENT);
		return resource;
	}

	private Form<?> createForm(final boolean predefined) {
		final Form<?> form = new Form<Void>("form");
		form.add(new FeedbackPanel("feedback"));

		final DropDownChoice<Context> ctxChooser = new DropDownChoice<Context>("srcContext", srcContext, getAvailableContexts());
		if (predefined) {
			ctxChooser.setVisible(false);
		} else {
			ctxChooser.setRequired(true);
		}
		form.add(ctxChooser);

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
		return form;
	}

	private String getFilename() {
		final StringBuilder sb = new StringBuilder(128);
		if (srcContext.getObject() == null) {
			sb.append("export-");
			sb.append(System.currentTimeMillis());
		} else {
			sb.append(srcContext.getObject().getQualifiedName().getSimpleName());
		}
		sb.append(".");
		if ("RDF-XML".equals(format.getObject())) {
			sb.append("rdf.xml");
		} else {
			sb.append(format.getObject().toLowerCase());
		}
		return sb.toString();
	}

	private IModel<StatementContainer> createContextExportModel() {
		return new AbstractReadOnlyModel<StatementContainer>() {
			@Override
			public StatementContainer getObject() {
				return informationManager.exportInformation(srcContext.getObject());
			}
		};
	}


	// ----------------------------------------------------

	class ContentStream extends AbstractResourceStream {

		private final IModel<String> format;
		private transient InputStream in;
		private Bytes length;

		/**
         * Constructor.
		 * @param format The format of export.
		 */
		public ContentStream(final IModel<String> format) {
			this.format = format;
		}

		@Override
		public InputStream getInputStream() throws ResourceStreamNotFoundException {
			IOReport report = new IOReport();
			final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			try {
				final StatementContainer stmtContainer = graphModel.getObject();

				if ("RDF-XML".equalsIgnoreCase(format.getObject())){
					new RdfXmlBinding().write(stmtContainer, buffer);
				} else if ("JSON".equals(format.getObject())) {
					new JsonBinding().write(stmtContainer, buffer);
				} else if ("N3".equals(format.getObject())) {
					new N3Binding().write(stmtContainer, buffer);
				} else {
					throw new IllegalArgumentException("Format not yet supported: " + format.getObject());
				}

				length = Bytes.bytes(buffer.size());
				in = new ByteArrayInputStream(buffer.toByteArray());

				report.add("Namespaces", stmtContainer.getNamespaces().size());
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

		@Override
		public void close() throws IOException {
			in.close();
			in = null;
		}

		@Override
		public Bytes length() {
			return length;
		}

	}

}
