/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.security.authserver;

import junit.framework.Assert;

import org.arastreju.sge.Arastreju;
import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.model.nodes.SNResource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import de.lichtflut.rb.core.eh.RBAuthException;
import de.lichtflut.rb.core.security.RBUser;

/**
 * <p>
 *  Test cases for {@link EmbeddedAuthUserManager}.
 * </p>
 *
 * <p>
 * 	Created May 21, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class EmbeddedAuthUserManagerTest {
	
	@Mock
	private EmbeddedAuthDomainManager domainManager;
	
	private ArastrejuGate gate;
	
	// ----------------------------------------------------
	
	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
		gate = Arastreju.getInstance().rootContext();
	}

	@After
	public void tearDown() {
		gate.close();
	}
	
	// ----------------------------------------------------
	
	@Test
	public void blackBoxTest() throws RBAuthException {
		initDomainZ();
		EmbeddedAuthUserManager userManager = new EmbeddedAuthUserManager(gate.startConversation(), domainManager);
		Assert.assertNull(userManager.findUser("x"));
		
		final RBUser user = new RBUser();
		user.setEmail("x@lichtflut.de");
		user.setUsername("x");
		
		userManager.registerUser(user, "y", "z");
		
		Assert.assertNotNull(userManager.findUser("x"));
	}
	
	// ----------------------------------------------------
	
	private void initDomainZ() {
		Mockito.when(domainManager.findDomainNode("z")).thenReturn(new SNResource());
	}

}
