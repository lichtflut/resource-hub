/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.mock;

import org.arastreju.sge.model.ResourceID;

import de.lichtflut.rb.core.schema.custom.LabelBuilderLocator;
import de.lichtflut.rb.core.schema.model.EntityLabelBuilder;
import de.lichtflut.rb.web.util.StaticLabelBuilders;

/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created Nov 10, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class MockLabelBuilderLocator implements LabelBuilderLocator {

	/** 
	 * {@inheritDoc}
	 */
	@Override
	public EntityLabelBuilder forType(final ResourceID type) {
		if (MockResourceSchemaFactory.ADRESS.equals(type)){
			return StaticLabelBuilders.forAddress();
		} else if (MockResourceSchemaFactory.CITY.equals(type)){
				return StaticLabelBuilders.forCity();
		} else if (MockResourceSchemaFactory.ORGANIZATION.equals(type)){
				return StaticLabelBuilders.forOrganization();
		} else if (MockResourceSchemaFactory.PERSON.equals(type)){
				return StaticLabelBuilders.forPerson();
		} else {
			return EntityLabelBuilder.DEFAULT;
		}
	}
}
