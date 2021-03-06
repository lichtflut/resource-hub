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

import de.lichtflut.rb.core.eh.RBAuthException;
import de.lichtflut.rb.core.security.RBUser;
import junit.framework.Assert;
import org.arastreju.sge.Conversation;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.naming.QualifiedName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

/**
 * <p>
 *  Test cases for {@link de.lichtflut.rb.core.security.authserver.EmbeddedAuthUserManager}.
 * </p>
 *
 * <p>
 * 	Created May 21, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class EmbeddedAuthAuthorizationManagerTest {

    private static final String ROLE_A = "role-a";

    private static final String ROLE_B = "role-b";

    private static final String ROLE_C = "role-c";

    private static final QualifiedName USER_X1 = QualifiedName.from("http://lichtflut.de/users/x1");
	
	@Mock
	private EmbeddedAuthDomainManager domainManager;

    @Mock
	private Conversation conversation;

	// ----------------------------------------------------
	
	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}

	@After
	public void tearDown() {
	}
	
	// ----------------------------------------------------
	
	@Test
    public void testUserDomainRoleAssignment() throws RBAuthException {
        givenDomainX();
        givenDomainZ();

        RBUser user = new RBUser(USER_X1);
        user.setEmail("x1@lichtflut.de");
        user.setUsername("x1");

        ResourceNode userNode = new SNResource(USER_X1);

        givenUserX1(userNode);

        EmbeddedAuthAuthorizationManager authManager = new EmbeddedAuthAuthorizationManager(conversation, domainManager);

        authManager.setUserRoles(user, "domain-x", Arrays.asList(ROLE_A, ROLE_B));

        authManager.setUserRoles(user, "domain-z", Arrays.asList(ROLE_A, ROLE_C));

        Assert.assertEquals(4, SNOPS.associations(userNode, EmbeddedAuthModule.HAS_ROLE).size());

	}

	// ----------------------------------------------------

    private void givenDomainX() {
        SNResource domainNode = new SNResource();
        SNResource roleANode = new SNResource();
        SNResource roleBNode = new SNResource();
        Mockito.when(domainManager.findDomainNode("domain-x")).thenReturn(domainNode);
        Mockito.when(domainManager.getOrCreateRole(domainNode, ROLE_A)).thenReturn(roleANode);
        Mockito.when(domainManager.getOrCreateRole(domainNode, ROLE_B)).thenReturn(roleBNode);
    }
	
	private void givenDomainZ() {
        SNResource domainNode = new SNResource();
        SNResource roleANode = new SNResource();
        SNResource roleBNode = new SNResource();
        SNResource roleCNode = new SNResource();
        Mockito.when(domainManager.findDomainNode("domain-z")).thenReturn(domainNode);
        Mockito.when(domainManager.getOrCreateRole(domainNode, ROLE_A)).thenReturn(roleANode);
        Mockito.when(domainManager.getOrCreateRole(domainNode, ROLE_B)).thenReturn(roleBNode);
        Mockito.when(domainManager.getOrCreateRole(domainNode, ROLE_C)).thenReturn(roleCNode);
	}

    private void givenUserX1(ResourceNode userNode) {
        Mockito.when(conversation.findResource(USER_X1)).thenReturn(userNode);
    }

}
