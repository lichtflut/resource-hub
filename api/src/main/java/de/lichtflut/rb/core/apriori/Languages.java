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
package de.lichtflut.rb.core.apriori;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;


/**
 * <p>
 *  A priori known names for geographical locations.
 * </p>
 *
 * <p>
 * 	Created Feb 17, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public interface Languages {
	
	String LANGUAGE_NAMESPACE_URI = "http://l2r.info/languages#";
	
	// ----------------------------------------------------
	
	ResourceID ENGLISH = new SimpleResourceID(LANGUAGE_NAMESPACE_URI, "Europe");
	
	ResourceID GERMAN = new SimpleResourceID(LANGUAGE_NAMESPACE_URI, "German");
	
	ResourceID FRENCH = new SimpleResourceID(LANGUAGE_NAMESPACE_URI, "French");
	
}
