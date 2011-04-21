/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.persistence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.arastreju.sge.context.Context;
import org.arastreju.sge.model.associations.Association;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.views.ResourceView;

import de.lichtflut.rb.core.schema.RBSchema;

/**
 * <p>
 *  Schema for a Class/Resource Type.
 * </p>
 *
 * <p>
 * 	Created Apr 20, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class SNResourceSchema extends ResourceView {
	
	public SNResourceSchema(final ResourceNode resource) {
		super(resource);
	}
	

	/**
	 * Constructor for new Resource Schemas.
	 */
	public SNResourceSchema() {
	}
	
	// -----------------------------------------------------

	/**
	 * Adds the given PropertyAssertion to this class.
	 */
	public void addPropertyAssertion(final SNPropertyAssertion pa, final Context ctx){
		Association.create(this, RBSchema.HAS_PROPERTY_ASSERT, pa, ctx);
	}
	
	/**
	 * Collects all PropertyAssertions defined by this Schema.
	 * @return The list of all property assertions.
	 */
	public List<SNPropertyAssertion> getPropertyAssertions(){
		Set<SNPropertyAssertion> result = new HashSet<SNPropertyAssertion>();
//		for (SNClass superCl : getSuperClasses()) {
//			result.addAll(superCl.getDeclaredPropertyDeclarations());
//		}
		result.addAll(getDeclaredPropertyAssertions());
		List<SNPropertyAssertion> list = new ArrayList<SNPropertyAssertion>(result);
		Collections.sort(list);
		return list;
	}
	
	/**
	 * Returns only the property declarations declared for this classifier, not the inherited.
	 * @return The list of property declarations.
	 */
	public List<SNPropertyAssertion> getDeclaredPropertyAssertions(){
		List<SNPropertyAssertion> result = new ArrayList<SNPropertyAssertion>();
		Set<Association> assocs = getAssociations(RBSchema.HAS_PROPERTY_ASSERT);
		for (Association current : assocs) {
			result.add(new SNPropertyAssertion(current.getClient().asResource()));
		}
		Collections.sort(result);
		return result;
	}

	/* (non-Javadoc)
	 * @see org.arastreju.api.model.semantic.ResourceClassifier#removePropertyDeclaration(org.arastreju.api.model.semantic.SNPropertyDeclaration)
	 */
	public void removePropertyAssertion(SNPropertyAssertion decl) {
		Set<Association> assocs = getAssociations(RBSchema.HAS_PROPERTY_ASSERT);
		Association toBeRemoved = null;
		for (Association current : assocs) {
			if (decl.equals(current.getClient())){
				toBeRemoved = current;
				break;
			}
		}
		revoke(toBeRemoved);
	}
	
}