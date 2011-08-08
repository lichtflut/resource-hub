/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.api;

import java.io.Serializable;
import java.util.Collection;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.naming.QualifiedName;
import de.lichtflut.rb.core.schema.model.RBEntity;
import de.lichtflut.rb.core.schema.model.ResourceSchema;

/**
 * <p>
 * This is the RB's ResourceTypeManagement "HighLevel" interface.
 * Whatever you want to manage, this service tries to give you the tools to do that.
 * No additional knowledge-base about infrastructure, technology stack e.g. should be required/necessary.
 * </p>
 *
 * Try to make this interface as flexible as you can.
 *
 * Please note, that this is not yet ready.
 * This might be renamed or refactored in sth. bigger
 *
 * Created: May 18, 2011
 *
 * @author Nils Bleisch
 */
public interface RBEntityManagement extends Serializable{

	/**
	 * <p>SearchContext enum fields.</p>
	 * <p>
	 * <ul>
	 * 	<li>CONJUNCT_MULTIPLE_KEYWORDS<br />
	 *  Splitting up the filter string into a set of filters by blank " "
	 * 	<ul>
	 * 		<li>filters are combined as logical conjunction (&&)</li>
	 * 		<li>Each filter acts like "contains" and must not match the whole value excatly</li>
	 * 	</ul>
	 * </li>
	 * </ul>
	 * </p>
	 */
	enum SearchContext{
		/**
		 * Splitting up the filter string into a set of filters by blank " ".
		 * 	<ul>
		 * 	<li>filters are combined as logical conjunction (&&)</li>
		 * 	<li>Each filter acts like "contains" and must not match the whole value excatly</li>
		 * 	</ul>
		 */
		CONJUNCT_MULTIPLE_KEYWORDS
	}


	/**
	 * creates or updates a given {@link ResourceTypeInstance}.
	 * @return true if the operation was successfull, false if not
	 * @param instance - The {@link RBEntity} which shall be created or updated
	 */
	boolean createOrUpdateEntity(RBEntity<Object> instance);

	// -----------------------------------------------------

	/**
	 * <p>
	 * Returns a {@link RBEntity} for the given params.
	 * </p>
	 * @param qn , the {@link QualifiedName} which has to be the ID of the RBEntity
	 * @return the resolved {@link RBEntity}-node or
	 * null if no {@link RBEntity} could be assigned to the params
	 */
	@SuppressWarnings("rawtypes")
	RBEntity loadEntity(QualifiedName qn);

	// -----------------------------------------------------

	/**
	 * <p>
	 * Returns a {@link RBEntity} for the given params.
	 * </p>
	 * @param nodeIdentifier - the {@link String} which has to be the URI of the RBEntity
	 * @return the resolved {@link RBEntity}-node or
	 * null if no {@link RBEntity} could be assigned to the params
	 */
	@SuppressWarnings("rawtypes")
	RBEntity loadEntity(String nodeIdentifier);

	// -----------------------------------------------------

	/**
	 * <p>
	 * Returns a {@link RBEntity} for the given params.
	 * </p>
	 * @param node - the {@link ResourceNode} which has to be the Node of the RBEntity
	 * @return the resolved {@link RBEntity}-node or
	 * null if no {@link RBEntity} could be assigned to the params
	 */
	@SuppressWarnings("rawtypes")
	RBEntity loadEntity(ResourceNode node);

	// -----------------------------------------------------

	/**
	 * <p>
	 * Returns all the type-instances of the given schemas without any exceptions.
	 * </p>
	 * @param schemas the specified collection of schemas
	 * @return a {@link Collection} of {@link RBEntity} which are dependent on the given schemas
	 */
	@SuppressWarnings("rawtypes")
	Collection<RBEntity> loadAllEntitiesForSchema(Collection<ResourceSchema> schemas);

	// -----------------------------------------------------

	/**
	 * <p>
	 * Returns all the type-instances of the given schema which are matching to the given filter and SearchContext.
	 * </p>
	 * @param schemas - the specified schemas collection
	 * @param filter - the search filter,
	 *  including keywords or patterns which should match the type's attributes in a SearchContext defined way
	 * @param ctx -  the SearchContext, if null, the default SearchContext "CONJUNCT_MULTIPLE_KEYWORDS" will be taken instead
	 * @return a {@link Collection} of {@link RBEntity} which depends on the given schemas, filter and searchcontext
	 */
	@SuppressWarnings("rawtypes")
	Collection<RBEntity> loadAllEntitiesForSchema(final Collection<ResourceSchema> schemas,
			final String filter, final SearchContext ctx);

	/**
	 * <p>
	 * Returns all the type-instances of the given schema without any exceptions.
	 * </p>
	 * @param schema -  the specified schema
	 * @return a {@link Collection} of {@link RBEntity} which depends on the given schema
	 */
	@SuppressWarnings("rawtypes")
	Collection<RBEntity> loadAllEntitiesForSchema(ResourceSchema schema);

	// -----------------------------------------------------

	/**
	 * <p>
	 * Returns all the type-instances of the given schema which are matching to the given filter and {@link SearchContext}.
	 * </p>
	 * @param schema - the specified schemas collection
	 * @param filter - the search filter,
	 *  including keywords or patterns which should match the type's attributes in a SearchContext defined way
	 * @param ctx - the SearchContext, if null, the default SearchContext "CONJUNCT_MULTIPLE_KEYWORDS" will be taken instead
	 * @return  a {@link Collection} of {@link RBEntity} which depends on the given schema, filter and searchcontext
	 */
	@SuppressWarnings("rawtypes")
	Collection<RBEntity> loadAllEntitiesForSchema(ResourceSchema schema, String filter, SearchContext ctx);

	// -----------------------------------------------------

}
