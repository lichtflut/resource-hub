/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SemanticGraph;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.persistence.TransactionControl;
import org.arastreju.sge.query.Query;
import org.arastreju.sge.query.QueryResult;
import org.arastreju.sge.structure.OrderBySerialNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.services.ArastrejuResourceFactory;
import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.core.services.ViewSpecificationService;
import de.lichtflut.rb.core.viewspec.MenuItem;
import de.lichtflut.rb.core.viewspec.Perspective;
import de.lichtflut.rb.core.viewspec.ViewPort;
import de.lichtflut.rb.core.viewspec.WDGT;
import de.lichtflut.rb.core.viewspec.WidgetSpec;
import de.lichtflut.rb.core.viewspec.impl.SNMenuItem;
import de.lichtflut.rb.core.viewspec.impl.SNPerspective;
import de.lichtflut.rb.core.viewspec.impl.SNWidgetSpec;
import de.lichtflut.rb.core.viewspec.impl.ViewSpecTraverser;

/**
 * <p>
 *  Implementation of {@link ViewSpecificationService}.
 * </p>
 *
 * <p>
 * 	Created Jan 30, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class ViewSpecificationServiceImpl implements ViewSpecificationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ViewSpecificationServiceImpl.class);

	private ServiceContext context;

	private ArastrejuResourceFactory arasFactory;

	// ----------------------------------------------------

	/**
	 * Default constructor.
	 */
	public ViewSpecificationServiceImpl() { }

	/**
	 * Constructor.
	 * @param context The service context.
	 * @param arasFactory The Arastreju resource factory providing conversations.
	 */
	public ViewSpecificationServiceImpl(final ServiceContext context, final ArastrejuResourceFactory arasFactory) {
		this.context = context;
		this.arasFactory = arasFactory;
	}

	// -- MENU ITEMS --------------------------------------

	@Override
	public List<MenuItem> getUsersMenuItems() {
		final ResourceNode user = currentUser();
		final List<MenuItem> result = new ArrayList<MenuItem>();
		for(Statement stmt : user.getAssociations()) {
			if (WDGT.HAS_MENU_ITEM.equals(stmt.getPredicate()) && stmt.getObject().isResourceNode()) {
				result.add(new SNMenuItem(stmt.getObject().asResource()));
			}
		}
		if (result.isEmpty()) {
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

	// -- PERSPECTIVES ------------------------------------

	@Override
	public Perspective findPerspective(final ResourceID id) {
		final ResourceNode existing = conversation().findResource(id.getQualifiedName());
		if (existing != null) {
			return new SNPerspective(existing);
		} else {
			return null;
		}
	}

	@Override
	public List<Perspective> findPerspectives() {
		final Query query = conversation().createQuery();
		query.addField(RDF.TYPE, WDGT.PERSPECTIVE);
		final QueryResult result = query.getResult();
		final List<Perspective> perspectives = new ArrayList<Perspective>(result.size());
		for (ResourceNode node : result) {
			perspectives.add(new SNPerspective(node));
		}
		return perspectives;
	}

	@Override
	public void store(final Perspective perspective) {
		conversation().attach(perspective);
		if (perspective.getViewPorts().isEmpty()) {
			// add two default view ports.
			perspective.addViewPort();
			perspective.addViewPort();
		}
	}

	@Override
	public void remove(final Perspective perspective) {
		final SemanticGraph graph = new ViewSpecTraverser().toGraph(perspective);
		final ModelingConversation mc = conversation();
		final TransactionControl tx = mc.beginTransaction();
		try {
			for (Statement stmt : graph.getStatements()) {
				mc.removeStatement(stmt);
			}
			tx.success();
		} finally {
			tx.finish();
		}
	}

	// -- WIDGETS -----------------------------------------

	@Override
	public WidgetSpec findWidgetSpec(final ResourceID id) {
		final ResourceNode existing = conversation().findResource(id.getQualifiedName());
		if (existing != null) {
			return new SNWidgetSpec(existing);
		} else {
			return null;
		}
	}

	@Override
	public void store(final WidgetSpec widgetSpec) {
		conversation().attach(widgetSpec);
	}

	// ----------------------------------------------------

	@Override
	public void store(final ViewPort viewPort) {
		conversation().attach(viewPort);
	}

	// ----------------------------------------------------

	/**
	 * Initialize the (probably new) user's menu items and dashboards.
	 */
	protected List<MenuItem> initializeDashboards(final ResourceNode user) {
		final List<MenuItem> result = new ArrayList<MenuItem>();
		final ResourceNode defaultMenu = conversation().resolve(WDGT.DEFAULT_MENU);
		for(Statement stmt : defaultMenu.getAssociations()) {
			if (WDGT.HAS_MENU_ITEM.equals(stmt.getPredicate()) && stmt.getObject().isResourceNode()) {
				final ResourceNode item = stmt.getObject().asResource();
				user.addAssociation(WDGT.HAS_MENU_ITEM, item);
				result.add(new SNMenuItem(item));
			}
		}
		return result;
	}

	protected ResourceNode currentUser() {
		final RBUser user = context.getUser();
		if (user == null) {
			throw new IllegalStateException("No user context set.");
		} else {
			return conversation().findResource(user.getQualifiedName());
		}
	}

	// ----------------------------------------------------

	private ModelingConversation conversation() {
		return arasFactory.getConversation(RBSystem.VIEW_SPEC_CTX);
	}

}
