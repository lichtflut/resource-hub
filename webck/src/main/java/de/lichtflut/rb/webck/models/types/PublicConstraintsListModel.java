/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models.types;

import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.services.SchemaManager;
import de.lichtflut.rb.webck.models.basic.AbstractLoadableDetachableModel;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  Loadable list model for Publid Type Definitions.
 * </p>
 *
 * <p>
 * 	Created Dec 13, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class PublicConstraintsListModel extends AbstractLoadableDetachableModel<List<Constraint>> {

	@SpringBean
	private SchemaManager schemaManager;
	
	// ----------------------------------------------------
	
	/**
	 * Constructor.
	 */
	public PublicConstraintsListModel() {
		Injector.get().inject(this);
	}
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public List<Constraint> load() {
		return new ArrayList<Constraint>(
				schemaManager.findPublicConstraints());
	}
	
}
