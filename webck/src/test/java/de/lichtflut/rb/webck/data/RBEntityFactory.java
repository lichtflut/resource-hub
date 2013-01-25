/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.data;

import java.util.Date;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.impl.RBEntityImpl;
import de.lichtflut.rb.webck.data.schema.ResourceSchemaFactory;

import org.arastreju.sge.model.nodes.SNResource;

/**
 * <p>
 * This class provides {@link de.lichtflut.rb.core.entity.RBEntity}s for testing purposes.
 * </p>
 * Created: May 7, 2012
 *
 * @author Ravi Knox
 */
public class RBEntityFactory {

	public static RBEntity createPersonEntity(){
		RBEntity entity = new RBEntityImpl(new SNResource(), ResourceSchemaFactory.buildPersonSchema());
		entity.getField(RB.HAS_FIRST_NAME).setValue(0, "Hans");
		entity.getField(RB.HAS_LAST_NAME).setValue(0, "Müller");
		entity.getField(RB.HAS_DATE_OF_BIRTH).setValue(0, new Date());
		entity.getField(RB.HAS_EMAIL).setValue(0, "hmüller@google.de");
		entity.getField(RBTestConstants.HAS_AVATAR).setValue(0, "dummy-id.jpeg");
		return entity;
	}


}