/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.common;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.views.SNEntity;

import java.io.Serializable;

/**
 * <p>
 *  Filter for relationships. 
 * </p>
 *
 * <p>
 * 	Created Dec 15, 2011
 * </p>
 *
 * @see RelationshipAccess
 *
 * @author Oliver Tigges
 */
public interface RelationshipFilter extends Serializable {
	
	boolean accept(Statement stmt, boolean declaredInSchema);
	
	// ----------------------------------------------------
	
	/**
	 * Accepts only undeclared statements.
	 */
	abstract class Undeclared implements RelationshipFilter {
		@Override
		public boolean accept(Statement stmt, boolean declaredInSchema) {
			if (!declaredInSchema) {
				return accept(stmt);
			} else {
				return false;
			}
		}
		
		protected boolean isOfType(ResourceNode node, ResourceID... types) {
			for (ResourceID current : types) {
				if(SNEntity.from(node).isInstanceOf(current)) {
					return true;	
				}
			}
			return false;
		}

		public abstract boolean accept(Statement stmt);
	} 

}
