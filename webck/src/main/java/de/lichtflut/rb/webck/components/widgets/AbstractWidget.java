/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.widgets;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.webck.models.ConditionalModel;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;

/**
 * <p>
 *  Base of all widgets.
 * </p>
 *
 * <p>
 * 	Created Jan 31, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class AbstractWidget extends Panel {

	private final ConditionalModel<Boolean> perspectiveInConfigMode;

	/**
	 * Constructor.
	 * @param id The component ID.
	 * @param perspectiveInConfigMode 
	 * @param model The title model.
	 */
	@SuppressWarnings("rawtypes")
	public AbstractWidget(final String id, final IModel<WidgetSpec> spec, ConditionalModel<Boolean> perspectiveInConfigMode) {
		super(id, spec);
		this.perspectiveInConfigMode = perspectiveInConfigMode;
		
		add(new Label("title", new DerivedDetachableModel<String, WidgetSpec>(spec) {
			@Override
			protected String derive(WidgetSpec original) {
				return original.getTitle();
			}
		}));
		
		add(new AjaxLink("removeWidget"){
			@Override
			public void onClick(AjaxRequestTarget target) {
				WidgetController controller = findParent(WidgetController.class);
				controller.removeWidget(spec.getObject());
			}
		}.add(visibleIf(perspectiveInConfigMode)));
		
	}
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	protected void onInitialize() {
		super.onInitialize();
		add(createConfigureLink("configureLink", perspectiveInConfigMode));
	}
	
	// ----------------------------------------------------
	
	protected Component createConfigureLink(String componentID, ConditionalModel<Boolean> perspectiveInConfigMode) {
		return new WebMarkupContainer(componentID).setVisible(false);
	}
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	protected void onDetach() {
		super.onDetach();
		perspectiveInConfigMode.detach();
	}
	
	
}