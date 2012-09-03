/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.parser;

import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.schema.parser.impl.rsf.RsfSchemaParser;
import de.lichtflut.rb.core.services.ConversationFactory;
import de.lichtflut.rb.core.services.SchemaImporter;
import de.lichtflut.rb.core.services.SchemaManager;
import de.lichtflut.rb.core.services.impl.SchemaManagerImpl;
import junit.framework.Assert;
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SNResource;
import org.arastreju.sge.persistence.TransactionControl;
import org.arastreju.sge.query.FieldParam;
import org.arastreju.sge.query.Query;
import org.arastreju.sge.query.SimpleQueryResult;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

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
    public void testRsfParsing() throws IOException {
        final InputStream in =
                getClass().getClassLoader().getResourceAsStream("test-schema.rsf");

        final RsfSchemaParser parser = new RsfSchemaParser();
        ParsedElements parsedElements = parser.parse(in);
        System.out.println(parsedElements.getSchemas());
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
