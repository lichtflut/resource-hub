/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models.viewspecs;

import java.util.List;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.lichtflut.rb.core.services.ViewSpecificationService;
import de.lichtflut.rb.core.viewspec.Perspective;

/**
 * <p>
 *  Model for loading of all available perspectives.
 * </p>
 *
 * <p>
 * 	Created Feb 6, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class PerspectiveListModel extends LoadableDetachableModel<List<Perspective>> {

	@SpringBean
	private ViewSpecificationService viewSpecificationService;

	// ----------------------------------------------------

	/**
	 * Constructor.
	 */
	public PerspectiveListModel() {
		Injector.get().inject(this);
	}

	// ----------------------------------------------------

	@Override
	public List<Perspective> load() {
		return viewSpecificationService.findPerspectives();
	}

}
