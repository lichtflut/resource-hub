/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models.domains;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.RBDomain;
import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.webck.models.CurrentUserModel;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;

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
public class AlternateDomainsModel extends DerivedDetachableModel<List<RBDomain>, RBUser> {

	@SpringBean
	private AuthModule authModule;
	
	@SpringBean 
	private ServiceContext context;
	
	// ----------------------------------------------------
	
	/**
	 * Default constructor. Will use current user.
	 */
	public AlternateDomainsModel() {
		this(new CurrentUserModel());
	}
	
	/**
	 * Constructor.
	 */
	public AlternateDomainsModel(IModel<RBUser> userModel) {
		super(userModel);
		Injector.get().inject(this);
	}
	
	// ----------------------------------------------------
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	protected List<RBDomain> derive(final RBUser user) {
		final String currentDomain = context.getDomain();
		
		final List<RBDomain> domains = new ArrayList<RBDomain>();
		final Collection<RBDomain> all = authModule.getDomainManager().getDomainsForUser(user);
		for (RBDomain current : all) {
			if (!current.getName().equals(currentDomain)) {
				domains.add(current);
			}
		}
		return domains;
	}

}
