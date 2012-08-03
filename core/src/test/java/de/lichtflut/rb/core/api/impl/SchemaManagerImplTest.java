/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.api.impl;

import de.lichtflut.rb.core.RBConfig;
import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.schema.model.FieldLabelDefinition;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;
import de.lichtflut.rb.core.services.ConversationFactory;
import de.lichtflut.rb.core.services.SchemaManager;
import de.lichtflut.rb.core.services.impl.SchemaManagerImpl;
import de.lichtflut.rb.mock.schema.ResourceSchemaFactory;
import org.arastreju.sge.Arastreju;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.persistence.TransactionControl;
import org.arastreju.sge.persistence.TxProvider;
import org.arastreju.sge.query.Query;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
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
