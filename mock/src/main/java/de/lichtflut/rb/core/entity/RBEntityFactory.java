/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.core.entity;

import java.util.Date;

import de.lichtflut.rb.core.constants.RBMock;
import de.lichtflut.rb.core.entity.impl.RBEntityImpl;
import de.lichtflut.rb.core.schema.ResourceSchemaFactory;

/**
 * <p>
 * This class provides {@link RBEntity}s for testing purposes.
 * </p>
 * Created: May 7, 2012
 *
 * @author Ravi Knox
 */
public class RBEntityFactory {
	
	public static RBEntity createPersonEntity(){
		RBEntity entity = new RBEntityImpl(ResourceSchemaFactory.buildPersonSchema());
		entity.getField(RBMock.HAS_FIRST_NAME).setValue(0, "Hans");
		entity.getField(RBMock.HAS_LAST_NAME).setValue(0, "Müller");
		entity.getField(RBMock.HAS_DATE_OF_BIRTH).setValue(0, new Date());
		entity.getField(RBMock.HAS_EMAIL).setValue(0, "hmüller@google.de");
		
		return entity;
	}

}
