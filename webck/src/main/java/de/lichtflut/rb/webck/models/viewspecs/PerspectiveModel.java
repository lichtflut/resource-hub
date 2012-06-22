/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models.viewspecs;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.services.ViewSpecificationService;
import de.lichtflut.rb.core.viewspec.Perspective;
import de.lichtflut.rb.webck.models.basic.AbstractLoadableDetachableModel;

/**
 * <p>
 *  Model for loading of perspectives by their ID.
 * </p>
 *
 * <p>
 * 	Created Feb 6, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class PerspectiveModel extends AbstractLoadableDetachableModel<Perspective> {
	
	private final ResourceID perspectiveID;
	
	@SpringBean
	private ViewSpecificationService viewSpecificationService;
	
	// ----------------------------------------------------
	
	/**
	 * Constructor. 
	 */
	public PerspectiveModel(final ResourceID perspectiveID) {
		this.perspectiveID = perspectiveID;
		Injector.get().inject(this);
	}
	
	// ----------------------------------------------------

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public Perspective load() {
		return viewSpecificationService.findPerspective(perspectiveID);
	}

}
