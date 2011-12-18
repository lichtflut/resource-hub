/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.arastreju.sge.model.nodes.views.SNClass;

import de.lichtflut.rb.core.services.ServiceProvider;

/**
 * <p>
 *  Loadable list model for rb:Types.
 * </p>
 *
 * <p>
 * 	Created Dec 13, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class RBTypeListModel extends AbstractLoadableDetachableModel<List<SNClass>> {

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public List<SNClass> load() {
		final List<SNClass> types =getServiceProvider().getTypeManager().findAllTypes();
		Collections.sort(types, new Comparator<SNClass>() {
			@Override
			public int compare(SNClass t1, SNClass t2) {
				return t1.getQualifiedName().getSimpleName().compareTo(t2.getQualifiedName().getSimpleName());
			}
		});
		return types;
	}
	
	// ----------------------------------------------------
	
	protected abstract ServiceProvider getServiceProvider();

}
