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
package de.lichtflut.rb.core.schema.custom;

import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.common.EntityLabelBuilder;

/**
 * <p>
 *  Locator for {@link EntityLabelBuilder}s.
 * </p>
 *
 * <p>
 * 	Created Sep 2, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public interface LabelBuilderLocator {

	/**
	 * Locates the label builder for the given type.
	 * @param type The resource type.
	 * @return The corresponding label builder.
	 */
	EntityLabelBuilder forType(ResourceID type);

}
