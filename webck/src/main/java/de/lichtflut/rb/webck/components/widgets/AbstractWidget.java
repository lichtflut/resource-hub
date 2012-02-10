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
import org.apache.wicket.model.IModel;

import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.webck.components.common.TypedPanel;
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
public class AbstractWidget extends TypedPanel<WidgetSpec> {

	private final ConditionalModel<Boolean> perspectiveInConfigMode;
	
	// ----------------------------------------------------

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
		
		add(new Label("title", getTitleModel()));
		
		add(new AjaxLink("removeWidget"){
			@Override
			public void onClick(AjaxRequestTarget target) {
				WidgetController controller = findParent(WidgetController.class);
				controller.remove(spec.getObject());
			}
		}.add(visibleIf(perspectiveInConfigMode)));
		
		
		add(new AjaxLink("up"){
			@Override
			public void onClick(AjaxRequestTarget target) {
				WidgetController controller = findParent(WidgetController.class);
				controller.moveUp(spec.getObject());
			}
		}.add(visibleIf(perspectiveInConfigMode)));
		
		add(new AjaxLink("down"){
			@Override
			public void onClick(AjaxRequestTarget target) {
				WidgetController controller = findParent(WidgetController.class);
				controller.moveDown(spec.getObject());
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
	
	protected IModel<String> getTitleModel() {
		return new DerivedDetachableModel<String, WidgetSpec>(getModel()) {
			@Override
			protected String derive(WidgetSpec original) {
				return original.getTitle();
			}
		};
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