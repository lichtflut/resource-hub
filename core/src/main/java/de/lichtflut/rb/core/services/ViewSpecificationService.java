/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services;

import java.util.List;

import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.viewspec.MenuItem;
import de.lichtflut.rb.core.viewspec.Perspective;
import de.lichtflut.rb.core.viewspec.ViewPort;
import de.lichtflut.rb.core.viewspec.WidgetSpec;

/**
 * <p>
 *  Service interface for view specifications and widgets.
 * </p>
 *
 * <p>
 * 	Created Jan 30, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public interface ViewSpecificationService {
	
	// -- MENU ITEMS --------------------------------------

    /**
     * Get the menu items to be displayed for current user context.
     * Supports unauthenticated users.
     * @return The menu items list.
     */
    List<MenuItem> getMenuItemsForDisplay();
	
	/**
	 * Get the menu items of the current user.
	 * @return The menu items list.
	 */
	List<MenuItem> getUsersMenuItems();
	
	/**
	 * Add a menu item for the current user.
	 * @param item The item.
	 */
	void addUsersMenuItem(MenuItem item);
	
	/**
	 * Remove a menu item of the current user.
	 * @param item The item to be removed.
	 */
	void removeUsersItem(MenuItem item);
	
	/**
	 * Store a menu item.
	 * @param item The item.
	 */
	void store(MenuItem item);
	
	// -- PERSPECTIVES ------------------------------------
	
	/**
	 * Find a perspective specification by it's unique ID.
	 * @param id The ID.
	 * @return The perspective specification or null if not found.
	 */
	Perspective findPerspective(ResourceID id);

    /**
     * Find all perspectives accessible for the current user.
     * @return Alle perspectives.
     */
    List<Perspective> findPerspectives();
	
	/**
	 * Remove a perspective.
	 * @param perspective The perspective to be removed.
	 */
	void remove(Perspective perspective);
	
	/**
	 * Store a perspective.
	 * @param perspective The perspective to store.
	 */
	void store(Perspective perspective);
	
	// -- WIDGETS -----------------------------------------
	
	/**
	 * Find a widget specification by it's unique ID.
	 * @param id The ID.
	 * @return The widget specification or null if not found.
	 */
	WidgetSpec findWidgetSpec(ResourceID id);
	
	/**
	 * Store the widget specification. Update or create.
	 * @param widgetSpec The specification to store.
	 */
	void store(WidgetSpec widgetSpec);

	// ----------------------------------------------------

    /**
     * Find a view port by it's ID.
     * @param id The view port id
     * @return The attached view port.
     */
    ViewPort findPort(ResourceID id);
	
	/**
	 * Store a view port. Update or create.
	 * @param viewPort The specification to store.
	 */
	void store(ViewPort viewPort);

    /**
     * Move this widget one position up in it's view port.
     * @param port The port of the widget.
     * @param widgetSpec The widget spec to move up.
     */
    void movePositionUp(ViewPort port, WidgetSpec widgetSpec);

    /**
     * Move this widget one position down in it's view port.
     * @param port The port of the widget.
     * @param widgetSpec The widget spec to move down.
     */
    void movePositionDown(ViewPort port, WidgetSpec widgetSpec);

    /**
     * Remove this widget from it's view port and delete it.
     * @param port The port of the widget.
     * @param widgetSpec The widget spec to move down.
     */
    void removeWidget(ViewPort port, WidgetSpec widgetSpec);


}
