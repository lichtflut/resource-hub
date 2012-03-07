/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.viewspec;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;

import de.lichtflut.rb.core.RBSystem;

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
	
	String DEFAULT_LAYOUT = "layout-50-50";
	
	String VIEWS_NAMESPACE_URI = "http://rb.lichtflut.de/view-specifications#";
	
	// -- CONSTANT RESOURCE IDS ---------------------------
	
	ResourceID DEFAULT_MENU = new SimpleResourceID(VIEWS_NAMESPACE_URI,  "DefaultMenu");
	
	// -- TYPES -------------------------------------------
	
	ResourceID PERSPECTIVE = new SimpleResourceID(RBSystem.SYS_NAMESPACE_URI,  "Perspective");
	
	ResourceID VIEW_PORT =new SimpleResourceID(RBSystem.SYS_NAMESPACE_URI,  "ViewPort");
	
	ResourceID WIDGET = new SimpleResourceID(RBSystem.SYS_NAMESPACE_URI, "Widget");
	
	
	ResourceID LAYOUT = new SimpleResourceID(RBSystem.SYS_NAMESPACE_URI, "Layout");
	
	ResourceID COLUMN_DEF = new SimpleResourceID(RBSystem.SYS_NAMESPACE_URI,  "EntityListColumnDefinition");
	
	ResourceID MENU_ITEM = new SimpleResourceID(RBSystem.SYS_NAMESPACE_URI,  "MenuItem");
	
	ResourceID ACTION_INSTANTIATE = new SimpleResourceID(RBSystem.SYS_NAMESPACE_URI,  "InstantiateAction");
	
	// -- WIDGET TYPES ------------------------------------
	
	ResourceID PREDEFINED = new SimpleResourceID(RBSystem.SYS_NAMESPACE_URI, "PredefinedWidget");
	
	ResourceID ENTITY_DETAILS = new SimpleResourceID(RBSystem.SYS_NAMESPACE_URI, "EntityDetailsWidget");
	
	ResourceID ENTITY_LIST = new SimpleResourceID(RBSystem.SYS_NAMESPACE_URI, "EnitityListWidget");
	
	ResourceID INVOVIS = new SimpleResourceID(RBSystem.SYS_NAMESPACE_URI, "InfoVisWidget");
	
	// -- PROPERTIES --------------------------------------
	
	/**
	 * A user has menu items.
	 */
	ResourceID HAS_MENU_ITEM = new SimpleResourceID(RBSystem.SYS_NAMESPACE_URI, "hasMenuItem");
	
	/**
	 * The layout of a perspective.
	 */
	ResourceID HAS_LAYOUT = new SimpleResourceID(RBSystem.SYS_NAMESPACE_URI, "hasLayout");
	
	/**
	 * A view port of a perspective.
	 */
	ResourceID HAS_VIEW_PORT = new SimpleResourceID(RBSystem.SYS_NAMESPACE_URI, "hasViewPort");
	
	/**
	 * A view port contains widgets.
	 */
	ResourceID CONTAINS_WIDGET = new SimpleResourceID(RBSystem.SYS_NAMESPACE_URI, "containsWidget");
	
	/**
	 * A Menu item shows a perspective.
	 */
	ResourceID SHOWS_PERSPECTIVE = new SimpleResourceID(RBSystem.SYS_NAMESPACE_URI, "showsPerspective");
	
	// -- SELECTION ---------------------------------------
	
	/**
	 * A widget has a selection of content.
	 */
	ResourceID HAS_SELECTION = new SimpleResourceID(RBSystem.SYS_NAMESPACE_URI, "hasSelection");
	
	/**
	 * A widget has a selection of content.
	 */
	ResourceID HAS_EXPRESSION = new SimpleResourceID(RBSystem.SYS_NAMESPACE_URI, "hasExpression");
	
	/**
	 * A widget has a selection of content.
	 */
	ResourceID HAS_PARAMETER = new SimpleResourceID(RBSystem.SYS_NAMESPACE_URI, "hasParameter");
	
	/**
	 * An expression may have a operator
	 */
	ResourceID HAS_OPERATOR = new SimpleResourceID(RBSystem.SYS_NAMESPACE_URI, "hasOperator");
	
	/**
	 * A parameter may concern a single field.
	 */
	ResourceID CONCERNS_FIELD = new SimpleResourceID(RBSystem.SYS_NAMESPACE_URI, "concernsField");
	
	/**
	 * The term of a parameter.
	 */
	ResourceID HAS_TERM = new SimpleResourceID(RBSystem.SYS_NAMESPACE_URI, "hasTerm");
	
	/**
	 * Operator NOT.
	 */ 
	ResourceID NOT_OPERATOR = new SimpleResourceID(RBSystem.SYS_NAMESPACE_URI, "NotOperator");
	
	/**
	 * Operator AND.
	 */
	ResourceID AND_OPERATOR = new SimpleResourceID(RBSystem.SYS_NAMESPACE_URI, "AndOperator");
	
	/**
	 * Operator OR.
	 */
	ResourceID OR_OPERATOR = new SimpleResourceID(RBSystem.SYS_NAMESPACE_URI, "OrOperator");

	// -- COLUMNS -----------------------------------------
	
	/**
	 * A widget can define a column.
	 */
	ResourceID DEFINES_COLUMN = new SimpleResourceID(RBSystem.SYS_NAMESPACE_URI, "definesColumn");
	
	/**
	 * A column corresponds to a predicate/property.
	 */
	ResourceID CORRESPONDS_TO_PROPERTY = new SimpleResourceID(RBSystem.SYS_NAMESPACE_URI, "correspondsToProperty");
	
	/**
	 * A column corresponds to a predicate/property.
	 */
	ResourceID HAS_HEADER = new SimpleResourceID(RBSystem.SYS_NAMESPACE_URI, "hasHeader");
	
	// -- ACTIONS -----------------------------------------
	
	/**
	 * A widget can support actions.
	 */
	ResourceID SUPPORTS_ACTION = new SimpleResourceID(RBSystem.SYS_NAMESPACE_URI, "supportsAction");
	
	/**
	 * Action to create instance of referenced type.
	 */
	ResourceID CREATE_INSTANCE_OF = new SimpleResourceID(RBSystem.SYS_NAMESPACE_URI, "createInstanceOf");
	
	
	// -- PREDEFINED --------------------------------------
	
	/**
	 * The full qualified class name of the Wicket component implementing a predefined widget.
	 */
	ResourceID IS_IMPLEMENTED_BY_CLASS = new SimpleResourceID(RBSystem.SYS_NAMESPACE_URI, "isImplementedByClass");
	
}
