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

import java.util.Locale;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.impl.CardinalityBuilder;
import de.lichtflut.rb.core.schema.model.impl.ExpressionBasedLabelBuilder;
import de.lichtflut.rb.core.schema.model.impl.FieldLabelDefinitionImpl;
import de.lichtflut.rb.core.schema.model.impl.LabelExpressionParseException;
import de.lichtflut.rb.core.schema.model.impl.PropertyDeclarationImpl;
import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;
import de.lichtflut.rb.webck.data.RBTestConstants;

/**
 * <p>
 * This class provides ResourceSchemas for testing purposes.
 * </p>
 * Created: May 4, 2012
 * 
 * @author Ravi Knox
 */
public class ResourceSchemaFactory {

	public static ResourceSchema buildPersonSchema() {
		ResourceSchemaImpl schema = new ResourceSchemaImpl(RB.PERSON);

		PropertyDeclaration firstname = new PropertyDeclarationImpl(RB.HAS_FIRST_NAME, Datatype.STRING);
		PropertyDeclaration lastname = new PropertyDeclarationImpl(RB.HAS_LAST_NAME, Datatype.STRING);
		PropertyDeclaration address = new PropertyDeclarationImpl(RB.HAS_ADDRESS, Datatype.RESOURCE);
		PropertyDeclaration dateOfBirth = new PropertyDeclarationImpl(RB.HAS_DATE_OF_BIRTH, Datatype.DATE);
		PropertyDeclaration	email = new PropertyDeclarationImpl(RB.HAS_EMAIL, Datatype.STRING);
		PropertyDeclaration children = new PropertyDeclarationImpl(RBTestConstants.HAS_CHILDREN, Datatype.RESOURCE);
		PropertyDeclaration avatar = new PropertyDeclarationImpl(RBTestConstants.HAS_AVATAR, Datatype.FILE);

		address.setConstraint(ConstraintsFactory.buildTypeConstraint(RBTestConstants.ADDRESS));
		email.setConstraint(ConstraintsFactory.buildPublicEmailConstraint());
		children.setConstraint(ConstraintsFactory.buildTypeConstraint(RB.PERSON));

		firstname.setCardinality(CardinalityBuilder.hasExcactlyOne());
		lastname.setCardinality(CardinalityBuilder.hasExcactlyOne());
		address.setCardinality(CardinalityBuilder.hasAtLeastOneUpTo(2));
		dateOfBirth.setCardinality(CardinalityBuilder.hasExcactlyOne());
		email.setCardinality(CardinalityBuilder.hasOptionalOneToMany());
		avatar.setCardinality(CardinalityBuilder.hasExcactlyOne());

		firstname.setFieldLabelDefinition(new FieldLabelDefinitionImpl("Firstname"));
		firstname.getFieldLabelDefinition().setLabel(Locale.GERMAN, "Vorname");
		lastname.setFieldLabelDefinition(new FieldLabelDefinitionImpl("Surename"));
		lastname.getFieldLabelDefinition().setLabel(Locale.GERMAN, "Nachname");
		address.setFieldLabelDefinition(new FieldLabelDefinitionImpl("Address"));
		address.getFieldLabelDefinition().setLabel(Locale.GERMAN, "Adresse");
		dateOfBirth.setFieldLabelDefinition(new FieldLabelDefinitionImpl("Date of Birth"));
		dateOfBirth.getFieldLabelDefinition().setLabel(Locale.GERMAN, "Geburtsdatum");
		email.setFieldLabelDefinition(new FieldLabelDefinitionImpl("E-Mail"));
		email.getFieldLabelDefinition().setLabel(Locale.GERMAN, "Email");
		children.setFieldLabelDefinition(new FieldLabelDefinitionImpl("Children"));
		children.getFieldLabelDefinition().setLabel(Locale.GERMAN, "Kinder");
		avatar.setFieldLabelDefinition(new FieldLabelDefinitionImpl("Avatar"));

		schema.addPropertyDeclaration(firstname);
		schema.addPropertyDeclaration(lastname);
		schema.addPropertyDeclaration(address);
		schema.addPropertyDeclaration(dateOfBirth);
		schema.addPropertyDeclaration(email);
		schema.addPropertyDeclaration(children);
		schema.addPropertyDeclaration(avatar);

		schema.addQuickInfo(RBTestConstants.HAS_AVATAR);
		schema.addQuickInfo(RB.HAS_FIRST_NAME);
		schema.addQuickInfo(RB.HAS_LAST_NAME);

		try {
			schema.setLabelBuilder(new ExpressionBasedLabelBuilder(RB.PERSON.toURI()));
		} catch (LabelExpressionParseException e) {
			e.printStackTrace();
		}
		return schema;
	}

	/**
	 * @return a schema for a Datacenter
	 */
	public static ResourceSchema buildDataCenter() {
		ResourceSchemaImpl schema = new ResourceSchemaImpl(RBTestConstants.DATA_CENTER);

		PropertyDeclaration id = new PropertyDeclarationImpl(RB.HAS_ID, Datatype.STRING);
		schema.addPropertyDeclaration(id);

		PropertyDeclaration name = new PropertyDeclarationImpl(RB.HAS_NAME, Datatype.STRING);
		schema.addPropertyDeclaration(name);

		PropertyDeclaration description = new PropertyDeclarationImpl(RB.HAS_DESCRIPTION, Datatype.STRING);
		schema.addPropertyDeclaration(description);

		PropertyDeclaration hostsMachine = new PropertyDeclarationImpl(RBTestConstants.HOSTS_MACHINE, Datatype.RESOURCE);
		schema.addPropertyDeclaration(hostsMachine);

		PropertyDeclaration inheritsFrom = new PropertyDeclarationImpl(RBTestConstants.INHERITS_FROM, Datatype.RESOURCE);
		inheritsFrom.setConstraint(ConstraintsFactory.buildTypeConstraint(RBTestConstants.SOFTWARE_ITEM));
		schema.addPropertyDeclaration(inheritsFrom);


		schema.addQuickInfo(RB.HAS_ID);
		schema.addQuickInfo(RB.HAS_NAME);
		schema.addQuickInfo(RB.HAS_DESCRIPTION);
		return schema;
	}

}
