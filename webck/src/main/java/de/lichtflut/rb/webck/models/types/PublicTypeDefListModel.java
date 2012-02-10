/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models.types;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.lichtflut.rb.core.schema.model.TypeDefinition;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.webck.models.basic.AbstractLoadableDetachableModel;

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
public class PublicTypeDefListModel extends AbstractLoadableDetachableModel<List<TypeDefinition>> {

	@SpringBean
	private ServiceProvider provider;
	
	// ----------------------------------------------------
	
	/**
	 * Constructor.
	 */
	public PublicTypeDefListModel() {
		Injector.get().inject(this);
	}
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public List<TypeDefinition> load() {
		return new ArrayList<TypeDefinition>(
				provider.getSchemaManager().findPublicTypeDefinitions());
	}
	
}
