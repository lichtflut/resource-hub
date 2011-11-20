/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.listview;

import org.apache.wicket.model.IModel;
import org.arastreju.sge.model.ResourceID;

/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created Nov 20, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public interface ColumnHeader {
	
	public enum ColumnType {
		DATA, ACTION
	}
	
	IModel<String> getLabel();
	
	ResourceID getPredicate();
	
	ColumnType getType();

}
