/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.schema;

import java.util.ArrayList;
import java.util.List;

import de.lichtflut.rb.core.constants.RBMock;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.impl.PropertyDeclarationImpl;

/**
 * <p>
 * This class provides {@link PropertyDeclaration}s for testing purposes.
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
		list.add(new PropertyDeclarationImpl(RBMock.HAS_CHILD_NODE, Datatype.RESOURCE));
		return list;
	}
	
	public static PropertyDeclaration buildHasNameProperty(){
		return new PropertyDeclarationImpl(RBMock.HAS_NAME, Datatype.STRING);
	}
}
