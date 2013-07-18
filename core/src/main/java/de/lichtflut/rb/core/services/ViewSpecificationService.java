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
package de.lichtflut.rb.core.services;

import java.util.List;

import de.lichtflut.rb.core.query.QueryContext;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.viewspec.Selection;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.viewspec.MenuItem;
import de.lichtflut.rb.core.viewspec.Perspective;
import de.lichtflut.rb.core.viewspec.ViewPort;
import de.lichtflut.rb.core.viewspec.WidgetSpec;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.naming.QualifiedName;
import org.arastreju.sge.query.QueryResult;

/**
 * <p>
 *  Service interface for view specifications and widgets.
 * </p>
 *
 * <p>
 * 	Created Jan 30, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public interface ViewSpecificationService {

    // -- PERSPECTIVES ------------------------------------

	/**
	 * Find a perspective specification by it's qualified name.
	 * @param qn The qualified name.
	 * @return The perspective specification or null if not found.
	 */
	Perspective findPerspective(QualifiedName qn);

	/**
	 * Initialize a perspective (e.g. add the view ports).
	 * @param id The ID.
     * @param owner The owner.
	 * @return The initialized perspective.
	 */
    Perspective initializePerspective(ResourceID id, RBUser owner);

	/**
	 * Find all perspectives accessible for the current user.
	 * @return Alle perspectives.
	 */
	List<Perspective> findPerspectives();

	/**
	 * Remove a perspective.
	 * @param qn The perspective to be removed.
	 */
	void remove(QualifiedName qn);

	/**
	 * Store a perspective.
	 * @param perspective The perspective to store.
	 */
	void store(Perspective perspective);

	// -- WIDGETS -----------------------------------------

	/**
	 * Find a widget specification by it's unique ID.
	 * @param id The ID.
	 * @return The widget specification or null if not found.
	 */
	WidgetSpec findWidgetSpec(ResourceID id);

	/**
	 * Store the widget specification. Update or create.
	 * @param widgetSpec The specification to store.
	 */
	void store(WidgetSpec widgetSpec);

	// -- VIEW PORTS --------------------------------------

	/**
	 * Find a view port by it's ID.
	 * @param id The view port id
	 * @return The attached view port.
	 */
	ViewPort findPort(ResourceID id);

	/**
	 * Store a view port. Update or create.
	 * @param viewPort The specification to store.
	 */
	void store(ViewPort viewPort);

	/**
	 * Move this widget one position up in it's view port.
	 * @param port The port of the widget.
	 * @param widgetSpec The widget spec to move up.
	 */
	void movePositionUp(ViewPort port, WidgetSpec widgetSpec);

	/**
	 * Move this widget one position down in it's view port.
	 * @param port The port of the widget.
	 * @param widgetSpec The widget spec to move down.
	 */
	void movePositionDown(ViewPort port, WidgetSpec widgetSpec);

	/**
	 * Remove this widget from it's view port and delete it.
	 * @param port The port of the widget.
	 * @param widgetSpec The widget spec to move down.
	 */
	void removeWidget(ViewPort port, WidgetSpec widgetSpec);

    // ----------------------------------------------------

    /**
     * Load data defined in a widget's selection.
     * @param widget The widget.
     * @param queryContext The context of this query containing information about current user, etc.
     * @return The query result.
     */
    QueryResult load(WidgetSpec widget, QueryContext queryContext);

}
