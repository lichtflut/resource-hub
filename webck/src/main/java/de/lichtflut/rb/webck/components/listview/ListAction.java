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
package de.lichtflut.rb.webck.components.listview;

/**
 * <p>
 *  Enumeration of standard actions on lists.
 * </p>
 *
 * <p>
 * 	Created Nov 17, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public enum ListAction {

	VIEW,
	EDIT,
	DELETE,
	CUSTOM;
	
	static ListAction forName(final String action) {
		if (VIEW.name().equals(action)) {
			return VIEW;
		} else if (EDIT.name().equals(action)) {
			return EDIT;
		} else if (DELETE.name().equals(action)) {
			return DELETE;
		}
		return CUSTOM;
	}
	
}
