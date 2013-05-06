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
package de.lichtflut.rb.core.common;

import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.schema.RBSchema;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.views.SNClass;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 *  Helper to get the 'schema identifying' type of a resource. By default if no Schema is found, the most specifying RDF.TYPE is returned.
 *  The detection of a resource's Schema/type is a three step process.
 *  <ol>
 *      <li>Check if the resource has directly assigned the type via system:hasSchemaIdentifyingType</li>
 *      <li>Check if any of the super classes has assigned the type via system:hasSchemaIdentifyingType</li>
 *      <li>Execute the same steps above for RDF.TYPE</li>
 *  </ol>
 * </p>
 * <p>
 *  Created 11.01.13
 * </p>
 *
 * @author Oliver Tigges
 * @author Ravi Knox
 */
public class SchemaIdentifyingType {

	/**
	 * @param resource Base resource to which the schema type is to be found
	 * @return The first occurrence of {@link RBSystem#HAS_SCHEMA_IDENTIFYING_TYPE} in base or superclasses.
	 */
	public static SNClass of(final ResourceID resource) {
		if(null == resource){
			return null;
		}
		return of(resource.asResource());
	}

	/**
	 * @param resource Base resource to which the schema type is to be found
	 * @return The first occurrence of {@link RBSystem#HAS_SCHEMA_IDENTIFYING_TYPE} in base or superclasses or {@link RDF#TYPE} if schema is ot found
	 */
	public static SNClass of(final ResourceNode resource){
        if(null == resource){
            return null;
        }

        // 1st: check direct identification of schema type
        SNClass siType = getSchemaIdentifiyingType(resource);
        if (siType != null) {
            return siType;
        }

        // 2nd: find type with schema in type hierarchy
        SNClass typeWithSchema = findTypeWithSchema(resource);
        if (typeWithSchema != null) {
            return typeWithSchema;
        }

        // 3rd: standard way
        return EntityType.of(resource);
    }

    // ----------------------------------------------------

    private static SNClass getSchemaIdentifiyingType(ResourceNode resource) {
        ResourceNode type = SNOPS.fetchObjectAsResource(resource, RBSystem.HAS_SCHEMA_IDENTIFYING_TYPE);
        if (type != null) {
            return SNClass.from(type);
        } else {
            return null;
        }
    }

    private static SNClass findTypeWithSchema(ResourceNode resource) {
        Set<ResourceNode> types = SNOPS.objectsAsResources(resource, RDF.TYPE);
        return findTypeWithSchema(types, new HashSet<ResourceNode>());
    }

    private static SNClass findTypeWithSchema(Set<ResourceNode> types, Set<ResourceNode> checked) {
        if (types.isEmpty()) {
            return null;
        }

        Set<ResourceNode> nextLevelTypes = new HashSet<ResourceNode>();
        for (ResourceNode type : types) {
            checked.add(type);
            if (!SNOPS.objects(type, RBSchema.HAS_SCHEMA).isEmpty()) {
                return SNClass.from(type);
            }
            nextLevelTypes.addAll(SNClass.from(type).getDirectSuperClasses());
        }
        nextLevelTypes.removeAll(checked);
        return findTypeWithSchema(nextLevelTypes, checked);
    }

}
