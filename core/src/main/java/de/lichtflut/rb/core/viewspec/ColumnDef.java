/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.viewspec;

import org.arastreju.sge.model.ResourceID;

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
public interface ColumnDef {

	public abstract ResourceID getProperty();

	public abstract String getHeader();

	public abstract Integer getPosition();

	public abstract void setProperty(ResourceID property);

	public abstract void setHeader(String header);

	public abstract void setPosition(Integer position);

}