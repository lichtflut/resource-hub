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
package de.lichtflut.rb.core.data;

import java.util.Date;

import org.arastreju.sge.model.nodes.SNResource;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.data.schema.ResourceSchemaFactory;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.impl.RBEntityImpl;

/**
 * <p>
 * This class provides {@link RBEntity}s for testing purposes.
 * </p>
 * Created: May 7, 2012
 *
 * @author Ravi Knox
 */
public class RBEntityFactory {

	/**
	 * Mock Person Entity. Not all Fields are set.
	 * @return
	 */
	public static RBEntity createPersonEntity(){
		RBEntity entity = new RBEntityImpl(new SNResource(), ResourceSchemaFactory.buildPersonSchema());
		entity.getField(RB.HAS_FIRST_NAME).setValue(0, "Hans");
		entity.getField(RB.HAS_LAST_NAME).setValue(0, "Müller");
		entity.getField(RB.HAS_DATE_OF_BIRTH).setValue(0, new Date());
		entity.getField(RB.HAS_EMAIL).setValue(0, "hmüller@google.de");
		return entity;
	}

}
