/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.rest.api;

import javax.ws.rs.core.MediaType;

import org.junit.Test;
import org.springframework.stereotype.Component;

import de.lichtflut.rb.core.security.DomainManager;
import de.lichtflut.rb.core.security.RBDomain;
import de.lichtflut.rb.rest.api.models.generate.SystemDomain;
import de.lichtflut.rb.rest.api.models.generate.SystemIdentity;

/**
 * <p>
 * TODO: To document
 * </p>
 * 
 * @author Nils Bleisch (nbleisch@lichtflut.de)
 * @created May 10, 2012
 */
@Component
public class DomainTest extends TestBase {

	private static final SystemIdentity TEST_USER = new SystemIdentity();
	private static final SystemDomain TEST_DOMAIN = new SystemDomain();

	
	// Testcase initializing
	static {
		// Init test-user identity
		TEST_USER.setId("test@test.de");
		TEST_USER.setPassword("test");
		TEST_USER.setUsername("test");
		
		
		TEST_DOMAIN.setDescription("test");
		TEST_DOMAIN.setDomainIdentifier("test");
		TEST_DOMAIN.setTitle("test");
		TEST_DOMAIN.setDomainAdministrator(TEST_USER);
	}
	
	@Override
	public void initTestCase(){
		registerDomain(TEST_DOMAIN);
		setCurrentSystemDomain(TEST_DOMAIN);
		//Register user on current system domain "test"
		registerSystemUser(TEST_USER);
		setCurrentSystemUser(TEST_USER);
	}
	
	
	
	@Test
	public void testDeleteAndCreateDomain(){
		DomainManager manager = getAuthModule().getDomainManager();
		//Find current domain
		RBDomain domain = manager.findDomain(TEST_DOMAIN.getDomainIdentifier());
		//Domain must be present
		assertNotNull("Domain " + TEST_DOMAIN.getDomainIdentifier() + " has to be present", domain);
		
		String path = "domain/" + TEST_DOMAIN.getDomainIdentifier();
		
		getWebResource(true).path(path).delete();
		
		domain = manager.findDomain(TEST_DOMAIN.getDomainIdentifier());
		//Domain doesnt exist
		assertNull("Domain " + TEST_DOMAIN.getDomainIdentifier() + " has to be absent", domain);
		
		getWebResource(true).path(TEST_DOMAIN.getDomainIdentifier()).entity(TEST_DOMAIN,MediaType.APPLICATION_JSON).post();
		
		domain = manager.findDomain(TEST_DOMAIN.getDomainIdentifier());
		//Domain must be present
		assertNull("Domain " + TEST_DOMAIN.getDomainIdentifier() + " has to be present", domain);
	}

}
