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
package de.lichtflut.rb.core.schema.persistence;

import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.Aras;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.views.ResourceView;

import de.lichtflut.rb.core.schema.RBSchema;
import de.lichtflut.rb.core.schema.model.ResourceSchema;

/**
 * <p>
 * Represents a {@link ResourceSchema}'s quick-info as a Semantic Node.
 * </p>
 * Created: Aug 29, 2012
 *
 * @author Ravi Knox
 */
public class SNQuickInfo extends ResourceView {

	// ---------------- Constructor -------------------------

	public SNQuickInfo(final ResourceID resourceID){
		SNOPS.assure(this, RBSchema.HAS_QUICK_INFO, resourceID);
	}

	// ------------------------------------------------------

	public void addSuccessor(final SNQuickInfo successor){
		SNOPS.associate(this, Aras.IS_PREDECESSOR_OF, successor);
	}

}
