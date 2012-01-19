/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.security.Domain;

import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.webck.models.basic.AbstractLoadableDetachableModel;
import de.lichtflut.rb.webck.models.basic.DerivedModel;

/**
 * <p>
 *  Model providing the current domain.
 * </p>
 *
 * <p>
 * 	Created Jan 16, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class CurrentDomainModel extends AbstractLoadableDetachableModel<Domain> {

	@SpringBean
	private ServiceProvider provider;
	
	// ----------------------------------------------------
	
	/**
	 * Constructor.
	 */
	public CurrentDomainModel() {
		Injector.get().inject(this);
	}
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public Domain load() {
		return provider.getDomainOrganizer().getMasterDomain();
	}

	/**
	 * @return A model providing the domain's name.
	 */
	public static IModel<String> displayNameModel() {
		return new DerivedModel<String, Domain>(new CurrentDomainModel()) {
			@Override
			protected String derive(Domain original) {
				if (original.getTitle() != null) {
					return original.getTitle();
				} else {
					return original.getUniqueName();
				}
			}
		};
	}
}
