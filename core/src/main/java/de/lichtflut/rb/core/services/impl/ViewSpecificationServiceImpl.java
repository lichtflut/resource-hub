/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SemanticGraph;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.persistence.TransactionControl;
import org.arastreju.sge.structure.OrderBySerialNumber;

import de.lichtflut.rb.core.services.ServiceProvider;
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
public class ViewSpecificationServiceImpl extends AbstractService implements ViewSpecificationService {

	/**
	 * @param provider
	 */
	public ViewSpecificationServiceImpl(final ServiceProvider provider) {
		super(provider);
	}
	
	// -- MENU ITEMS --------------------------------------

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public List<MenuItem> getUsersMenuItems() {
		final ResourceNode user = currentUser();
		if (user == null) {
			return Collections.emptyList();
		}
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
		return result;
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void addUsersMenuItem(MenuItem item) {
		final ResourceNode user = currentUser();
		if (user != null) {
			store(item);
			user.addAssociation(WDGT.HAS_MENU_ITEM, item);
		}
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void store(MenuItem item) {
		mc().attach(item);
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void removeUsersItem(MenuItem item) {
		final ResourceNode user = currentUser();
		SNOPS.remove(user, WDGT.HAS_MENU_ITEM, item);
		//TODO: Remove item if private one.
		//mc().remove(item);
	}
	
	// -- PERSPECTIVES ------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public Perspective findPerspective(ResourceID id) {
		final ResourceNode existing = mc().findResource(id.getQualifiedName());
		if (existing != null) {
			return new SNPerspective(existing);
		} else {
			return null;
		}
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void store(Perspective perspective) {
		mc().attach(perspective);
		if (perspective.getViewPorts().isEmpty()) {
			// add two default view ports.
			perspective.addViewPort();
			perspective.addViewPort();
		}
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void remove(Perspective perspective) {
		final SemanticGraph graph = new ViewSpecTraverser().toGraph(perspective);
		final ModelingConversation mc = mc();
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

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public WidgetSpec findWidgetSpec(ResourceID id) {
		final ResourceNode existing = mc().findResource(id.getQualifiedName());
		if (existing != null) {
			return new SNWidgetSpec(existing);
		} else {
			return null;
		}
	}

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void store(WidgetSpec widgetSpec) {
		mc().attach(widgetSpec);
	}
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void store(ViewPort viewPort) {
		mc().attach(viewPort);
	}
	
	// ----------------------------------------------------
	
	/**
	 * Initialize the (probably new) user's menu items and dashboards.
	 */
	protected List<MenuItem> initializeDashboards(ResourceNode user) {
		final List<MenuItem> result = new ArrayList<MenuItem>();
		final ResourceNode defaultMenu = getProvider().getResourceResolver().resolve(WDGT.DEFAULT_MENU);
		for(Statement stmt : defaultMenu.getAssociations()) {
			if (WDGT.HAS_MENU_ITEM.equals(stmt.getPredicate()) && stmt.getObject().isResourceNode()) {
				final ResourceNode item = stmt.getObject().asResource();
				user.addAssociation(WDGT.HAS_MENU_ITEM, item);
				result.add(new SNMenuItem(item));
			}
		}
		return result;
	}
	
}
