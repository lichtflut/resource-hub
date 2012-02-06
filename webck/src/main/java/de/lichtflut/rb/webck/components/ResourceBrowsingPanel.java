/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;

import org.apache.wicket.Component;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.entity.EntityHandle;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.webck.application.RBWebSession;
import de.lichtflut.rb.webck.browsing.EntityAttributeApplyAction;
import de.lichtflut.rb.webck.browsing.ReferenceReceiveAction;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.editor.BrowsingButtonBar;
import de.lichtflut.rb.webck.components.editor.ClassifyEntityPanel;
import de.lichtflut.rb.webck.components.editor.EntityPanel;
import de.lichtflut.rb.webck.components.editor.IBrowsingHandler;
import de.lichtflut.rb.webck.components.editor.LocalButtonBar;
import de.lichtflut.rb.webck.components.relationships.CreateRelationshipPanel;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import de.lichtflut.rb.webck.models.BrowsingContextModel;
import de.lichtflut.rb.webck.models.entity.RBEntityModel;

/**
 * <p>
 *  Panel for placing a resource browser panel into a page.
 * </p>
 *
 * <p>
 * 	Created Dec 5, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
@SuppressWarnings("rawtypes")
public class ResourceBrowsingPanel extends Panel implements IBrowsingHandler {

	private RBEntityModel model;
	
	@SpringBean
	private ServiceProvider provider;
	
	// ----------------------------------------------------

	/**
	 * Constructor.
	 * @param id The component ID.
	 */
	public ResourceBrowsingPanel(final String id) {
		super(id);
		
		model = new RBEntityModel() {
			@Override
			protected ServiceProvider getServiceProvider() {
				return provider;
			}
		};
		
		final Form form = new Form("form");
		form.setOutputMarkupId(true);
		
		form.add(new EntityPanel("entity", model));
		form.add(new ClassifyEntityPanel("classifier", model));
		
		form.add(new LocalButtonBar("localButtonBar", model));
		form.add(new BrowsingButtonBar("browsingButtonBar", model));
		
		form.add(new CreateRelationshipPanel("relationCreator", model)
			.add(visibleIf(BrowsingContextModel.isInViewMode())));
		
		form.add(createRelationshipView("relationships", model));
		
		add(form);
		
		setOutputMarkupId(true);
	}
	
	// -- IBrowsingHandler --------------------------------

	/** 
	* {@inheritDoc}
	*/
	@Override
	public void createReferencedEntity(EntityHandle handle, ResourceID predicate) {
		// store current entity 
		// TODO: this should be stored transient in session / browsing history.
		provider.getEntityManager().store(model.getObject());
		
		// navigate to sub entity
		final ReferenceReceiveAction action = new EntityAttributeApplyAction(model.getObject(), predicate);
		RBWebSession.get().getHistory().createReference(handle, action);
	}
	
	// ----------------------------------------------------
	
	protected Component createRelationshipView(String id, IModel<RBEntity> model) {
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
	
}
