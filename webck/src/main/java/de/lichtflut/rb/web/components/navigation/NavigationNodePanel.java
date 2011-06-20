/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.components.navigation;

import java.util.Collections;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import de.lichtflut.infra.exceptions.NotYetImplementedException;

/**
 * <p>
 *  Simple NavigationNode implementations which is the 'Component' itself.
 * </p>
 *
 * <p>
 * 	Created May 19, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
@SuppressWarnings("serial")
public class NavigationNodePanel extends Panel implements NavigationNode {

	/**
	 * Constructor.
	 * @param id The component ID.
	 * @param link The link (must have component ID 'link')
	 * @param label The label.
	 */
	public NavigationNodePanel(final String id, final AbstractLink link, final IModel<String> label) {
		super(id);
		add(link);
		link.add(new Label("label", label));
	}

	// -----------------------------------------------------

	/* (non-Javadoc)
	 * @see de.lichtflut.rb.web.components.navigation.NavigationNode#isExpandable()
	 */
	/**
	 * @return false /
	 */
	public boolean isExpandable() {
		return false;
	}

	/* (non-Javadoc)
	 * @see de.lichtflut.rb.web.components.navigation.NavigationNode#isExpanded()
	 */
	/**
	 * @return false /
	 */
	public boolean isExpanded() {
		return false;
	}

	/* (non-Javadoc)
	 * @see de.lichtflut.rb.web.components.navigation.NavigationNode#isActive()
	 */
	/**
	 * @return false /
	 */
	public boolean isActive() {
		return false;
	}

	/* (non-Javadoc)
	 * @see de.lichtflut.rb.web.components.navigation.NavigationNode#hasChildren()
	 */
	/**
	 * @return false /
	 */
	public boolean hasChildren() {
		return false;
	}

	/* (non-Javadoc)
	 * @see de.lichtflut.rb.web.components.navigation.NavigationNode#getChildren()
	 */
	/**
	 * @return /
	 */
	public List<NavigationNode> getChildren() {
		return Collections.emptyList();
	}

	/* (non-Javadoc)
	 * @see de.lichtflut.rb.web.components.navigation.NavigationNode#getComponent()
	 */
	/**
	 * @return /
	 */
	public Component getComponent() {
		return this;
	}

	/* (non-Javadoc)
	 * @see de.lichtflut.rb.web.components.navigation.NavigationNode#addChild(de.lichtflut.rb.web.components.navigation.NavigationNode)
	 */
	/**
	 * @param node /
	 * @return /
	 */
	public NavigationNode addChild(final NavigationNode node) {
		throw new NotYetImplementedException();
	}

}
