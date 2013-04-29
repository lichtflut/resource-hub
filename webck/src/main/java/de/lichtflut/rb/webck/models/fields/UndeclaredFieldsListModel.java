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
package de.lichtflut.rb.webck.models.fields;

import de.lichtflut.rb.core.entity.ResourceField;
import de.lichtflut.rb.webck.components.listview.ColumnConfiguration;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;
import org.apache.wicket.model.IModel;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  Model of undeclared entity fields derived from a ResourceNode and a ColumnConfiguration.
 * </p>
 *
 * <p>
 * 	Created Dec 15, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class UndeclaredFieldsListModel extends DerivedDetachableModel<List<ResourceField>, ResourceNode> {

	private final ColumnConfiguration config;
	
	// ----------------------------------------------------

	/**
	 * Constructor
	 * @param source The source model.
	 * @param config The column configuration.
	 */
	public UndeclaredFieldsListModel(IModel<ResourceNode> source, final ColumnConfiguration config) {
		super(source);
		this.config = config;
	}

	// ----------------------------------------------------
	
	/** 
	* {@inheritDoc}
	*/
	@Override
	public List<ResourceField> derive(final ResourceNode subject) {
		final List<ResourceField> result = new ArrayList<ResourceField>();
		for(ResourceID predicate : config.getPredicatesToDisplay()) {
			result.add(new ResourceField(predicate, SNOPS.objects(subject, predicate)));
		}
		return result;
	}

}
