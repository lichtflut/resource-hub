/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models.types;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.arastreju.sge.model.nodes.views.SNProperty;

import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.webck.models.basic.AbstractLoadableDetachableModel;

/**
 * <p>
 *  Loadable list model for Properties.
 * </p>
 *
 * <p>
 * 	Created Dec 13, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class SNPropertyListModel extends AbstractLoadableDetachableModel<List<SNProperty>> {

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public List<SNProperty> load() {
		final List<SNProperty> properties = getServiceProvider().getTypeManager().findAllProperties();
		Collections.sort(properties, new Comparator<SNProperty>() {
			@Override
			public int compare(SNProperty t1, SNProperty t2) {
				return t1.getQualifiedName().getSimpleName().compareTo(t2.getQualifiedName().getSimpleName());
			}
		});
		return properties;
	}
	
	// ----------------------------------------------------
	
	protected abstract ServiceProvider getServiceProvider();

}
