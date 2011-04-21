/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
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
package de.lichtflut.rb.core.schema.persistence;

import java.util.Set;

import org.arastreju.sge.context.Context;
import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.associations.Association;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.ResourceView;
import org.arastreju.sge.model.nodes.views.SNText;
import org.arastreju.sge.model.nodes.views.SNUri;

import de.lichtflut.infra.Infra;
import de.lichtflut.rb.core.schema.RBSchema;


/**
 * <p>
 * Represents the declaration of a Property, which can be assigned to a Class
 * <p>
 * 
 * <p>
 * Consists of a property and constraints:
 * <ul>
 *  <li> identifier (URI)</li>
 *  <li> datatype</li>
 *  <li> constraints</li>
 * </ul>
 * 
 * 	Created: 20.01.2009
 * </p>
 *
 * @author Oliver Tigges
 */
public class SNPropertyDeclaration extends ResourceView {

	/**
	 * Constructor for a new property declaration node.
	 */
	public SNPropertyDeclaration() {
	}
	
	/**
	 * Creates a view for given resource.
	 * @param resource
	 */
	public SNPropertyDeclaration(final ResourceNode resource) {
		super(resource);
	}
	
	// -----------------------------------------------------

	public SNUri getIdentifier() {
		SemanticNode id = getSingleAssociationClient(RBSchema.HAS_IDENTIFIER);
		if (id != null) {
			return id.asValue().asUri();
		} else {
			return null;
		}
	}
	
	public void setIdentifier(final SNUri id, final Context context) {
		if (!Infra.equals(getIdentifier(), id)){
			removeAssocs(RBSchema.HAS_IDENTIFIER);
			Association.create(this, RBSchema.HAS_IDENTIFIER, id, context);
		}
	}
	
	public ElementaryDataType getDatatype() {
		SemanticNode type = getSingleAssociationClient(RBSchema.HAS_DATATYPE);
		if (type != null) {
			String name = type.asValue().asText().getStringValue();
			return ElementaryDataType.valueOf(name);
		} else {
			return null;
		}
	}
	
	public void setDatatype(final ElementaryDataType type, final Context context) {
		if (!Infra.equals(getDatatype(), type)){
			removeAssocs(RBSchema.HAS_DATATYPE);
			Association.create(this, RBSchema.HAS_DATATYPE, new SNText(type.name()), context);
		}
	}
	
	// -----------------------------------------------------
	
	/**
	 * Removes all associations with given predicate.
	 * @param predicate The predicate.
	 */
	protected void removeAssocs(final ResourceID predicate){
		Set<Association> assocs = getAssociations(predicate);
		for (Association assoc : assocs) {
			revoke(assoc);
		}
	}
	
}
