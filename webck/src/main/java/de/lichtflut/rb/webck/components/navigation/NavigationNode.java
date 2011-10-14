/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.navigation;

import java.io.Serializable;
import java.util.List;

import org.apache.wicket.Component;

/**
 * <p>
 *  Node in a Navigation Tree.
 * </p>
 *
 * <p>
 * 	A Navigation Node has different states:
 * 	<ul>
 *	  	<li>visible: Is the node visible?</li>
 *		<li>enabled: Can the node be clicked/activated?</li>
 *		<li>expandable: Has the node (maybe enabled and visible) children?</li>
 *		<li>expanded: Is the node expanded?</li>
 *		<li>active: Is this node the last being clicked/activated?</li>
 *  </ul>
 * <p>
 * 	Created May 19, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public interface NavigationNode extends Serializable {
	/**
	 * Returns whether NavigationNode is visible or not.
	 * @return TRUE if node is Visible, FALSE if not
	 */
	boolean isVisible();
	/**
	 * Returns whether NavigationNode is enabled or not.
	 * @return TRUE if node is enabled, FALSE if not
	 */
	boolean isEnabled();
	/**
	 * Returns whether NavigationNode is expandable or not.
	 * @return TRUE if node is expandable, FALSE if not
	 */
	boolean isExpandable();
	/**
	 * Returns whether NavigationNode is expanded or not.
	 * @return TRUE if node is expanded, FALSE if not
	 */
	boolean isExpanded();
	/**
	 * Returns whether NavigationNode is active or not.
	 * @return TRUE if node is active, FALSE if not
	 */
	boolean isActive();
	/**
	 * Returns whether NavigationNode has children or not.
	 * @return TRUE if node has children, FALSE if not
	 */
	boolean hasChildren();

	/**
	 * Returns a List of all children.
	 * @return List of all children
	 */
	List<NavigationNode> getChildren();

	/**
	 * Returns the wicket component representing this node.
	 * @return The wicket component representing this node
	 */
	Component getComponent();

	/**
	 * Add a child node.
	 * @param node - The child node
	 * @return this
	 */
	NavigationNode addChild(NavigationNode node);

}
