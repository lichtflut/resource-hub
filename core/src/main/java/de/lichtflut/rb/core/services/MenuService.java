package de.lichtflut.rb.core.services;

import de.lichtflut.rb.core.viewspec.MenuItem;

import java.util.List;

/**
 * <p>
 *  Service for first level menu definition.
 * </p>
 *
 * <p>
 *  Created Jan 30, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public interface MenuService {

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
}
