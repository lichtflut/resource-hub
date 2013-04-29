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
package de.lichtflut.rb.core.api.impl;

import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.services.ConversationFactory;
import de.lichtflut.rb.core.services.impl.SchemaManagerImpl;
import org.arastreju.sge.Conversation;
import org.arastreju.sge.persistence.TransactionControl;
import org.arastreju.sge.query.Query;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

/**
 * <p>
 * Test cases for {@link SchemaManagerImpl}.
 * </p>
 * 
 * <p>
 * Created Oct 7, 2011
 * </p>
 * 
 * @author Oliver Tigges
 */
public class SchemaManagerImplTest {

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

}
