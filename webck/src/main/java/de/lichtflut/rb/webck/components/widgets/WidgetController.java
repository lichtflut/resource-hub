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
package de.lichtflut.rb.webck.components.widgets;

import de.lichtflut.rb.core.viewspec.WidgetSpec;

/**
 * <p>
 *  A controller for widgets.
 * </p>
 *
 * <p>
 * 	Created Feb 6, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public interface WidgetController {
	
	/**
	 * Remove a widget.
	 * @param widget The widget to be removed.
	 */
	void remove(WidgetSpec widget);
	
	/**
	 * Move a widget one position up
	 * @param widget The widget to be moved.
	 */
	void moveUp(WidgetSpec widget);
	
	/**
	 * Move a widget one position down
	 * @param widget The widget to be moved.
	 */
	void moveDown(WidgetSpec widget);


}
