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
package de.lichtflut.rb.webck.models.types;

import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.services.SchemaManager;
import de.lichtflut.rb.webck.models.basic.AbstractLoadableDetachableModel;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  Loadable list model for Publid Type Definitions.
 * </p>
 *
 * <p>
 * 	Created Dec 13, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class PublicConstraintsListModel extends AbstractLoadableDetachableModel<List<Constraint>> {

	@SpringBean
	private SchemaManager schemaManager;
	
	// ----------------------------------------------------
	
	/**
	 * Constructor.
	 */
	public PublicConstraintsListModel() {
		Injector.get().inject(this);
	}
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public List<Constraint> load() {
		return new ArrayList<Constraint>(
				schemaManager.findPublicConstraints());
	}
	
}
