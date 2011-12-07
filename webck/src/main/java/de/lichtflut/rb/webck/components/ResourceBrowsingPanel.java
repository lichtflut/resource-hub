/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.arastreju.sge.model.ResourceID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.core.api.EntityManager;
import de.lichtflut.rb.core.entity.EntityHandle;
import de.lichtflut.rb.core.entity.RBEntityReference;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.webck.application.BrowsingHistory;
import de.lichtflut.rb.webck.application.RBWebSession;
import de.lichtflut.rb.webck.behaviors.SlideTransitionBehavior;
import de.lichtflut.rb.webck.common.Action;
import de.lichtflut.rb.webck.common.EntityAttributeApplyAction;
import de.lichtflut.rb.webck.components.editor.BrowsingButtonBar;
import de.lichtflut.rb.webck.components.editor.EntityPanel;
import de.lichtflut.rb.webck.components.editor.IBrowsingHandler;
import de.lichtflut.rb.webck.components.editor.LocalButtonBar;
import de.lichtflut.rb.webck.models.BrowsingContextModel;
import de.lichtflut.rb.webck.models.RBEntityModel;

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
public abstract class ResourceBrowsingPanel extends Panel implements IBrowsingHandler {

	private final Logger logger = LoggerFactory.getLogger(ResourceBrowsingPanel.class);
	
	private RBEntityModel model;
	
	// ----------------------------------------------------

	/**
	 * @param id
	 */
	public ResourceBrowsingPanel(final String id) {
		super(id);
		
		model = new RBEntityModel() {
			@Override
			protected ServiceProvider getServiceProvider() {
				return ResourceBrowsingPanel.this.getServiceProvider();
			}
		};
		
		final Form form = new Form("form");
		form.setOutputMarkupId(true);
		
		form.add(new EntityPanel("entity", model, BrowsingContextModel.isInEditMode()));
		
		form.add(createLocalBar(form));
		form.add(createBrowsingBar(form));
		
		add(form);
		
		setOutputMarkupId(true);
		
		add(new SlideTransitionBehavior());
	}
	
	// ----------------------------------------------------
	
	public abstract ServiceProvider getServiceProvider();
	
	// -- IBrowsingHandler --------------------------------

	public abstract CharSequence getUrlToResource(EntityHandle handle);

	/** 
	* {@inheritDoc}
	*/
	public void browseTo(EntityHandle handle, boolean editable) {
		history().browseTo(handle);
		if (editable) {
			history().beginEditing();
		}
		addToAjax();
	}
	
	/** 
	* {@inheritDoc}
	*/
	@Override
	public void createSubEntity(EntityHandle handle, ResourceID predicate) {
		// store current entity
		getServiceProvider().getEntityManager().store(model.getObject());
		
		// navigate to sub entity
		final Action action = new EntityAttributeApplyAction(predicate);
		RBWebSession.get().getHistory().createReferencedEntity(handle, action);
		addToAjax();
	}
	
	// ----------------------------------------------------
	
	protected LocalButtonBar createLocalBar(Form form) {
		return new LocalButtonBar("localButtonBar", model, form) {
			@Override
			public EntityManager getEntityManager() {
				return getServiceProvider().getEntityManager();
			}
			
			@Override
			public void updateView() {
				addToAjax();
			}
		};
	}
	
	protected BrowsingButtonBar createBrowsingBar(Form form) {
		return new BrowsingButtonBar("browsingButtonBar", model, form) {
			@Override
			public void onSave() {
				getServiceProvider().getEntityManager().store(model.getObject());
				history().applyReferencedEntity(new RBEntityReference(model.getObject()));
				addToAjax();
			}
			
			@Override
			public void onCancel() {
				history().back();
				addToAjax();
			}
		};
	}
	
	// -- WICKET LIFECYLCE --------------------------------
	
	/** 
	* {@inheritDoc}
	*/
	@Override
	protected void onConfigure() {
		super.onConfigure();
		
		final EntityHandle currentEntity = history().getCurrentEntity();
		model.reset(currentEntity, history().getCurrentActions());
		logger.info("Showing " + currentEntity);
	}
	
	// -----------------------------------------------------

	private BrowsingHistory history() {
		return RBWebSession.get().getHistory();
	}
	
	private void addToAjax() {
		final AjaxRequestTarget target = AjaxRequestTarget.get();
		if (target != null) {
			target.add(this);
		}
	}

}
