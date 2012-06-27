/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.mock.schema;

import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.impl.CardinalityBuilder;
import de.lichtflut.rb.core.schema.model.impl.FieldLabelDefinitionImpl;
import de.lichtflut.rb.core.schema.model.impl.PropertyDeclarationImpl;
import de.lichtflut.rb.mock.RBMock;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * This class provides {@link de.lichtflut.rb.core.schema.model.PropertyDeclaration}s for testing purposes.
 * </p>
 * Created: May 7, 2012
 *
 * @author Ravi Knox
 */
public class PropertyDeclarationFactory {

	public static List<PropertyDeclaration> buildPersonPropertyDecls(){
		List<PropertyDeclaration> list = new ArrayList<PropertyDeclaration>();
		list.add(new PropertyDeclarationImpl(RBMock.HAS_FIRST_NAME, Datatype.STRING));
		list.add(new PropertyDeclarationImpl(RBMock.HAS_LAST_NAME, Datatype.STRING));
		list.add(new PropertyDeclarationImpl(RBMock.HAS_ADDRESS, Datatype.RESOURCE));
		list.add(new PropertyDeclarationImpl(RBMock.HAS_DATE_OF_BIRTH, Datatype.DATE));
		list.add(new PropertyDeclarationImpl(RBMock.HAS_EMAIL, Datatype.STRING));
		list.add(buildHasChildrenPropertyDecl());
		return list;
	}
	
	public static PropertyDeclaration buildHasNameProperty(){
		return new PropertyDeclarationImpl(RBMock.HAS_NAME, Datatype.STRING);
	}
	
	public static PropertyDeclaration buildHasChildrenPropertyDecl(){
		PropertyDeclaration decl = new PropertyDeclarationImpl(RBMock.HAS_CHILD_NODE, Datatype.RESOURCE, ConstraintsFactory.buildPublicPersonConstraint());
		decl.setCardinality(CardinalityBuilder.extractFromString("[n..n]"));
		decl.setFieldLabelDefinition(new FieldLabelDefinitionImpl("Children"));
		return decl;
	}
}
