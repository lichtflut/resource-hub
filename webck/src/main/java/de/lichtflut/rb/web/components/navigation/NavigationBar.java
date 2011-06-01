/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.components.navigation;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;

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
public class NavigationBar extends Panel implements NavigationNode {

	private final List<NavigationNode> children = new ArrayList<NavigationNode>();
	
	// -----------------------------------------------------
	
	/**
	 * @param id The component ID.
	 */
	public NavigationBar(final String id) {
		super(id);
		
		final ListView<NavigationNode> itemView = new ListView<NavigationNode>("nodeList", children) {
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
	public boolean isExpandable() {
		return false;
	}

	/* (non-Javadoc)
	 * @see de.lichtflut.rb.web.components.navigation.NavigationNode#isExpanded()
	 */
	public boolean isExpanded() {
		return false;
	}

	/* (non-Javadoc)
	 * @see de.lichtflut.rb.web.components.navigation.NavigationNode#isActive()
	 */
	public boolean isActive() {
		return false;
	}

	/* (non-Javadoc)
	 * @see de.lichtflut.rb.web.components.navigation.NavigationNode#hasChildren()
	 */
	public boolean hasChildren() {
		return !children.isEmpty();
	}

	/* (non-Javadoc)
	 * @see de.lichtflut.rb.web.components.navigation.NavigationNode#getChildren()
	 */
	public List<NavigationNode> getChildren() {
		return children;
	}

	/* (non-Javadoc)
	 * @see de.lichtflut.rb.web.components.navigation.NavigationNode#getComponent()
	 */
	public Component getComponent() {
		return this;
	}
	
	/* (non-Javadoc)
	 * @see de.lichtflut.rb.web.components.navigation.NavigationNode#addChild(de.lichtflut.rb.web.components.navigation.NavigationNode)
	 */
	public NavigationNode addChild(final NavigationNode node) {
		this.children.add(node);
		return this;
	}

}
