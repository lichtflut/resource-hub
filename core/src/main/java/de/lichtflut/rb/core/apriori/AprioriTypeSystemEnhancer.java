/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.apriori;


import java.util.Collection;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.views.SNClass;

import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.core.services.TypeManager;

/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created Sep 22, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class AprioriTypeSystemEnhancer implements AprioriModeller {
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public final void run(final ServiceProvider sp) {
		final TypeManager tm = sp.getTypeManager();
		for (ResourceID current : getAprioriTypes()) {
			addType(tm, current);
		}
	}
	
	// -----------------------------------------------------
	
	public abstract Collection<ResourceID> getAprioriTypes();
	
	public void postprocess(final SNClass type) { }
	
	// -----------------------------------------------------
	
	protected final void addType(TypeManager manager, ResourceID typeID) {
		SNClass type = manager.createType(typeID.getQualifiedName());
		postprocess(type);
	}
	
	

}
