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

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.RDFS;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;

import de.lichtflut.rb.core.perceptions.Perception;

import static org.arastreju.sge.SNOPS.singleObject;
import static org.arastreju.sge.SNOPS.string;

/**
 * <p>
 *  List model for Perceptions wizard.
 * </p>
 *
 * Created: Jan 11, 2013
 *
 * @author Ravi Knox
 */
public class PerceptionWizardListModel extends LoadableDetachableModel<List<List<Perception>>> {

	private final IModel<List<ResourceNode>> categories;

	private List<List<Perception>> perceptionsList;

	// ---------------- Constructor -------------------------

	/**
	 * Constructor.
	 * @param categories Specifies the types of perceptions
	 */
	public PerceptionWizardListModel(final IModel<List<ResourceNode>> categories){
		this.categories = categories;
	}

	// ------------------------------------------------------

	@Override
	public List<List<Perception>> load() {
		if(null == perceptionsList){
			perceptionsList = new ArrayList<List<Perception>>();
			for (ResourceNode category : categories.getObject()) {
				ArrayList<Perception> perceptions = new ArrayList<Perception>();
				perceptions.add(createPerceptionFor(category));
				perceptionsList.add(perceptions);
			}}
		return perceptionsList;
	}

	public Perception createPerceptionFor(ResourceNode category) {
		Perception perception = new Perception();
		perception.setCategory(category);
		perception.setColor("ffffff");
		perception.setID(getSubstring(category, 3));
		perception.setName(getLabel(category));
		return perception;
	}

	public void addPerceptionFor(final ResourceNode category){
		List<Perception> list = perceptionsList.get(categories.getObject().indexOf(category));
		list.add(createPerceptionFor(category));
	}

	// ------------------------------------------------------

	private String getSubstring(final ResourceNode category, final int length) {
		if(null == category){
			return "";
		}
		return getLabel(category).substring(0,4).toUpperCase();
	}

    private String getLabel(final ResourceNode category) {
        return string(singleObject(category, RDFS.LABEL));
    }

}
