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
package de.lichtflut.rb.core.data;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;

import de.lichtflut.rb.core.RB;

/**
 * <p>
 * This class provides several constants for testing purposes.
 * </p>
 * Created: May 7, 2012
 *
 * @author Ravi Knox
 */
public class RBTestConstants {

	public static final ResourceID ADDRESS = new SimpleResourceID(RB.COMMON_NAMESPACE_URI, "Address");

	public static final ResourceID HAS_FILE = new SimpleResourceID(RB.COMMON_NAMESPACE_URI, "hasFile");

}
