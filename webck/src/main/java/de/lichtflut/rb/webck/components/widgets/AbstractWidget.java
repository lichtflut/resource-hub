/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.widgets;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.areEqual;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.webck.common.DisplayMode;
import de.lichtflut.rb.webck.components.common.TypedPanel;
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
public abstract class AbstractWidget extends TypedPanel<WidgetSpec> {
	
	private final IModel<DisplayMode> mode = new Model<DisplayMode>(DisplayMode.VIEW);
	
	private final ConditionalModel<DisplayMode> isViewMode;
	
	private final ConditionalModel<DisplayMode> isEditMode;
	
	// ----------------------------------------------------

	/**
	 * The constructor.
	 * @param id The component ID.
	 * @param spec The widget specification.
	 */
	@SuppressWarnings("rawtypes")
	public AbstractWidget(String id, IModel<WidgetSpec> spec) {
		super(id, spec);
		
		setOutputMarkupId(true);
		
		this.isViewMode = areEqual(mode, DisplayMode.VIEW);
		this.isEditMode = areEqual(mode, DisplayMode.EDIT);
		
		final WebMarkupContainer display = createDisplayPane("display");
		display.add(new AjaxLink("configureLink") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				mode.setObject(DisplayMode.EDIT);
				target.add(AbstractWidget.this);
			}
		});
		display.add(visibleIf(isViewMode));
		add(display);
		
		final WebMarkupContainer config = createConfigurationPane("configuration", spec);
		config.add(visibleIf(isEditMode));
		add(config);
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
	
	protected WebMarkupContainer getDisplayPane() {
		return (WebMarkupContainer) get("display");
	}
	
	// ----------------------------------------------------
	
	protected boolean isEditMode() {
		return DisplayMode.EDIT.equals(mode.getObject());
	}
	
}
