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
import de.lichtflut.rb.core.services.ConversationFactory;
import de.lichtflut.rb.core.services.SchemaImporter;
import de.lichtflut.rb.core.services.SchemaManager;
import de.lichtflut.rb.core.services.impl.SchemaManagerImpl;
import junit.framework.Assert;
import org.arastreju.sge.Arastreju;
import org.arastreju.sge.ArastrejuGate;
import org.arastreju.sge.Conversation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.io.InputStream;

import static org.mockito.Mockito.when;

/**
 * <p>
 *  Integration test for resource schema parser.
 * </p>
 *
 *  <p>
 * 	 Created Apr. 14, 2011
 *  </p>
 *
 * @author Nils Bleisch
 *
 */
public class ResourceSchemaParserIT {

	private Conversation conversation;

	@Mock
	private ConversationFactory conversationFactory;

	private ArastrejuGate gate;

	// -----------------------------------------------------

	@Before
	public void setUp() {
		final Arastreju aras = Arastreju.getInstance();
		gate = aras.openMasterGate();
		this.conversation = gate.startConversation();

		MockitoAnnotations.initMocks(this);
		Mockito.reset(conversationFactory);
		when(conversationFactory.getConversation(RBSystem.TYPE_SYSTEM_CTX)).thenReturn(conversation);
	}

	@After
	public void tearDown(){
		conversation.close();
		gate.close();
	}

	// ----------------------------------------------------

	@Test
	public void testRsfImport() throws IOException {
		final InputStream in =
				getClass().getClassLoader().getResourceAsStream("test-schema.rsf");

		final SchemaManager manager = new SchemaManagerImpl(conversationFactory);
		final SchemaImporter importer = manager.getImporter("rsf");
		importer.read(in);
		Assert.assertEquals(2, manager.findAllResourceSchemas().size());

		Assert.assertEquals(1, manager.findPublicConstraints().size());
	}


}
