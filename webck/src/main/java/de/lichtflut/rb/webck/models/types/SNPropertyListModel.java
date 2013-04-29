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

import de.lichtflut.rb.core.services.TypeManager;
import de.lichtflut.rb.webck.models.basic.AbstractLoadableDetachableModel;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.nodes.views.SNProperty;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * <p>
 *  Loadable list model for Properties.
 * </p>
 *
 * <p>
 * 	Created Dec 13, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class SNPropertyListModel extends AbstractLoadableDetachableModel<List<SNProperty>> {

	@SpringBean
	private TypeManager typeManager;
	
	// ----------------------------------------------------
	
	/**
	 * Constructor.
	 */
	public SNPropertyListModel() {
		Injector.get().inject(this);
	}
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public List<SNProperty> load() {
		final List<SNProperty> properties = typeManager.findAllProperties();
		Collections.sort(properties, new Comparator<SNProperty>() {
			@Override
			public int compare(SNProperty t1, SNProperty t2) {
				return t1.getQualifiedName().getSimpleName().compareTo(t2.getQualifiedName().getSimpleName());
			}
		});
		return properties;
	}
	
}
