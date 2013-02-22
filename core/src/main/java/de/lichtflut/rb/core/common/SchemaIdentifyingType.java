package de.lichtflut.rb.core.common;

import org.arastreju.sge.SNOPS;
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

		schemaClass = findRekursive(node, RBSystem.HAS_SCHEMA_IDENTIFYING_TYPE);

		if(null == schemaClass && fallback){
			schemaClass = findRekursive(node, RDF.TYPE);
		}
		return SNClass.from(schemaClass);
	}

	// ------------------------------------------------------

	private static SemanticNode findRekursive(final ResourceNode node, final ResourceID predicate){
		if(null != findAssociation(node, predicate)){
			return findAssociation(node, predicate);
		}
		SemanticNode schemaType = recursion(node, predicate);
		return schemaType;
	}

	private static SemanticNode recursion(final ResourceNode node, final ResourceID predicate){
		SemanticNode result = null;
		for (ResourceNode subclass : SNOPS.objectsAsResources(node, RDFS.SUB_CLASS_OF)) {
			if(null != findAssociation(subclass, predicate)){
				result = findAssociation(subclass, predicate);
			}else{
				result = recursion(subclass, predicate);
			}
		}
		return result;
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
