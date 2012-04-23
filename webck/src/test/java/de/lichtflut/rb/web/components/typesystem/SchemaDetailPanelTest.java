/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.components.typesystem;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.tester.WicketTester;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.model.SimpleResourceID;
import org.arastreju.sge.naming.QualifiedName;
import org.junit.Before;
import org.junit.Test;

import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.impl.CardinalityBuilder;
import de.lichtflut.rb.core.schema.model.impl.ConstraintBuilder;
import de.lichtflut.rb.core.schema.model.impl.PropertyDeclarationImpl;
import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;
import de.lichtflut.rb.core.schema.model.impl.TypeDefinitionImpl;
import de.lichtflut.rb.webck.components.form.RBStandardButton;
import de.lichtflut.rb.webck.components.typesystem.schema.SchemaDetailPanel;

/**
 * <p>
 *  Testcase for {@link SchemaDetailPanel}.
 * </p>
 *
 * <p>
 * 	Created Feb 27, 2012
 * </p>
 *
 * @author Ravi Knox
 */
public class SchemaDetailPanelTest{
	
	private WicketTester tester;
	
	@Before
	public void setUp() {
		tester = new WicketTester();
	}

	@Test
	public void testPanel(){
		ResourceSchema schema = createSchema();
		IModel<ResourceSchema> model = Model.of(schema);
		Panel panel = new SchemaDetailPanel("test", model);
		tester.startComponentInPage(panel);
		tester.assertNoErrorMessage();
		tester.assertNoInfoMessage();
		tester.assertComponent("test:form:deleteButton", RBStandardButton.class); 
		tester.assertComponent("test:form:addButton", RBStandardButton.class);
		tester.assertComponent("test:form:editButton", RBStandardButton.class);
		tester.assertContains(">http://lichtflut.de#Person");
		tester.assertContains("#TestRes");
		tester.assertContains(">hatAlter");
		tester.assertContains(">\\[1\\.\\.3868686\\]");
		tester.assertContains(">.*@.*");
	}

	/**
	 * @return the created {@link ResourceSchema}
	 */
	private ResourceSchema createSchema() {
		ResourceSchemaImpl schema = new ResourceSchemaImpl(SNOPS.id(new QualifiedName("http://lichtflut.de#Person")));

		TypeDefinitionImpl p1 = new TypeDefinitionImpl();
		TypeDefinitionImpl p2 = new TypeDefinitionImpl();
		TypeDefinitionImpl p3 = new TypeDefinitionImpl();
		p1.setName("http://lichtflut.de#geburtsdatum");
		p2.setName("http://lichtflut.de#email");
		p3.setName("http://lichtflut.de#alter");
 
		p1.setElementaryDataType(Datatype.RESOURCE);
		p2.setElementaryDataType(Datatype.STRING);
		p3.setElementaryDataType(Datatype.INTEGER);

		p1.addConstraint(ConstraintBuilder.buildConstraint(new SimpleResourceID("http://lichtflut.de#TestRes")));
		p2.addConstraint(ConstraintBuilder.buildConstraint(".*@.*"));
		PropertyDeclarationImpl pa1 = new PropertyDeclarationImpl(new SimpleResourceID("http://lichtflut.de#","hatGeburtstag"), p1);
		PropertyDeclarationImpl pa2 = new PropertyDeclarationImpl(new SimpleResourceID("http://lichtflut.de#","hatEmail"), p2);
		PropertyDeclarationImpl pa3 = new PropertyDeclarationImpl(new SimpleResourceID("http://lichtflut.de#","hatAlter"), p3);
		pa1.setCardinality(CardinalityBuilder.hasExcactlyOne());
		pa2.setCardinality(CardinalityBuilder.hasAtLeastOneUpTo(3868686));
		pa3.setCardinality(CardinalityBuilder.hasExcactlyOne());

		schema.addPropertyDeclaration(pa1);
		schema.addPropertyDeclaration(pa2);
		schema.addPropertyDeclaration(pa3);

		return schema;
	}
}
