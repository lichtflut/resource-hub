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
