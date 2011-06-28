/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.components.navigation;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

import de.lichtflut.rb.core.spi.RBServiceProvider;
import de.lichtflut.rb.web.ck.components.CKComponent;

/**
 * <p>
 *  A common navigation bar.
 * </p>
 *
 * <p>
 * 	Created May 19, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
@SuppressWarnings("serial")
public class NavigationBar extends CKComponent implements NavigationNode {

	private final List<NavigationNode> children = new ArrayList<NavigationNode>();

	// -----------------------------------------------------

	/**
	 * @param id The component ID.
	 */
	public NavigationBar(final String id) {
		super(id);

		final ListView<NavigationNode> itemView = new ListView<NavigationNode>("nodeList", children) {
			@Override
			protected void populateItem(final ListItem<NavigationNode> item) {
				Component comp = item.getModelObject().getComponent();
				item.add(comp);
			}
		};
		add(itemView);
	}

	// -----------------------------------------------------

	/* (non-Javadoc)
	 * @see de.lichtflut.rb.web.components.navigation.NavigationNode#isExpandable()
	 */
	/**
	 * @return boolean /
	 */
	@Override
	public boolean isExpandable() {
		return false;
	}

	/* (non-Javadoc)
	 * @see de.lichtflut.rb.web.components.navigation.NavigationNode#isExpanded()
	 */
	/**
	 * @return boolean
	 */
	@Override
	public boolean isExpanded() {
		return false;
	}

	/* (non-Javadoc)
	 * @see de.lichtflut.rb.web.components.navigation.NavigationNode#isActive()
	 */
	/**
	 * @return boolean
	 */
	@Override
	public boolean isActive() {
		return false;
	}

	/* (non-Javadoc)
	 * @see de.lichtflut.rb.web.components.navigation.NavigationNode#hasChildren()
	 */
	/**
	 * @return boolean /
	 */
	@Override
	public boolean hasChildren() {
		return !children.isEmpty();
	}

	/* (non-Javadoc)
	 * @see de.lichtflut.rb.web.components.navigation.NavigationNode#getChildren()
	 */
	/**
	 * @return children /
	 */
	@Override
	public List<NavigationNode> getChildren() {
		return children;
	}

	/* (non-Javadoc)
	 * @see de.lichtflut.rb.web.components.navigation.NavigationNode#getComponent()
	 */
	/**
	 * @return this /
	 */
	@Override
	public Component getComponent() {
		return this;
	}

	/* (non-Javadoc)
	 * @see de.lichtflut.rb.web.components.navigation.NavigationNode#addChild(de.lichtflut.rb.web.components.navigation.NavigationNode)
	 */
	/**
	 * @param node /
	 * @return this
	 */
	@Override
	public NavigationNode addChild(final NavigationNode node) {
		this.children.add(node);
		return this;
	}

	@Override
	protected void initComponent(final CKValueWrapperModel model) {
		// TODO Auto-generated method stub

	}

	@Override
	public RBServiceProvider getServiceProvider() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CKComponent setViewMode(final ViewMode mode) {
		// TODO Auto-generated method stub
		return null;
	}

}
