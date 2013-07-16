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
package de.lichtflut.rb.webck.models.perceptions;

import java.util.Collections;
import java.util.List;

import org.apache.wicket.injection.Injector;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.structure.OrderBySerialNumber;

import de.lichtflut.rb.core.perceptions.Perception;
import de.lichtflut.rb.core.services.PerceptionDefinitionService;
import de.lichtflut.rb.webck.models.basic.AbstractLoadableDetachableModel;

/**
 * <p>
 *  Model loading all perceptions.
 * </p>
 *
 * <p>
 *  Created 16.11.12
 * </p>
 *
 * @author Oliver Tigges
 */
public class PerceptionModel extends AbstractLoadableDetachableModel<List<Perception>> {

	@SpringBean
	private PerceptionDefinitionService perceptionDefinitionService;

	// ----------------------------------------------------

	public PerceptionModel() {
		super();
		Injector.get().inject(this);
	}

	// ----------------------------------------------------

	@Override
	public List<Perception> load() {
		List<Perception> perceptions = perceptionDefinitionService.findAllPerceptions();
		Collections.sort(perceptions, new OrderBySerialNumber());
		return perceptions;
	}

	public void remove(final Perception perception){
		int indexOf = getObject().indexOf(perception);
		getObject().remove(indexOf);
	}

}
