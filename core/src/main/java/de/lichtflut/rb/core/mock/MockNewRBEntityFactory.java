/*
 * Copyright 2011 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.mock;

import java.util.ArrayList;
import java.util.List;

import org.arastreju.sge.model.ElementaryDataType;
import org.arastreju.sge.model.SimpleResourceID;

import de.lichtflut.rb.core.schema.model.IRBEntity;
import de.lichtflut.rb.core.schema.model.IRBField;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.impl.CardinalityBuilder;
import de.lichtflut.rb.core.schema.model.impl.ConstraintFactory;
import de.lichtflut.rb.core.schema.model.impl.NewRBEntity;
import de.lichtflut.rb.core.schema.model.impl.PropertyAssertionImpl;
import de.lichtflut.rb.core.schema.model.impl.PropertyDeclarationImpl;
import de.lichtflut.rb.core.schema.model.impl.ResourceSchemaImpl;

/**
 * <p>
 * This class provides static {@link IRBEntity}s for testing purposes.
 * </p>
 * Created: Aug 16, 2011
 *
 * @author Ravi Knox
 */
public class MockNewRBEntityFactory {

	/**
	 * Creates a {@link NewRBEntity} with appropriate values.
	 * @return Instance of {@link NewRBEntity}
	 */
	public static NewRBEntity createStringBasedNewRBEntity(){
		List<List<Object>> list = new ArrayList<List<Object>>();
		List<Object> emailList = new ArrayList<Object>();
		List<Object> ageList = new ArrayList<Object>();
		List<Object> childrenList = new ArrayList<Object>();
		emailList.add("max@moritz.de");
		emailList.add("chef@koch.de");
		ageList.add("23");
		childrenList.add("3");
		list.add(emailList);
		list.add(ageList);
		list.add(childrenList);
		int counter = 0;
		NewRBEntity entity = new NewRBEntity(createStringOnlySchema());
		for (IRBField field : entity.getAllFields()) {
			field.setFieldValues(list.get(counter));
			counter++;
		}
		return entity;
	}
	
	/**
	 * <p>
	 * Returns a {@link ResourceSchema} based on Strings only.
	 * </p>
	 * @return schema -
	 */
	private static ResourceSchema createStringOnlySchema() {
		ResourceSchemaImpl schema = new ResourceSchemaImpl(
				"http://lichtflut.de#", "Stringschema");
		PropertyDeclarationImpl p2 = new PropertyDeclarationImpl();
		PropertyDeclarationImpl p3 = new PropertyDeclarationImpl();
		PropertyDeclarationImpl p4 = new PropertyDeclarationImpl();
		p2.setName("http://lichtflut.de#email");
		p3.setName("http://lichtflut.de#age");
		p4.setName("http://lichtflut.de#children");

		p2.setElementaryDataType(ElementaryDataType.STRING);
		p3.setElementaryDataType(ElementaryDataType.STRING);
		p4.setElementaryDataType(ElementaryDataType.STRING);

		p2.addConstraint(ConstraintFactory.buildConstraint(".*@.*"));

		PropertyAssertionImpl pa2 = new PropertyAssertionImpl(
				new SimpleResourceID("http://lichtflut.de#", "hasEmail"), p2);
		PropertyAssertionImpl pa3 = new PropertyAssertionImpl(
				new SimpleResourceID("http://lichtflut.de#", "hasAge"), p3);
		PropertyAssertionImpl pa4 = new PropertyAssertionImpl(
				new SimpleResourceID("http://lichtflut.de#", "hasChild"), p4);

		pa2.setCardinality(CardinalityBuilder.hasAtLeastOneUpTo(2));
		pa3.setCardinality(CardinalityBuilder.hasExcactlyOne());
		pa4.setCardinality(CardinalityBuilder.hasOptionalOneToMany());

		schema.addPropertyAssertion(pa2);
		schema.addPropertyAssertion(pa3);
		schema.addPropertyAssertion(pa4);

		return schema;
	}
}
