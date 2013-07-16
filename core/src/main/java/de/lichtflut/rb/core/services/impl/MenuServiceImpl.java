/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.lichtflut.rb.core.services.impl;

import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.common.Accessibility;
import de.lichtflut.rb.core.common.SerialNumberOrderedNodesContainer;
import de.lichtflut.rb.core.services.ConversationFactory;
import de.lichtflut.rb.core.services.MenuService;
import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.core.services.ViewSpecificationService;
import de.lichtflut.rb.core.viewspec.MenuItem;
import de.lichtflut.rb.core.viewspec.Perspective;
import de.lichtflut.rb.core.viewspec.ViewPort;
import de.lichtflut.rb.core.viewspec.WDGT;
import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.core.viewspec.impl.SNMenuItem;
import de.lichtflut.rb.core.viewspec.impl.SNPerspective;
import de.lichtflut.rb.core.viewspec.impl.SNViewPort;
import de.lichtflut.rb.core.viewspec.impl.SNWidgetSpec;
import de.lichtflut.rb.core.viewspec.impl.ViewSpecTraverser;
import org.arastreju.sge.Conversation;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SemanticGraph;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.naming.QualifiedName;
import org.arastreju.sge.persistence.TransactionControl;
import org.arastreju.sge.query.Query;
import org.arastreju.sge.query.QueryResult;
import org.arastreju.sge.structure.OrderBySerialNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 *  Implementation of {@link de.lichtflut.rb.core.services.MenuService}.
 * </p>
 *
 * <p>
 * 	Created Jan 30, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class MenuServiceImpl implements MenuService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MenuServiceImpl.class);

	private ServiceContext context;

	private ConversationFactory conversationFactory;

	// ----------------------------------------------------

	/**
	 * Default constructor.
	 */
	public MenuServiceImpl() { }

    /**
     * Constructor.
     * @param arasFactory The Arastreju resource factory providing conversations.
     */
    public MenuServiceImpl(final ConversationFactory arasFactory) {
        this.context = null;
        this.conversationFactory = arasFactory;
    }

	/**
     * Constructor.
     * @param context The service context.
     * @param arasFactory The Arastreju resource factory providing conversations.
     */
    public MenuServiceImpl(final ServiceContext context, final ConversationFactory arasFactory) {
        this.context = context;
        this.conversationFactory = arasFactory;
    }

	// -- MENU ITEMS --------------------------------------

	@Override
	public List<MenuItem> getMenuItemsForDisplay() {
		if (context != null && context.isAuthenticated()) {
			return getUsersMenuItems();
		} else {
			return getDefaultMenu();
		}
	}

	@Override
	public List<MenuItem> getUsersMenuItems() {
		final ResourceNode user = currentUser();
		final List<MenuItem> result = new ArrayList<MenuItem>();
		addUsersMenuItem(user, result);
		if (result.isEmpty() && user != null) {
			result.addAll(initializeDashboards(user));
		}
		Collections.sort(result, new OrderBySerialNumber());
		LOGGER.debug("Context: {}.", conversation().getConversationContext());
		LOGGER.debug("{} has menu items: {}.", user, result);

		return result;
	}

	@Override
	public void addUsersMenuItem(final MenuItem item) {
		final ResourceNode user = currentUser();
		store(item);
		user.addAssociation(WDGT.HAS_MENU_ITEM, item);
		LOGGER.debug("Added item {} to user {}.", item, user);
	}

	@Override
	public void store(final MenuItem item) {
		conversation().attach(item);
	}

	@Override
	public void removeUsersItem(final MenuItem item) {
		final ResourceNode user = currentUser();
		SNOPS.remove(user, WDGT.HAS_MENU_ITEM, item);
		LOGGER.debug("Context: {}.", conversation().getConversationContext());
		LOGGER.debug("Removed item {} from user {}.", item, user);
		LOGGER.debug("Left items of user {}: ", getUsersMenuItems());
		//TODO: Remove item if private one.
		//conversation.remove(item);
	}

	// ----------------------------------------------------

	protected ResourceNode currentUser() {
        if (context == null || context.getUser() == null) {
            throw new IllegalStateException("No user context set.");
		} else {
			return conversation().findResource(context.getUser().getQualifiedName());
		}
	}

	/**
	 * Initialize the (probably new) user's menu items and dashboards.
	 */
	protected List<MenuItem> initializeDashboards(final ResourceNode user) {
		final List<MenuItem> menuItems = getDefaultMenu();
		for (MenuItem item : menuItems) {
			user.addAssociation(WDGT.HAS_MENU_ITEM, item);
		}
		return menuItems;
	}

	/**
	 * Get the default menu items.
	 */
	protected List<MenuItem> getDefaultMenu() {
		final List<MenuItem> result = new ArrayList<MenuItem>();
		final ResourceNode defaultMenu = conversation().resolve(WDGT.DEFAULT_MENU);
		for(Statement stmt : defaultMenu.getAssociations()) {
			if (WDGT.HAS_MENU_ITEM.equals(stmt.getPredicate()) && stmt.getObject().isResourceNode()) {
				final ResourceNode item = stmt.getObject().asResource();
				result.add(new SNMenuItem(item));
			}
		}
		return result;
	}

	private void addUsersMenuItem(final ResourceNode user, final List<MenuItem> result) {
		if (user == null) {
			return;
		}
		for(Statement stmt : user.getAssociations()) {
			if (WDGT.HAS_MENU_ITEM.equals(stmt.getPredicate()) && stmt.getObject().isResourceNode()) {
				result.add(new SNMenuItem(stmt.getObject().asResource()));
			}
		}
	}

	// ----------------------------------------------------

	private Conversation conversation() {
		return conversationFactory.getConversation(RBSystem.VIEW_SPEC_CTX);
	}

}
