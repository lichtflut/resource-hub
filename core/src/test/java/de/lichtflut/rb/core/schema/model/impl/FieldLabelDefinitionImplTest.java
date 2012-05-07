/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.Locale;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.junit.Test;

import de.lichtflut.rb.core.schema.model.FieldLabelDefinition;

/**
 * <p>
 * Testclass for {@link FieldLabelDefinitionImpl}.
 * </p>
 * Created: May 7, 2012
 *
 * @author Ravi Knox
 */
public class FieldLabelDefinitionImplTest {

	private FieldLabelDefinition label;
	private String nameGer = "Softwareentwickler";
	private String nameEng = "Software developer";
	
	/**
	 * Test method for {@link de.lichtflut.rb.core.schema.model.impl.FieldLabelDefinitionImpl#FieldLabelDefinitionImpl()}.
	 */
	@Test
	public void testFieldLabelDefinitionImplDefaultConstructor() {
		label = new FieldLabelDefinitionImpl();
		assertNotNull(label);
	}

	/**
	 * Test method for {@link de.lichtflut.rb.core.schema.model.impl.FieldLabelDefinitionImpl#FieldLabelDefinitionImpl()}.
	 */
	@Test
	public void testFieldLabelDefinitionImplResIDConstructor() {
		ResourceID id = new SimpleResourceID();
		label = new FieldLabelDefinitionImpl(id);
		assertEquals("Default label is not as expected", id.getQualifiedName().getSimpleName(), label.getDefaultLabel());
		assertEquals("Locales should be empty", Collections.EMPTY_SET, label.getSupportedLocales());
		assertEquals("Label for given locale should be the default", label.getDefaultLabel(), label.getLabel(Locale.GERMAN));
	}
	
	/**
	 * Test method for {@link de.lichtflut.rb.core.schema.model.impl.FieldLabelDefinitionImpl#FieldLabelDefinitionImpl()}.
	 */
	@Test
	public void testFieldLabelDefinitionImplStringConstructor() {
		String name = "Person";
		label = new FieldLabelDefinitionImpl(name);
		assertEquals("Default label is not as expected", name, label.getDefaultLabel());
		assertEquals("Locales should be empty", Collections.EMPTY_SET, label.getSupportedLocales());
		assertEquals("Label for given locale should be the default", label.getDefaultLabel(), label.getLabel(Locale.GERMAN));
	}

	/**
	 * Test method for {@link de.lichtflut.rb.core.schema.model.impl.FieldLabelDefinitionImpl#getDefaultLabel()}.
	 */
	@Test
	public void testGetDefaultLabel() {
		ResourceID id = new SimpleResourceID();
		label = new FieldLabelDefinitionImpl(id);
		assertEquals("Default label is not as expected", id.getQualifiedName().getSimpleName(), label.getDefaultLabel());
	}

	/**
	 * Test method for {@link de.lichtflut.rb.core.schema.model.impl.FieldLabelDefinitionImpl#getLabel(java.util.Locale)}.
	 */
	@Test
	public void testGetLabel() {
		label = new FieldLabelDefinitionImpl(nameEng);
		label.setLabel(Locale.GERMAN, nameGer);
		
		assertEquals("German label is not as expected", nameGer, label.getLabel(Locale.GERMAN));
		assertEquals("Label for given locale should be the default", nameEng, label.getLabel(Locale.ENGLISH));
		
		label.setLabel(Locale.ENGLISH, nameEng);
		
		assertEquals("English label is not as expected", nameEng, label.getLabel(Locale.ENGLISH));
	}

	/**
	 * Test method for {@link de.lichtflut.rb.core.schema.model.impl.FieldLabelDefinitionImpl#getSupportedLocales()}.
	 */
	@Test
	public void testGetSupportedLocales() {
		label = new FieldLabelDefinitionImpl(nameEng);
		label.setLabel(Locale.GERMAN, nameGer);
		label.setLabel(Locale.ENGLISH, nameEng);
		label.setLabel(Locale.JAPAN, null);
		assertTrue("Supported Languages are not as expected", 3 == label.getSupportedLocales().size());
	}

	/**
	 * Test method for {@link de.lichtflut.rb.core.schema.model.impl.FieldLabelDefinitionImpl#setDefaultLabel(java.lang.String)}.
	 */
	@Test
	public void testSetDefaultLabel() {
		label = new FieldLabelDefinitionImpl(nameEng);
		label.setDefaultLabel(nameGer);
		
		assertEquals("Default label is not as expected", nameGer, label.getDefaultLabel());
	}

	/**
	 * Test method for {@link de.lichtflut.rb.core.schema.model.impl.FieldLabelDefinitionImpl#setLabel(java.util.Locale, java.lang.String)}.
	 */
	@Test
	public void testSetLabel() {
		label = new FieldLabelDefinitionImpl();
		label.setLabel(Locale.GERMAN, nameGer);
		
		assertEquals("Label is not as expected", nameGer, label.getLabel(Locale.GERMAN));
	}

}
