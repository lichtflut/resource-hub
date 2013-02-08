package de.lichtflut.rb.core.common;

import org.arastreju.sge.apriori.RDF;
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
		if(null == resource){
			return null;
		}
		return of(resource.asResource());
	}

	/**
	 * @param node Base node to which the schema type is to be found
	 * @return The first occurrence of {@link RBSystem#HAS_SCHEMA_IDENTIFYING_TYPE} in base or superclasses or {@link RDF#TYPE} if schema is ot found
	 */
	public static SNClass of(final ResourceNode node){
		return of(node, true);
	}

	/**
	 * @param node Base node to which the schema type is to be found
	 * @param fallback If true and no schema is found, return the most specifying RDF:TYPE
	 * @return The first occurrence of {@link RBSystem#HAS_SCHEMA_IDENTIFYING_TYPE} in base or superclasses or {@link RDF#TYPE} if schema is ot found and fallback is set
	 */
	public static SNClass of(final ResourceNode node, final boolean fallback){
		SemanticNode schemaClass = null;

		if(null == node){
			return null;
		}

		schemaClass = findAssociationRekursive(node, RBSystem.HAS_SCHEMA_IDENTIFYING_TYPE);

		if(null == schemaClass && fallback){
			schemaClass = findAssociationRekursive(node, RDF.TYPE);
		}
		return SNClass.from(schemaClass);
	}

	// ------------------------------------------------------

	private static SemanticNode findAssociationRekursive(final ResourceNode node, final ResourceID predicate) {
		SemanticNode schemaClass;
		schemaClass = findAssociation(node, predicate);

		if(null == schemaClass && isSubClass(node)){
			schemaClass = SchemaIdentifyingType.findAssociationRekursive(findAssociation(node, RDFS.SUB_CLASS_OF), predicate);
		}
		return schemaClass;
	}

	private static boolean isSubClass(final ResourceNode node) {
		return findAssociation(node, RDFS.SUB_CLASS_OF) != null;
	}

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
