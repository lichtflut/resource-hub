/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.widgets;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.and;
import static de.lichtflut.rb.webck.models.ConditionalModel.areEqual;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.webck.common.DisplayMode;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.models.ConditionalModel;

/**
 * <p>
 *  Abstract widget panel.
 * </p>
 *
 * <p>
 * 	Created Jan 20, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class ConfigurableWidget extends AbstractWidget {
	
	private final IModel<DisplayMode> mode = new Model<DisplayMode>(DisplayMode.VIEW);
	
	private final ConditionalModel<DisplayMode> isViewMode = areEqual(mode, DisplayMode.VIEW);
	
	private final ConditionalModel<DisplayMode> isEditMode = areEqual(mode, DisplayMode.EDIT);
	
	// ----------------------------------------------------

	/**
	 * The constructor.
	 * @param id The component ID.
	 * @param spec The widget specification.
	 * @param perspectiveInConfigMode Conditional whether the perspective is in configuration mode.
	 */
	public ConfigurableWidget(String id, IModel<WidgetSpec> spec, ConditionalModel<Boolean> perspectiveInConfigMode) {
		super(id, spec, perspectiveInConfigMode);
		
		setOutputMarkupId(true);
		
		final WebMarkupContainer display = createDisplayPane("display");
		display.add(visibleIf(isViewMode));
		add(display);
		
		final WebMarkupContainer config = createConfigurationPane("configuration", spec);
		config.add(visibleIf(isEditMode));
		add(config);
	}
	
	// ----------------------------------------------------
	
	public boolean isEditMode() {
		return DisplayMode.EDIT.equals(mode.getObject());
	}
	
	public void switchToDisplay() {
		this.mode.setObject(DisplayMode.VIEW);
		RBAjaxTarget.add(this);
	}
	
	public void switchToConfiguration() {
		this.mode.setObject(DisplayMode.EDIT);
		RBAjaxTarget.add(this);
	}
	
	// ----------------------------------------------------
	
	/**
	 * To be implemented by subclasses.
	 * @param componentID The ID.
	 * @param spec The specification.
	 * @return The panel for widget configuration. 
	 */
	protected abstract WebMarkupContainer createConfigurationPane(String componentID, IModel<WidgetSpec> spec);
	
	/**
	 * @param componentID The ID.
	 * @return The panel for displaying the widget data. 
	 */
	protected WebMarkupContainer createDisplayPane(String componentID) {
		final WebMarkupContainer display = new WebMarkupContainer(componentID);
		display.setOutputMarkupId(true);
		return display;
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@SuppressWarnings("rawtypes")
	@Override
	protected Component createConfigureLink(String componentID, ConditionalModel<Boolean> perspectiveInConfigMode) {
		
		final AjaxLink configLink = new AjaxLink("configureLink") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				switchToConfiguration();
			}
		};
		configLink.add(visibleIf(and(perspectiveInConfigMode, isViewMode)));
		return configLink;
	}
	
	// ----------------------------------------------------
	
	protected WebMarkupContainer getDisplayPane() {
		return (WebMarkupContainer) get("display");
	}
	
}
