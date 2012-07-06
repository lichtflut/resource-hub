/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models.domains;

import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.RBDomain;
import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.webck.models.ConditionalModel;
import de.lichtflut.rb.webck.models.basic.AbstractLoadableDetachableModel;
import de.lichtflut.rb.webck.models.basic.DerivedModel;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.context.DomainIdentifier;

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
public class CurrentDomainModel extends AbstractLoadableDetachableModel<RBDomain> {

	@SpringBean
	private AuthModule authModule;
	
	@SpringBean 
	private ServiceContext context;
	
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
	public RBDomain load() {
		final String domain = context.getDomain();
		if (domain != null) {
			return authModule.getDomainManager().findDomain(domain);
		} else {
			return null;
		}
	}

	/**
	 * @return A model providing the domain's name.
	 */
	public static IModel<String> displayNameModel() {
		return new DerivedModel<String, RBDomain>(new CurrentDomainModel()) {
			@Override
			protected String derive(RBDomain original) {
				if (original.getTitle() != null) {
					return original.getTitle();
				} else {
					return original.getName();
				}
			}
		};
	}
	
	/**
	 * @return A conditional model checking if the current domain is the master domain.
	 */
	public static ConditionalModel<RBDomain> isMasterDomain() {
		return new ConditionalModel<RBDomain>(new CurrentDomainModel()) {
			@Override
			public boolean isFulfilled() {
				return DomainIdentifier.MASTER_DOMAIN.equals((getObject().getName()));
			}
		};
	}
}
