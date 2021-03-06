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
package de.lichtflut.rb.core.schema.parser;

import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.schema.parser.exception.SchemaParsingException;
import de.lichtflut.rb.core.schema.parser.impl.rsf.RsfSchemaParser;
import de.lichtflut.rb.core.services.ConversationFactory;
import de.lichtflut.rb.core.services.SchemaImporter;
import de.lichtflut.rb.core.services.SchemaManager;
import de.lichtflut.rb.core.services.impl.SchemaManagerImpl;
import org.arastreju.sge.Conversation;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.persistence.TransactionControl;
import org.arastreju.sge.query.Query;
import org.arastreju.sge.query.SimpleQueryResult;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.io.InputStream;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * <p>
 *  Some tests to proof and specify the ResourceSchemaParser.
 * </p>
 *
 *  <p>
 * 	 Created Apr. 14, 2011
 *  </p>
 *
 * @author Nils Bleisch
 *
 */
public class ResourceSchemaParserTest {

	@Mock
	private Query query;

	@Mock
	private TransactionControl tx;

	@Mock
	private Conversation conversation;

	@Mock
	private ConversationFactory conversationFactory;

	// -----------------------------------------------------

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		Mockito.reset(conversation);
		when(conversationFactory.getConversation(RBSystem.TYPE_SYSTEM_CTX)).thenReturn(conversation);
		when(conversation.beginTransaction()).thenReturn(tx);
		when(conversation.createQuery()).thenReturn(query);
	}

	// -----------------------------------------------------

	@Test
	public void testRsfImport() throws IOException {

		givenNoSchemasYet();
		givenResolverReturnsNewResources();

		final InputStream in =
				getClass().getClassLoader().getResourceAsStream("test-schema.rsf");

		final SchemaManager manager = new SchemaManagerImpl(conversationFactory);
		final SchemaImporter importer = manager.getImporter("rsf");
		importer.read(in);
	}

	@Test
	public void testRsfParsing() throws IOException, SchemaParsingException {
		final InputStream in =
				getClass().getClassLoader().getResourceAsStream("test-schema.rsf");

		final RsfSchemaParser parser = new RsfSchemaParser();
		parser.parse(in);
	}

	// -- GIVEN -------------------------------------------

	public void givenNoSchemasYet() {
		Query schemaQuery = mock(Query.class);
		when(query.addField(any(ResourceID.class), any(ResourceID.class))).thenReturn(schemaQuery);
		when(schemaQuery.getResult()).thenReturn(SimpleQueryResult.EMPTY);
	}

	public void givenResolverReturnsNewResources() {
		when(conversation.resolve(any(ResourceID.class))).thenReturn(new SNResource());
	}

}
