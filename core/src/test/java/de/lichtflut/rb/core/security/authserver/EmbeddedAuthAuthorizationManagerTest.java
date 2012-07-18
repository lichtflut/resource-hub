/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.security.authserver;

import de.lichtflut.rb.core.eh.RBAuthException;
import de.lichtflut.rb.core.security.RBUser;
import junit.framework.Assert;
import org.arastreju.sge.ModelingConversation;
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

    private static final QualifiedName USER_X1 = new QualifiedName("http://lichtflut.de/users/x1");
	
	@Mock
	private EmbeddedAuthDomainManager domainManager;

    @Mock
	private ModelingConversation conversation;

	
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

        Assert.assertEquals(4, userNode.getAssociations(EmbeddedAuthModule.HAS_ROLE).size());

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
