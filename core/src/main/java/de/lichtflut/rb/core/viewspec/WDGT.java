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

    ResourceID ENTITY_TREE = new SimpleResourceID(RBSystem.SYS_NAMESPACE_URI, "EnitityTreeWidget");
	
	ResourceID INFOVIS = new SimpleResourceID(RBSystem.SYS_NAMESPACE_URI, "InfoVisWidget");
	
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

    /**
     * A widget can display a content item. {@see ContentDisplayItem}
     */
    ResourceID DISPLAYS_CONTENT_ITEM = new SimpleResourceID(RBSystem.SYS_NAMESPACE_URI, "displaysContentItem");
	
	// -- SELECTION ---------------------------------------
	
	/**
	 * A widget has a selection of content.
	 */
	ResourceID HAS_SELECTION = new SimpleResourceID(RBSystem.SYS_NAMESPACE_URI, "hasSelection");

    /**
     * A selection by rdf:type.
     */
    ResourceID SELECT_BY_TYPE = new SimpleResourceID(RBSystem.SYS_NAMESPACE_URI, "selectByType");

    /**
     * A selection by a value.
     */
    ResourceID SELECT_BY_VALUE = new SimpleResourceID(RBSystem.SYS_NAMESPACE_URI, "selectByValue");

    /**
     * A selection by a relation.
     */
    ResourceID SELECT_BY_RELATION = new SimpleResourceID(RBSystem.SYS_NAMESPACE_URI, "selectByRelation");

    /**
     * A selection by a relation.
     */
    ResourceID SELECT_BY_QUERY = new SimpleResourceID(RBSystem.SYS_NAMESPACE_URI, "selectByQuery");

    /**
     * A selection by a relation.
     */
    ResourceID SELECT_BY_SCRIPT = new SimpleResourceID(RBSystem.SYS_NAMESPACE_URI, "selectByScript");

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
