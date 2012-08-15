/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.api.impl;

import static org.mockito.Mockito.when;

import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.persistence.TransactionControl;
import org.arastreju.sge.query.Query;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.services.ConversationFactory;
import de.lichtflut.rb.core.services.impl.SchemaManagerImpl;

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
	private ModelingConversation conversation;

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




}
