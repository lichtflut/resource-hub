/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.context.Context;
import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.associations.Association;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNValue;
import org.arastreju.sge.model.nodes.views.SNEntity;

/**
 * <p>
 * Represents an instance of a well defined ResourceType (RT) This type is
 * either represented as key_value store and as graph-based ResourceView,
 * including associations and so on.
 * </p>
 *
 * Created: May 17, 2011
 *
 * @author Nils Bleisch
 * @param <T>
 */
public abstract class RBEntity<T extends Object> extends SNEntity
		implements Serializable {

	/**
	 * Keys which help to get some meta data for a given attribute name during.
	 * runtime
	 */
	public enum MetaDataKeys {
		MAX, MIN, CURRENT, TYPE
	}

	private static final long serialVersionUID = 535252848460633518L;

	// -- CONSTRUCTOR --------------------------------------

	/**
	 * Default constructor.
	 */
	public RBEntity() {
		// do nothing
	}

	/**
	 * Takes the node of this type as argument.
	 * @param id - the {@link ResourceNode} id
	 */
	public RBEntity(final ResourceNode id) {
		super(id);
	}

	/**
<<<<<<< HEAD
	 * @param attribute - {@link String} representing the attribute
	 * @param value - {@link Object}
	 * @throws RBInvalidValueException -
	 * @throws RBInvalidAttributeException -
	 * <p>
	 *         InvalidValueException when the value does not match the required
	 *         datatype or constraints or if the maximum count of possible
	 *         values is reached (depends on the cardinality).
	 *         InvalidAttributeException if this attribute does not exists.
	 *         </p>
	 * @return the ticket where this value has been stored
	 */
	public abstract Integer addValueFor(String attribute, T value)
			throws RBInvalidValueException, RBInvalidAttributeException;

	// -----------------------------------------------------

	/**
	 * Get a collection of attribute names which are matched by the given simple name.
	 * @param simpleAttribute -
	 * @return Collection of names which are matched by the given simple name
	 */
	public abstract Collection<String> getAttributesNamesForSimple(
			String simpleAttribute);

	/**
	 * Get a simple attribute-name representation of the origin ones. This is
	 * realized by removing the prefix-namespaces. Please note, that this is
	 * just designed for display purposes. All other methods still require the
	 * full qualified and unique attribute name
	 * @param attribute -
	 * @return Simple Attribute Name
	 */
	public abstract String getSimpleAttributeName(String attribute);

	// -----------------------------------------------------

	/**
	 * <p>
	 *         InvalidValueException when the value does not match the required
	 *         datatype or constraints or if the maximum count of possible
	 *         values is reached (depends on the cardinality). of if the ticket
	 *         does not exists.
	 * </p>
	 * @param attribute - {@link String} representing the attribute
	 * @param value - {@link Object}
	 * @param ticket - the reference to an existing value assignment
	 * @throws RBInvalidValueException -
	 * @throws RBInvalidAttributeException -
	 * 			<p>
	 * 			  if this attribute does not exists.
	 *         </p>
	 */
	public abstract void addValueFor(String attribute, T value, int ticket)
			throws RBInvalidValueException, RBInvalidAttributeException;

	/**
	 * <p>
	 * Tries to return the value which matches to the given attribute and index.
	 * </p>
	 * @param attribute - {@link String} representing the attribute
	 * @param index -
	 * @return the value or null if no value could be found for the attribute and index
	 */
	public abstract T getValueFor(final String attribute, final int index);

	// -----------------------------------------------------

	/**
	 * <p>
	 * Returns the complete set of all the attribute-names of this instance.
	 * </p>
	 * @return all the attribute-names of this instance as {@link Collection}.
	 */
	public abstract Collection<String> getAttributeNames();

	// -----------------------------------------------------

	/**
	 * <p>
	 * Return all the values for a given attribute.
	 * </p>
	 * @param attribute -
	 * @return the requested set of Values as {@link Collection}
	 */
	public abstract Collection<T> getValuesFor(final String attribute);

	// -----------------------------------------------------

	/**
	 * <p>
	 * Return all the existing tickets for a given attribute.
	 * </p>
	 * @param attribute - {@link String} representing the attribute
	 * @return the requested set of tickets as {@link Collection}
	 */
	public abstract Collection<Integer> getTicketsFor(final String attribute);

	// -----------------------------------------------------

	/**
	 * <p>
	 *  .
	 * </p>
	 * @param attribute - {@link String} representing the attribute
	 * @return a {@link RBValidatorFactory} for a given attribute.
	 *
	 */
	public abstract RBValidator<T> getValidatorFor(final String attribute);

	// -----------------------------------------------------

	/**
	 * <p>
	 * Tries to generate and return a ticket for given attribute.
	 * </p>
	 * @param attribute - {@link String} representing the attribute
	 * @throws RBInvalidValueException if the maximum count of possible values
	 *         (tickets) is reached (depends on the cardinality). or if the
	 *         ticket does not exists.
	 * @throws RBInvalidAttributeException if this attribute does not exists.
	 * @return the ticket id
	 */
	public abstract Integer generateTicketFor(final String attribute)
			throws RBInvalidValueException, RBInvalidAttributeException;

	// -----------------------------------------------------

	/**
	 * <p>
	 * Tries to release and remove an exisiting ticket.
	 * </p>
	 * @param attribute - {@link String} representing the attribute
	 * @param ticket -
	 * @throws RBInvalidValueException if the minimum count of possible values
	 *         (tickets) is reached (depends on the cardinality). or if the
	 *         ticket does not exists.
	 * @throws RBInvalidAttributeException if this attribute does not exists.
	 *
	 */
	public abstract void releaseTicketFor(final String attribute, final int ticket)
			throws RBInvalidValueException, RBInvalidAttributeException;

	// -----------------------------------------------------

	/**
	 * <p>
	 * Returns the MetaInformations for a given attribute specified with a key.
	 * </p>
	 * @param key - the specified key
	 * @param attribute - {@link String} representing the attribute
	 * @return the MetaInfo as Object. The key does specify what for a type it will be.
	 */
	public abstract Object getMetaInfoFor(final String attribute, final MetaDataKeys key);

	// -----------------------------------------------------

	/**
	 * <p>
	 * Returns the {@link ResourceSchema} of this Entity.
	 * </p>
	 * @return the {@link ResourceSchema}
	 */
	public abstract ResourceSchema getResourceSchema();

	// -----------------------------------------------------

	/**
	 * <p>
	 * Return the ResourceType of this Entity.
	 * </p>
	 * @return the {@link ResourceID} of the Type
	 */
	public abstract ResourceID getResourceTypeID();

	// -----------------------------------------------------

	/**
	 * <p>
	 * Creates associations based on the internal key-value map. The new
	 * association might replace an existing older one.
	 * </p>
	 * @param ctx - the associated context
	 */
	public void createAssociations(final Context ctx){
		Association.create(this, RDF.TYPE, getResourceTypeID(), ctx);
		ResourceSchema schema = getResourceSchema();
		Collection<PropertyAssertion> assertions = schema
				.getPropertyAssertions();
		for (PropertyAssertion pAssertion : assertions) {
			try {
				for (Association assoc : getAssociations(pAssertion
						.getPropertyDescriptor())) {
					this.revoke(assoc);
				}
			} catch (org.neo4j.graphdb.NotFoundException e){
				e.getMessage();
				// If this node was not found, do nothing
			}
			Collection<T> values = this.getValuesFor(pAssertion
					.getPropertyDescriptor().getQualifiedName().toURI());
			for (T value : values) {
				Object val = value;
				ElementaryDataType type = pAssertion.getPropertyDeclaration()
						.getElementaryDataType();
				switch (type) {
					case DATE:
						type = ElementaryDataType.STRING;
						break;
					default: //do nothing break;
				}
				Association.create(this, pAssertion.getPropertyDescriptor(),
						new SNValue(type, val), ctx);
			}// End of inner for loop
		}// End of outer for loop
	}

	// -----------------------------------------------------

	/**
	 * <p>
	 * Sorts a {@link List} of {@link RBEntity} by the specified attribute.
	 * Sorting attributes of {@link ElementaryDataType}.RESOURCE is not possible.
	 * </p>
	 * @param entities - {@link List} of {@link RBEntity}
	 * @param attribute - which attribute to order by
	 * @param ascending - true if list is to be sorted in an anscending order, false if in a descending order
	 */
	@SuppressWarnings("rawtypes")
	public static void sortByAttribute(final List<RBEntity> entities, final String attribute, final boolean ascending){
		if((entities.size() > 0)){
			RBEntity entity = entities.get(entities.size()-1);
			if(entity.getValuesFor(attribute).size()<1){
				try {
					throw new RBInvalidAttributeException("There is no such attribute: " + attribute);
				} catch (RBInvalidAttributeException e) {
					e.printStackTrace();
				}
			}
			if(!entity.getMetaInfoFor(attribute, MetaDataKeys.TYPE).equals(ElementaryDataType.RESOURCE)){
				Comparator<RBEntity> comparator = new Comparator<RBEntity>(){

					@Override
					public int compare(final RBEntity e1, final RBEntity e2) {
						return (e1.getValueFor(attribute, 0).toString())
							.compareToIgnoreCase(e2.getValueFor(attribute, 0).toString());
					}
				};
				if(ascending){
					Collections.sort(entities, comparator);
				}else{
					Collections.sort(entities, Collections.reverseOrder(comparator));
				}
			}
		}
	}

	// ------------------------------------------------------------
	/**
	 * {@inheritDoc}}.
	 */
	@Override
	public String toString() {
		final int maxVal = 5;
		String schemaName = this.getResourceSchema().getDescribedResourceID()
				.getQualifiedName().getSimpleName();
		StringBuilder output = new StringBuilder("[" + schemaName + "] ");
		ArrayList<String> attributes = new ArrayList<String>(
				this.getAttributeNames());
		for (int cnt = 0; (cnt < maxVal && cnt < attributes.size()); cnt++) {
			ArrayList<T> values = new ArrayList<T>(this.getValuesFor(attributes
					.get(cnt)));
			if (values.size() > 0) {
				if (cnt != 0){
					output.append(", ");
				}
				output.append(this.getSimpleAttributeName(attributes.get(cnt))
						+ "=" + values.get(0).toString());
			}
		}
		return output.toString();
	}
}
