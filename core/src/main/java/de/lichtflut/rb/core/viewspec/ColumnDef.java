/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.viewspec;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;

/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created Jan 30, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public interface ColumnDef extends ResourceNode {

	ResourceID getProperty();

	String getHeader();

	Integer getPosition();

	void setProperty(ResourceID property);

	void setHeader(String header);

	void setPosition(Integer position);

}