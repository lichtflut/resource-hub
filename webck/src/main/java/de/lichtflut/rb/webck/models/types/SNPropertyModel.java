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
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.views.SNProperty;
import org.arastreju.sge.naming.QualifiedName;

/**
 * <p>
 *  Loadable model for a Property.
 * </p>
 *
 * <p>
 * 	Created Mar 16, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class SNPropertyModel extends AbstractLoadableDetachableModel<SNProperty> {

	@SpringBean
	private TypeManager typeManager;
	
	private final QualifiedName qn;
	
	// ----------------------------------------------------
	
	/**
	 * Constructor.
	 */
	public SNPropertyModel(final ResourceID id) {
		this.qn = id.getQualifiedName();
		Injector.get().inject(this);
	}
	
	/**
	 * Constructor.
	 */
	public SNPropertyModel(final QualifiedName qn) {
		this.qn = qn;
		Injector.get().inject(this);
	}
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public SNProperty load() {
		return typeManager.findProperty(qn);
	}
	
}
