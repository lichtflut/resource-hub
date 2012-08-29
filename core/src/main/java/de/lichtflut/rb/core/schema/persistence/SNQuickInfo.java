/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
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
