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
package de.lichtflut.rb.webck.components.dialogs;

import de.lichtflut.rb.core.services.TypeManager;
import de.lichtflut.rb.webck.behaviors.TitleModifier;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.nodes.views.SNProperty;
import org.arastreju.sge.naming.QualifiedName;

/**
 * <p>
 *  Dialog for creation of a new rdf:Property.
 * </p>
 *
 * <p>
 * 	Created Dec 13, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class CreatePropertyDialog extends AbstractCreateResourceDialog {

	@SpringBean
	private TypeManager typeManager;
	
	/**
	 * @param id
	 */
	public CreatePropertyDialog(String id) {
		super(id);
		
		add(TitleModifier.title(new ResourceModel("global.dialogs.create-property.title")));
		
	}
	
	// ----------------------------------------------------

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public void onCreate(QualifiedName qn, AjaxRequestTarget target) {
		final SNProperty property = typeManager.createProperty(qn);
		send(getPage(), Broadcast.BREADTH, 
				new ModelChangeEvent<SNProperty>(property, ModelChangeEvent.PROPERTY));
	}

}
