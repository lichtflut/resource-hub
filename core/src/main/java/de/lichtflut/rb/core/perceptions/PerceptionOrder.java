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
package de.lichtflut.rb.core.perceptions;

import java.util.List;

import org.arastreju.sge.model.nodes.ResourceNode;

import de.lichtflut.rb.core.common.SerialNumberOrderedNodesContainer;


/**
 * <p>
 * Re-arrange Perception order.
 * </p>
 * Created: Jan 14, 2013
 *
 * @author Ravi Knox
 */
public class PerceptionOrder extends SerialNumberOrderedNodesContainer {

	private final List<Perception> perceptions;

	// ---------------- Constructor -------------------------

	public PerceptionOrder(final List<Perception> perceptions){
		this.perceptions = perceptions;
	}

	// ------------------------------------------------------

	public void swap(final Perception a, final Perception b){
		new SerialNumberOrderedNodesContainer() {
			@Override
			protected List<? extends ResourceNode> getList() {
				return perceptions;
			}
		}.swap(a, b);
	}

	// ------------------------------------------------------

	@Override
	protected List<? extends ResourceNode> getList() {
		return perceptions;
	}

}
