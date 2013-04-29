/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
		gate = Arastreju.getInstance().openMasterGate();
	}

	@After
	public void tearDown() {
		gate.close();
	}
	
	// ----------------------------------------------------
	
	@Test
	public void blackBoxTest() throws RBAuthException {
		givenDomainZ();

		EmbeddedAuthUserManager userManager = new EmbeddedAuthUserManager(gate.startConversation(), domainManager);
		Assert.assertNull(userManager.findUser("x"));
		
		final RBUser user = new RBUser();
		user.setEmail("x@lichtflut.de");
		user.setUsername("x");
		
		userManager.registerUser(user, "y", "z");
		
		Assert.assertNotNull(userManager.findUser("x"));
	}

	// ----------------------------------------------------
	
	private void givenDomainZ() {
        Mockito.when(domainManager.findDomainNode("z")).thenReturn(new SNResource());
	}

}
