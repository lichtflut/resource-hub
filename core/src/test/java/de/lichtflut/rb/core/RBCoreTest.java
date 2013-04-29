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
package de.lichtflut.rb.core;

import de.lichtflut.rb.core.security.AuthModule;
import de.lichtflut.rb.core.security.DomainManager;
import de.lichtflut.rb.core.services.EntityManager;
import de.lichtflut.rb.core.services.FileService;
import de.lichtflut.rb.core.services.SchemaManager;
import de.lichtflut.rb.core.services.SemanticNetworkService;
import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.core.services.TypeManager;
import org.arastreju.sge.Conversation;
import org.junit.Before;
import org.mockito.Mock;

import java.util.Locale;

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
	protected Conversation conversation;

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
