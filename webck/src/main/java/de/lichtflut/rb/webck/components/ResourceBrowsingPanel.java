/*
 * Copyright 2013 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;

import java.util.List;
import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.util.MapModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.ResourceID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.core.eh.ErrorCodes;
import de.lichtflut.rb.core.entity.EntityHandle;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.core.schema.model.impl.CardinalityBuilder;
import de.lichtflut.rb.core.services.EntityManager;
import de.lichtflut.rb.core.services.SemanticNetworkService;
import de.lichtflut.rb.webck.browsing.BrowsingState;
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
import de.lichtflut.rb.webck.components.relationships.CreateRelationshipPanel;
import de.lichtflut.rb.webck.components.relationships.RelationshipOverviewPanel;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import de.lichtflut.rb.webck.models.BrowsingContextModel;
import de.lichtflut.rb.webck.models.basic.AbstractLoadableModel;
import de.lichtflut.rb.webck.models.basic.LoadableModel;

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
public class ResourceBrowsingPanel extends Panel implements IBrowsingHandler {

	private final LoadableModel<RBEntity> model = new RBEntityModel();
	private final IModel<Map<Integer, List<RBField>>> errorModel = new MapModel<Integer,List<RBField>>();

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
		add(new RBEntityFeedbackPanel("feedback", errorModel).setOutputMarkupId(true).setEscapeModelStrings(false));

		final Form<?> form = new Form<Void>("form");
		form.setOutputMarkupId(true);
		form.setMultiPart(true);

		form.add(createInfoPanel("resourceInfo", model));
		form.add(createEntityPanel("entity", model));
		form.add(createClassifyPanel("classifier", model));
		form.add(createLocalButtonBar("localButtonBar", model));

		form.add(new BrowsingButtonBar("browsingButtonBar", model));

		form.add(createRelationshipPanel("relationCreator", model));

		form.add(createRelationshipView("relationships", model));

		add(form);

		setDefaultModel(model);
		setOutputMarkupId(true);
	}

	// -- IBrowsingHandler --------------------------------

	@Override
	public void createReferencedEntity(final EntityHandle handle, final ResourceID predicate) {
		// store current entity
		// TODO: this should be stored transient in session / browsing history.

		entityManager.store(model.getObject());

		// navigate to sub entity
		final ReferenceReceiveAction<?> action = new EntityAttributeApplyAction(model.getObject(), predicate);
		RBWebSession.get().getHistory().createReference(handle, action);
	}

	public Form<?> getForm(){
		return (Form<?>) get("form");
	}

	// -- OVERRIDE HOOKS ----------------------------------

	protected Component createRelationshipView(final String id, final IModel<RBEntity> model) {
		return new RelationshipOverviewPanel(id, model).add(visibleIf(BrowsingContextModel.isInViewMode()));
	}

	protected Component createInfoPanel(final String id, final IModel<RBEntity> model) {
		return new EntityInfoPanel(id, model);
	}

	protected Component createEntityPanel(final String id, final IModel<RBEntity> model) {
		return new EntityPanel(id, model);
	}

	protected Component createClassifyPanel(final String id, final IModel<RBEntity> model) {
		return new ClassifyEntityPanel(id, model);
	}

	protected Component createRelationshipPanel(final String id, final IModel<RBEntity> model) {
		return new CreateRelationshipPanel(id, model).add(visibleIf(BrowsingContextModel.isInViewMode()));
	}

	protected LocalButtonBar createLocalButtonBar(final String id, final IModel<RBEntity> model) {
		return new LocalButtonBar(id, model) {
			@Override
			protected void onSave(final IModel<RBEntity> model, final AjaxRequestTarget target, final Form<?> form) {
				ResourceBrowsingPanel.this.onSave(model);
			}

			@Override
			protected void onError(final Map<Integer, List<RBField>> errors) {
				// Wie still have to save the changes, so they won't get lost during ajax update
				entityManager.store(model.getObject());
				errorModel.setObject(errors);
				//				String errorMessage = buildFeedbackMessage(errors);
				//				error(errorMessage);
				RBAjaxTarget.add(ResourceBrowsingPanel.this);
			}

			@Override
			protected void onCancel(final AjaxRequestTarget target, final Form<?> form){
				ResourceBrowsingPanel.this.onCancel(target, form);
			}
		};
	}

	protected void onCancel(final AjaxRequestTarget target, final Form<?> form) {
		BrowsingState state = RBWebSession.get().getHistory().getCurrentStep().getState();
		if(BrowsingState.CREATE.name().equals(state.name())){
			entityManager.delete(model.getObject().getID());
		}
		RBWebSession.get().getHistory().back();
		send(getPage(), Broadcast.BREADTH, new ModelChangeEvent<Void>(ModelChangeEvent.ENTITY));
	}

	/**
	 * Additional save-action.
	 * @param model Edited entity
	 */
	protected void onSave(final IModel<RBEntity> model) {
		entityManager.store(model.getObject());
		RBWebSession.get().getHistory().finishEditing();
		send(getPage(), Broadcast.BREADTH, new ModelChangeEvent<Void>(ModelChangeEvent.ENTITY));
	}

	// ----------------------------------------------------

	@Override
	public void onEvent(final IEvent<?> event) {
		final ModelChangeEvent<?> mce = ModelChangeEvent.from(event);
		if (mce.isAbout(ModelChangeEvent.RELATIONSHIP, ModelChangeEvent.ENTITY)) {
			RBAjaxTarget.add(this);
		}
	}

	@Override
	protected void onConfigure() {
		super.onConfigure();
		// reset the model before render to fetch the latest from browsing history.
		model.reset();
	}

	// ------------------------------------------------------

	/**
	 * TODO: OT 2012-12-05 Transfer markup into HTML-Template and text into properties (not internationalizable)
	 *
	 * Better: create own feedback panel: http://m3g4h4rd.blogspot.de/2011/01/how-to-customize-wicket-feedback-panel.html
	 */
	private String buildFeedbackMessage(final Map<Integer, List<RBField>> errors) {
		StringBuilder sb = new StringBuilder();
		sb.append(getString("error.validation"));
		sb.append("<ul>");
		for (Integer errorCode : errors.keySet()) {
			if(ErrorCodes.CARDINALITY_EXCEPTION == errorCode){
				sb.append("Cardinality is not as defined: ");
				List<RBField> fields = errors.get(ErrorCodes.CARDINALITY_EXCEPTION);
				for (RBField field : fields) {
					sb.append("<li>");
					sb.append("Cardinality of \"" + field.getLabel(getLocale()) + "\" is definened as: " + CardinalityBuilder.getCardinalityAsString(field.getCardinality()));
					sb.append("</li>");
				}
			}
		}
		sb.append("</ul>");
		return sb.toString();
	}

	// ------------------------------------------------------

	/**
	 * <p>
	 *  Model for an {@link RBEntity} loaded from entity stack in session.
	 * </p>
	 *
	 * <p>
	 * 	Created Nov 15, 2011
	 * </p>
	 *
	 * @author Oliver Tigges
	 */
	class RBEntityModel extends AbstractLoadableModel<RBEntity> {

		final Logger LOGGER = LoggerFactory.getLogger(RBEntityModel.class);

		@SpringBean
		private EntityManager entityManager;

		@SpringBean
		private SemanticNetworkService networkService;

		// -----------------------------------------------------

		/**
		 * Default constructor.
		 */
		public RBEntityModel() {
			Injector.get().inject(this);
		}

		// ----------------------------------------------------

		@Override
		public RBEntity load() {
			final EntityHandle handle = RBWebSession.get().getHistory().getCurrentStep().getHandle();
			if (handle.hasId()) {
				LOGGER.debug("Loading RB Entity: " + handle.getId());
				final RBEntity loaded = entityManager.find(handle.getId());
				return loaded;
			} else if (handle.hasType()){
				LOGGER.debug("Creating new RB Entity");
				final RBEntity created = entityManager.create(handle.getType());
				handle.setId(created.getID());
				handle.markOnCreation();
				return created;
			} else {
				throw new IllegalStateException("Cannot initialize RB Entity Model.");
			}
		}
	}
}

