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

import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.webck.config.QueryServicePathBuilder;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.ResourceID;

/**
 * <p>
 *  Search field.
 * </p>
 *
 * <p>
 * 	Created Dec 16, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class SearchField extends DataPickerField<ResourceID> {

	@SpringBean
	private QueryServicePathBuilder pathBuilder;
	
	@SpringBean
	private ServiceContext serviceContext;
	
	// ----------------------------------------------------

    /**
     * Constructor.
     * @param id The wicket ID.
     * @param entity The model containing a selected entity.
     * @param searchText The model containing the search text.
     */
    public SearchField(final String id, final IModel<ResourceID> entity, final IModel<String> searchText) {
        super(id, entity, searchText);
        setType(ResourceID.class);
        setSource(pathBuilder
                .create(serviceContext.getDomain())
                .queryEntities()
                .ofType(RBSystem.ENTITY.toURI())
                .toURI()
        );
        getSuggestLink().setVisible(false);
    }
}
