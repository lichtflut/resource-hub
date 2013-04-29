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
package de.lichtflut.rb.webck.data;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;

import de.lichtflut.rb.core.RB;



/**
 * <p>
 * Some constants for testing purposes.
 * </p>
 * Created: Jan 7, 2013
 *
 * @author Ravi Knox
 */
public interface RBTestConstants {

	ResourceID ADDRESS = new SimpleResourceID(RB.COMMON_NAMESPACE_URI, "Address");

	ResourceID SOFTWARE_ITEM = new SimpleResourceID(RB.COMMON_NAMESPACE_URI, "SoftwareItem");

	ResourceID DATA_CENTER = new SimpleResourceID(RB.COMMON_NAMESPACE_URI, "DataCenter");

	ResourceID PHYSICAL_MACHINE = new SimpleResourceID(RB.COMMON_NAMESPACE_URI, "PhysicalMachine");

	// ------------------------------------------------------

	ResourceID HAS_AVATAR = new SimpleResourceID(RB.COMMON_NAMESPACE_URI, "hasAvatar");

	ResourceID HOSTS_MACHINE = new SimpleResourceID(RB.COMMON_NAMESPACE_URI, "hostsMachine");

	ResourceID INHERITS_FROM = new SimpleResourceID(RB.COMMON_NAMESPACE_URI, "inheritsFrom");

	ResourceID HAS_CHILDREN = new SimpleResourceID(RB.COMMON_NAMESPACE_URI, "hasChildren");

}
