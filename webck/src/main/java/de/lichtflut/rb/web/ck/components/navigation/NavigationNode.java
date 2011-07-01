/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.ck.components.navigation;

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
	 *
	 * @return /
	 */
	boolean isVisible();
	/**
	 *
	 * @return /
	 */
	boolean isEnabled();
	/**
	 *
	 * @return /
	 */
	boolean isExpandable();
	/**
	 *
	 * @return /
	 */
	boolean isExpanded();
	/**
	 *
	 * @return /
	 */
	boolean isActive();
	/**
	 *
	 * @return /
	 */
	boolean hasChildren();

	/**
	 *
	 * @return /
	 */
	List<NavigationNode> getChildren();

	/**
	 * @return The wicket component representing this node.
	 */
	Component getComponent();

	/**
	 * Add a child node.
	 * @param node The child node.
	 * @return This.
	 */
	NavigationNode addChild(NavigationNode node);

}
