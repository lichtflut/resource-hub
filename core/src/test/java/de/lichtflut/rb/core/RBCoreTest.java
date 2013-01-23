/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core;

import java.util.Locale;

import org.arastreju.sge.ModelingConversation;
import org.junit.Before;
import org.mockito.Mock;

import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.DomainManager;
import de.lichtflut.rb.core.services.EntityManager;
import de.lichtflut.rb.core.services.FileService;
import de.lichtflut.rb.core.services.SchemaManager;
import de.lichtflut.rb.core.services.SemanticNetworkService;
import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.core.services.TypeManager;

/**
 * <p>
 * Base class vor all RB Testcases.
 * </p><p>
 * The {@link Locale} is set to {@link Locale#ENGLISH} by default.
 * </p><p>
 * All services are instantiated.
 * </p>
 * Created: Nov 5, 2012
 *
 * @author Ravi Knox
 */
public class RBCoreTest {

	// --------- Services -----------------------------------

	@Mock
	protected SemanticNetworkService networkService;

	@Mock
	protected ModelingConversation conversation;

	@Mock
	protected EntityManager entityManager;

	@Mock
	protected SchemaManager schemaManager;

	@Mock
	protected TypeManager typeManager;

	@Mock
	protected DomainManager domainManager;

	@Mock
	protected ServiceContext serviceContext;

	@Mock
	protected FileService fileService;

	@Mock
	protected AuthModule authModule;

	// ------------------------------------------------------

	@Before
	public void setUp() {
		Locale.setDefault(Locale.ENGLISH);
	}
}
