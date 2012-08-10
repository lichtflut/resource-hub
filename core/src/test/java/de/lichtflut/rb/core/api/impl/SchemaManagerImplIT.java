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
 *  Integration test for schema management.
 *
 *  TODO: Move to integration-test module
 *
 * </p>
 *
 * <p>
 * Created Oct 7, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class SchemaManagerImplIT {

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

	// -----------------------------------------------------

	@Test
	public void testStoreAndRetrieve() {
		final SchemaManager manager = new SchemaManagerImpl(conversationFactory);
		final ResourceSchema original = ResourceSchemaFactory.buildPersonSchema();
		manager.store(original);

		final ResourceSchema found = manager.findSchemaForType(original.getDescribedType());

		assertThat(found, notNullValue());
		assertThat(found.getPropertyDeclarations().size(), is(original.getPropertyDeclarations().size()));
		PropertyDeclaration decl = found.getPropertyDeclarations().get(0);
		PropertyDeclaration originalDecl = original.getPropertyDeclarations().get(0);
		assertThat(decl.getCardinality().getMaxOccurs(), is(originalDecl.getCardinality().getMaxOccurs()));
		assertThat(decl.getCardinality().getMinOccurs(), is(originalDecl.getCardinality().getMinOccurs()));
		assertThat(decl.getConstraint(), nullValue());
		assertThat(decl.getDatatype(), equalTo(originalDecl.getDatatype()));
		assertThat(decl.getFieldLabelDefinition(), instanceOf(FieldLabelDefinition.class));
		assertThat(decl.getPropertyDescriptor(), equalTo(originalDecl.getPropertyDescriptor()));
	}

	@Test
	public void testReplacing() {
		final SchemaManager manager = new SchemaManagerImpl(conversationFactory);
		final ResourceSchema original = ResourceSchemaFactory.buildPersonSchema();
		manager.store(original);

		final ResourceSchema found = manager.findSchemaForType(original.getDescribedType());
		assertThat(found, notNullValue());
		assertThat(found.getPropertyDeclarations().size(), is(original.getPropertyDeclarations().size()));

		final ResourceSchema other = new ResourceSchemaImpl(original.getDescribedType());
		manager.store(other);

		ResourceSchema retrieved = manager.findSchemaForType(original.getDescribedType());
		assertNotNull(retrieved);
		assertThat(found.getPropertyDeclarations().size(), is(original.getPropertyDeclarations().size()));

		manager.store(original);

		retrieved = manager.findSchemaForType(original.getDescribedType());
		assertNotNull(retrieved);
		assertThat(found.getPropertyDeclarations().size(), is(original.getPropertyDeclarations().size()));

	}

    // ----------------------------------------------------



}