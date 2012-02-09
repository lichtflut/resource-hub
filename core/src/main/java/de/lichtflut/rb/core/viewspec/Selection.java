/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.viewspec;

import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.query.Query;

/**
 * <p>
 *  Selection of nodes.
 * </p>
 *
 * <p>
 * 	Created Jan 20, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public interface Selection extends ResourceNode {
	
	boolean isDefined();
	
	void adapt(Query query);

}
