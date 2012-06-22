/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models.user;

import java.util.Collections;
import java.util.Set;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.lichtflut.rb.core.security.RBUser;
import de.lichtflut.rb.core.services.SecurityService;
import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;

/**
 * <p>
 *  Model for a user's permissions.
 * </p>
 *
 * <p>
 * 	Created May 7, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class UserPermissionModel extends DerivedDetachableModel<Set<String>, RBUser> {
	
	@SpringBean
	private SecurityService securityService;
	
	@SpringBean 
	private ServiceContext context;
	
	// ----------------------------------------------------
	
	public UserPermissionModel(RBUser user) {
		super(user);
		Injector.get().inject(this);
	}
	
	public UserPermissionModel(IModel<RBUser> user) {
		super(user);
		Injector.get().inject(this);
	}
	
	// ----------------------------------------------------

	/** 
	 * {@inheritDoc}
	 */
	@Override
	protected Set<String> derive(RBUser user) {
		if (user != null) {
			String domain = context.getDomain();
			return securityService.getUserPermissions(user, domain);
		} else {
			return Collections.emptySet();
		}
	}
	
	/** 
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> getDefault() {
		return Collections.emptySet();
	}
	
}
