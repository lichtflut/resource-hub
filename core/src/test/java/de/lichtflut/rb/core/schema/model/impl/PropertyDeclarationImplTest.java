/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema.model.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.SimpleResourceID;
import org.junit.Test;

import de.lichtflut.rb.core.schema.model.Constraint;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;

/**
 * Testclass for {@link PropertyDeclarationImpl}.
 * <br>
 * Created: May 2, 2012
 *
 * @author Ravi Knox
 * TODO: create some tests...
 */
public class PropertyDeclarationImplTest {

	@Test
	public void testPropertyDeclImpl() {
		PropertyDeclaration decl = new PropertyDeclarationImpl();
		assertNotNull(decl);
	}
	
	@Test
	public void testPropertyDeclImplWithParams(){
		String label = "field1";
		String pattern = "htp://lf.de/person#123456-lki8";
		ResourceID resID = new SimpleResourceID("http://lf.de/test#" + label);
		Constraint constr = ConstraintBuilder.buildLiteralConstraint(pattern);
		
		PropertyDeclaration pdec = new PropertyDeclarationImpl(resID, Datatype.STRING, constr);
		
		assertEquals("Label donot match", label, pdec.getFieldLabelDefinition().getDefaultLabel());
		assertEquals("ResourceID does not match", resID, pdec.getPropertyDescriptor());
		assertEquals("Constraint is not as expected", pattern, pdec.getConstraint().getLiteralConstraint().toString());
		assertEquals("DataType does not match", Datatype.STRING, pdec.getDatatype());
		assertEquals("Min value of cardinality is not as expected", 0, pdec.getCardinality().getMinOccurs());
		assertEquals("Max value of cardinality is not as expected", Integer.MAX_VALUE, pdec.getCardinality().getMaxOccurs());
	}

}
