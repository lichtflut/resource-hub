/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.viewspec;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;

import de.lichtflut.rb.core.RB;

/**
 * <p>
 *  Constants for perspectives and widgets.
 * </p>
 *
 * <p>
 * 	Created Jan 23, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public interface WDGT {
	
	/**
	 * The layout of a perspective.
	 */
	ResourceID HAS_LAYOUT = new SimpleResourceID(RB.SYS_NAMESPACE_URI, "hasLayout");
	
	/**
	 * A view port of a perspective.
	 */
	ResourceID HAS_VIEW_PORT = new SimpleResourceID(RB.SYS_NAMESPACE_URI, "hasViewPort");
	
	/**
	 * A view port contains widgets.
	 */
	ResourceID CONTAINS_WIDGET = new SimpleResourceID(RB.SYS_NAMESPACE_URI, "containsWidget");
	
	// -- SELECTION ---------------------------------------
	
	/**
	 * A widget has a selection of content.
	 */
	ResourceID HAS_SELECTION = new SimpleResourceID(RB.SYS_NAMESPACE_URI, "hasSelection");
	
	/**
	 * A widget has a selection of content.
	 */
	ResourceID HAS_EXPRESSION = new SimpleResourceID(RB.SYS_NAMESPACE_URI, "hasExpression");
	
	/**
	 * A widget has a selection of content.
	 */
	ResourceID HAS_PARAMETER = new SimpleResourceID(RB.SYS_NAMESPACE_URI, "hasParameter");
	
	/**
	 * An expression may have a operator
	 */
	ResourceID HAS_OPERATOR = new SimpleResourceID(RB.SYS_NAMESPACE_URI, "hasOperator");
	
	/**
	 * A parameter may concern a single field.
	 */
	ResourceID CONCERNS_FIELD = new SimpleResourceID(RB.SYS_NAMESPACE_URI, "concernsField");
	
	/**
	 * The term of a parameter.
	 */
	ResourceID HAS_TERM = new SimpleResourceID(RB.SYS_NAMESPACE_URI, "hasTerm");
	
	/**
	 * Operator NOT.
	 */ 
	ResourceID NOT_OPERATOR = new SimpleResourceID(RB.SYS_NAMESPACE_URI, "NotOperator");
	
	/**
	 * Operator AND.
	 */
	ResourceID AND_OPERATOR = new SimpleResourceID(RB.SYS_NAMESPACE_URI, "AndOperator");
	
	/**
	 * Operator OR.
	 */
	ResourceID OR_OPERATOR = new SimpleResourceID(RB.SYS_NAMESPACE_URI, "OrOperator");

}
