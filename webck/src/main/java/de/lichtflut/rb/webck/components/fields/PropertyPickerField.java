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
package de.lichtflut.rb.webck.components.fields;

import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.webck.config.QueryServicePathBuilder;
import de.lichtflut.rb.webck.models.resources.ResourceDisplayModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.ResourceID;

/**
 * <p>
 * 	Picker field for rdf:Properties.
 * </p>
 *
 * <p>
 * 	Created Dec 14, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class PropertyPickerField extends DataPickerField<ResourceID> {

    @SpringBean
    private QueryServicePathBuilder pathBuilder;

    @SpringBean
    private ServiceContext serviceContext;

    // ----------------------------------------------------

	/**
	 * Constructor.
	 * @param id The component ID.
	 * @param model The model.
	 */
	public PropertyPickerField(final String id, final IModel<ResourceID> model) {
		super(id, model, new ResourceDisplayModel(model));
		setType(ResourceID.class);
        setSource(findProperty());
	}
	
	// -----------------------------------------------------
	
	public String findProperty() {
        return pathBuilder.create(serviceContext.getDomain()).queryProperties().toString();
	}
	
}
