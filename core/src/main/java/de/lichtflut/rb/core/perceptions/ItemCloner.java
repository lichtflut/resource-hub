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
package de.lichtflut.rb.core.perceptions;

import de.lichtflut.rb.core.RBSystem;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.Aras;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.Statement;
import org.arastreju.sge.model.StatementMetaInfo;
import org.arastreju.sge.model.associations.HalfStatement;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.StatementOrigin;
import org.arastreju.sge.model.nodes.views.InheritedDecorator;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 *  Cloner of perception items.
 * </p>
 *
 * <p>
 *  Created 03.01.13
 * </p>
 *
 * @author Oliver Tigges
 */
public class ItemCloner {

	private final Set<ResourceID> predicateBlackList = new HashSet<ResourceID>();

	// ----------------------------------------------------

	public ItemCloner() {
		// statements with these predicates are not to be cloned
		predicateBlackList.add(RBSystem.BELONGS_TO_PERCEPTION);
		predicateBlackList.add(Aras.HAS_CHILD_NODE);
	}

	// ----------------------------------------------------

	public PerceptionItem createClone(final PerceptionItem original) {
		PerceptionItem clone = initClone(original);
		cloneAssociations(clone, original);
		defineRevocations(clone);
		rebaseClone(clone, original);
		return clone;
	}

	// ----------------------------------------------------

	private PerceptionItem initClone(final PerceptionItem original) {
		PerceptionItem clone = new PerceptionItem();
		clone.addAssociation(Aras.INHERITS_FROM, original);
		clone.addAssociation(RBSystem.BASED_ON, original);
		return clone;
	}

	private void cloneAssociations(final PerceptionItem target, final PerceptionItem original) {
		for (Statement stmt : original.getAssociations()) {
			StatementMetaInfo metaInfo = stmt.getMetaInfo();
			if (predicateBlackList.contains(stmt.getPredicate())) {
				continue;
			}
			if (!StatementOrigin.ASSERTED.equals(metaInfo.getOrigin())) {
				// ignore inferred and inherited statements.
				continue;
			}
			target.addAssociation(stmt.getPredicate(), stmt.getObject());
		}
	}

	private void defineRevocations(final PerceptionItem clone) {
		for (ResourceID predicate : predicateBlackList) {
			InheritedDecorator.revoke(clone, new HalfStatement(predicate, Aras.ANY));
		}
	}

	/**
	 * The cloned not must not inherit the base of the original item. It will inherit this indirectly.
	 */
	private void rebaseClone(final PerceptionItem clone, final PerceptionItem original) {
		Collection<SemanticNode> originalBases = getBases(original);
		for (SemanticNode current : originalBases) {
			InheritedDecorator.revoke(clone, new HalfStatement(RBSystem.BASED_ON, current));
			InheritedDecorator.revoke(clone, new HalfStatement(Aras.INHERITS_FROM, current));
		}
	}

	private Collection<SemanticNode> getBases(final PerceptionItem item) {
		return SNOPS.objects(item, RBSystem.BASED_ON);
	}

}
