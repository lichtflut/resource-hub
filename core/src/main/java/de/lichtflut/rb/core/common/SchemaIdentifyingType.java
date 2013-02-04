package de.lichtflut.rb.core.common;

import org.arastreju.sge.apriori.RDFS;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.SNClass;

import de.lichtflut.rb.core.RBSystem;

/**
 * <p>
 *  Helper to get the 'schema identifying' type of a resource.
 *  The detection of a resource's type is a three step process.
 *  <ol>
 *      <li>Check if the resource has directly assigned the type via system:hasSchemaIdentifyingType</li>
 *      <li>Check if any of the super classes has assigned the type via system:hasSchemaIdentifyingType</li>
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
	 * @param node Base node to which the schema type is to be found
	 * @return The first occurrence of {@link RBSystem#HAS_SCHEMA_IDENTIFYING_TYPE} in base or superclasses.
	 */
	public static SNClass of(final ResourceID resource) {
		return of(resource.asResource());
	}

	/**
	 * @param node Base node to which the schema type is to be found
	 * @return The first occurrence of {@link RBSystem#HAS_SCHEMA_IDENTIFYING_TYPE} in base or superclasses.
	 */
	public static SNClass of(final ResourceNode node){
		SemanticNode schemaClass = null;
		SNClass result = null;

		if(null == node){
			return null;
		}

		schemaClass = findAssociation(node, RBSystem.HAS_SCHEMA_IDENTIFYING_TYPE);

		if(null == schemaClass && node != null){
			schemaClass = SchemaIdentifyingType.of(findAssociation(node, RDFS.SUB_CLASS_OF));
		}

		if(null != schemaClass){
			result = SNClass.from(schemaClass);
		}
		return result;
	}

	// ------------------------------------------------------

	private static ResourceNode findAssociation(final ResourceNode node, final ResourceID predicate) {
		ResourceNode result = null;
		for (Statement assoc : node.getAssociations()) {
			if(predicate.equals(assoc.getPredicate())){
				result = assoc.getObject().asResource();
				break;
			}
		}
		return result;
	}
}
