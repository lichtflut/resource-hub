/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.api;

import java.io.Serializable;
import java.util.Collection;

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
		CONJUNCT_MULTIPLE_KEYWORDS
	}
	
	
	/**
	 * creates or updates a given {@link ResourceTypeInstance}
	 * @return true if the operation was successfull, false if not
	 */
	public boolean createOrUpdateRTInstance(RBEntity<Object> instance);

	
	/**
	 * returns all the type-instances of the given schemas without any exceptions
	 * @param schemas the specified collection of schemas
	 */
	@SuppressWarnings("unchecked")
	public Collection<RBEntity> loadAllResourceTypeInstancesForSchema(Collection<ResourceSchema> schemas);
	
	// -----------------------------------------------------
	
	/**
	 * returns all the type-instances of the given schema which are matching to the given filter and SearchContext
	 * @param schema the specified schemas collection
	 * @param filter the search filter, including keywords or patterns which should match the type's attributes in a SearchContext defined way
	 * @param ctx the SearchContext, if null, the default SearchContext "CONJUNCT_MULTIPLE_KEYWORDS" will be taken instead
	 */
	@SuppressWarnings("unchecked")
	public Collection<RBEntity> loadAllResourceTypeInstancesForSchema(Collection<ResourceSchema> schemas, String filter, SearchContext ctx);
	
	/**
	 * returns all the type-instances of the given schema without any exceptions
	 * @param schema the specified schema
	 */
	@SuppressWarnings("unchecked")
	public Collection<RBEntity> loadAllResourceTypeInstancesForSchema(ResourceSchema schema);
	
	// -----------------------------------------------------
	
	/**
	 * returns all the type-instances of the given schema which are matching to the given filter and SearchContext
	 * @param schema the specified schemas collection
	 * @param filter the search filter, including keywords or patterns which should match the type's attributes in a SearchContext defined way
	 * @param ctx the SearchContext, if null, the default SearchContext "CONJUNCT_MULTIPLE_KEYWORDS" will be taken instead
	 */
	@SuppressWarnings("unchecked")
	public Collection<RBEntity> loadAllResourceTypeInstancesForSchema(ResourceSchema schema, String filter, SearchContext ctx);
	
	// -----------------------------------------------------
	
}
