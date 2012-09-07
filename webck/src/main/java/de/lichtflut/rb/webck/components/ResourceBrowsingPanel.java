/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.defaultButtonIf;
import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.not;

import java.io.IOException;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.entity.EntityHandle;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.services.EntityManager;
import de.lichtflut.rb.core.services.impl.LinkProvider;
import de.lichtflut.rb.webck.browsing.EntityAttributeApplyAction;
import de.lichtflut.rb.webck.browsing.ReferenceReceiveAction;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.common.RBWebSession;
import de.lichtflut.rb.webck.components.entity.BrowsingButtonBar;
import de.lichtflut.rb.webck.components.entity.ClassifyEntityPanel;
import de.lichtflut.rb.webck.components.entity.EntityInfoPanel;
import de.lichtflut.rb.webck.components.entity.EntityPanel;
import de.lichtflut.rb.webck.components.entity.IBrowsingHandler;
import de.lichtflut.rb.webck.components.entity.LocalButtonBar;
import de.lichtflut.rb.webck.components.form.RBStandardButton;
import de.lichtflut.rb.webck.components.relationships.CreateRelationshipPanel;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import de.lichtflut.rb.webck.models.BrowsingContextModel;
import de.lichtflut.rb.webck.models.ConditionalModel;
import de.lichtflut.rb.webck.models.entity.RBEntityModel;
import de.lichtflut.repository.ContentDescriptor;
import de.lichtflut.repository.impl.ContentDescriptorBuilder;

/**
 * <p>
 * Panel for placing a resource browser panel into a page.
 * </p>
 * 
 * <p>
 * Created Dec 5, 2011
 * </p>
 * 
 * @author Oliver Tigges
 */
@SuppressWarnings("rawtypes")
public class ResourceBrowsingPanel extends Panel implements IBrowsingHandler {

	private final RBEntityModel model = new RBEntityModel();

	@SpringBean
	private EntityManager entityManager;

	// ---------------- Constructor -------------------------

	/**
	 * Constructor.
	 * 
	 * @param id The component ID.
	 */
	public ResourceBrowsingPanel(final String id) {
		super(id);

		add(new EntityInfoPanel("resourceInfo", model));

		final Form form = new Form("form");
		form.setOutputMarkupId(true);
		form.setMultiPart(true);

		form.add(new EntityPanel("entity", model));
		form.add(new ClassifyEntityPanel("classifier", model));

		form.add(createLocalButtonBar());
		form.add(new BrowsingButtonBar("browsingButtonBar", model));

		form.add(new CreateRelationshipPanel("relationCreator", model).add(visibleIf(BrowsingContextModel.isInViewMode())));

		form.add(createRelationshipView("relationships", model));

		add(form);

		setOutputMarkupId(true);
	}

	// -- IBrowsingHandler --------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createReferencedEntity(final EntityHandle handle, final ResourceID predicate) {
		// store current entity
		// TODO: this should be stored transient in session / browsing history.
		entityManager.store(model.getObject());

		// navigate to sub entity
		final ReferenceReceiveAction action = new EntityAttributeApplyAction(model.getObject(), predicate);
		RBWebSession.get().getHistory().createReference(handle, action);
	}

	// ----------------------------------------------------

	protected Component createRelationshipView(final String id, final IModel<RBEntity> model) {
		return new WebMarkupContainer(id);
	}

	// ----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onEvent(final IEvent<?> event) {
		final ModelChangeEvent<?> mce = ModelChangeEvent.from(event);
		if (mce.isAbout(ModelChangeEvent.RELATIONSHIP, ModelChangeEvent.ENTITY)) {
			RBAjaxTarget.add(this);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onConfigure() {
		super.onConfigure();
		// reset the model before render to fetch the latest from browsing history.
		model.reset();
	}

	// ------------------------------------------------------

	private LocalButtonBar createLocalButtonBar() {
		return new LocalButtonBar("localButtonBar", model) {
			@Override
			protected Component createSaveButton(final IModel<RBEntity> model) {
				ConditionalModel viewMode = BrowsingContextModel.isInViewMode();

				final RBStandardButton save = new RBStandardButton("save") {
					@Override
					protected void applyActions(final AjaxRequestTarget target, final Form<?> form) {
						prepareForFileService(model);
						entityManager.store(model.getObject());
						RBWebSession.get().getHistory().finishEditing();
						send(getPage(), Broadcast.BREADTH, new ModelChangeEvent<Void>(ModelChangeEvent.ENTITY));
					}
				};
				save.add(visibleIf(not(viewMode)));
				save.add(defaultButtonIf(not(viewMode)));
				return save;
			}
		};
	}

	protected void prepareForFileService(final IModel<RBEntity> model) {
		for (RBField field : model.getObject().getAllFields()) {
			if (field.getDataType().equals(Datatype.FILE)) {
				int index = 0;
				ContentDescriptor descriptor = null;
				for (Object value : field.getValues()) {
					buildContentDescriptorFor(model, field, index, descriptor, value);
				}
			}
		}
	}

	private void buildContentDescriptorFor(final IModel<RBEntity> model, final RBField field, int index, ContentDescriptor descriptor,
			final Object value) {
		if(!(value instanceof String)){
			//There will be only single file upload, so get first element.
			FileUpload fileUpload = (FileUpload) ((List)value).get(0);
			String path = new LinkProvider().buildRepositoryStructureFor(model.getObject(), fileUpload.getClientFileName());
			try {
				descriptor = new ContentDescriptorBuilder().name(fileUpload.getClientFileName())
						.mimeType(fileUpload.getContentType()).path(path).data(fileUpload.getInputStream()).build();
			} catch (IOException e) {
				e.printStackTrace();
			}
			field.setValue(index, descriptor);
			index++;
		}
	}

}
