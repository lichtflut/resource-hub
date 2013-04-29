/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.lichtflut.rb.webck.data.schema;

import java.util.ArrayList;
import java.util.List;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.impl.CardinalityBuilder;
import de.lichtflut.rb.core.schema.model.impl.FieldLabelDefinitionImpl;
import de.lichtflut.rb.core.schema.model.impl.PropertyDeclarationImpl;

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
		list.add(new PropertyDeclarationImpl(RB.HAS_FIRST_NAME, Datatype.STRING));
		list.add(new PropertyDeclarationImpl(RB.HAS_LAST_NAME, Datatype.STRING));
		list.add(new PropertyDeclarationImpl(RB.HAS_ADDRESS, Datatype.RESOURCE));
		list.add(new PropertyDeclarationImpl(RB.HAS_DATE_OF_BIRTH, Datatype.DATE));
		list.add(new PropertyDeclarationImpl(RB.HAS_EMAIL, Datatype.STRING));
		list.add(buildHasChildrenPropertyDecl());
		return list;
	}

	public static PropertyDeclaration buildHasNameProperty(){
		return new PropertyDeclarationImpl(RB.HAS_NAME, Datatype.STRING);
	}

	public static PropertyDeclaration buildHasChildrenPropertyDecl(){
		PropertyDeclaration decl = new PropertyDeclarationImpl(RB.HAS_CHILD_NODE, Datatype.RESOURCE, ConstraintsFactory.buildPublicPersonConstraint());
		decl.setCardinality(CardinalityBuilder.extractFromString("[n..n]"));
		decl.setFieldLabelDefinition(new FieldLabelDefinitionImpl("Children"));
		return decl;
	}
}
