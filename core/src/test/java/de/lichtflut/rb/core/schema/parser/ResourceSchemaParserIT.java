/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.parser;

import de.lichtflut.rb.core.RBConfig;
import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.services.ConversationFactory;
import de.lichtflut.rb.core.services.SchemaImporter;
import de.lichtflut.rb.core.services.SchemaManager;
import de.lichtflut.rb.core.services.impl.SchemaManagerImpl;
import junit.framework.Assert;
import org.arastreju.sge.Arastreju;
import org.arastreju.sge.ModelingConversation;
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
 *
 *  TODO: Move to integration-test module
 *
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

    private ModelingConversation conversation;

    @Mock
    private ConversationFactory conversationFactory;

    // -----------------------------------------------------

    @Before
    public void setUp() {
        final Arastreju aras = Arastreju.getInstance(new RBConfig().getArastrejuConfiguration());
        this.conversation = aras.openMasterGate().startConversation();

        MockitoAnnotations.initMocks(this);
        Mockito.reset(conversationFactory);
        when(conversationFactory.getConversation(RBSystem.TYPE_SYSTEM_CTX)).thenReturn(conversation);
    }
	
	// ----------------------------------------------------
	
	@Test
	public void testJsonImport() throws IOException {
		final InputStream in = 
				getClass().getClassLoader().getResourceAsStream("test-schema.json");

        final SchemaManager manager = new SchemaManagerImpl(conversationFactory);
        final SchemaImporter importer = manager.getImporter("json");
		importer.read(in);
		
		Assert.assertEquals(5, manager.findAllResourceSchemas().size());
		
		Assert.assertEquals(1, manager.findPublicConstraints().size());
	}
	
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
