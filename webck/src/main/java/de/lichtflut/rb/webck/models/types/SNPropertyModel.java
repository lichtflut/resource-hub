/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models.types;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.views.SNProperty;
import org.arastreju.sge.naming.QualifiedName;

import de.lichtflut.rb.core.services.TypeManager;
import de.lichtflut.rb.webck.models.basic.AbstractLoadableDetachableModel;

/**
 * <p>
 *  Loadable model for a Property.
 * </p>
 *
 * <p>
 * 	Created Mar 16, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class SNPropertyModel extends AbstractLoadableDetachableModel<SNProperty> {

	@SpringBean
	private TypeManager typeManager;
	
	private final QualifiedName qn;
	
	// ----------------------------------------------------
	
	/**
	 * Constructor.
	 */
	public SNPropertyModel(final ResourceID id) {
		this.qn = id.getQualifiedName();
		Injector.get().inject(this);
	}
	
	/**
	 * Constructor.
	 */
	public SNPropertyModel(final QualifiedName qn) {
		this.qn = qn;
		Injector.get().inject(this);
	}
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public SNProperty load() {
		return typeManager.findProperty(qn);
	}
	
}
