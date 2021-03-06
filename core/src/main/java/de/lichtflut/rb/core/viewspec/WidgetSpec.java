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

import java.util.List;

import de.lichtflut.rb.core.viewspec.impl.SNWidgetAction;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;

/**
 * <p>
 * 	Definition of a widget.
 * </p>
 * 
 * <p>
 * 	Created Jan 19, 2012
 * </p>
 * 
 * @author Oliver Tigges
 */
public interface WidgetSpec extends ResourceNode {

	ResourceID getID();

	String getTitle();

	void setTitle(String title);

	String getDescription();

	void setDescription(String desc);

	// -- The widget's position in it's port --------------

	Integer getPosition();

	void setPosition(Integer position);

	// -- selected content ----

	Selection getSelection();

	void setSelection(Selection selection);

	// -- referenced content -

	String getContentID();

	void setContentID(String contentID);

	// ----------------------------------------------------

	List<WidgetAction> getActions();

    void addAction(WidgetAction action);

    // ----------------------------------------------------

    List<ColumnDef> getColumns();

    void addColumn(ColumnDef column);

}
