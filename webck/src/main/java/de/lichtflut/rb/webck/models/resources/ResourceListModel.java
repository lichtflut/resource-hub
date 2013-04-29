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
package de.lichtflut.rb.webck.models.resources;

import de.lichtflut.rb.webck.models.basic.AbstractLoadableDetachableModel;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.Conversation;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.apriori.RDF;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.query.Query;
import org.arastreju.sge.query.SortCriteria;

import java.util.List;

/**
 * <p>
 *  Model of a list of Resource Nodes.
 * </p>
 *
 * <p>
 * 	Created Nov 15, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class ResourceListModel extends AbstractLoadableDetachableModel<List<ResourceNode>> {

	@SpringBean
	private Conversation conversation;
	
	private final ResourceID type;
	
	private final String[] sortColumns;
	
	// -----------------------------------------------------
	
	/**
	 * @param type The type of the resources to load.
	 */
	public ResourceListModel(final ResourceID type, final String... sortColumns) {
		this.type = type;
		this.sortColumns = sortColumns;
		Injector.get().inject(this);
	}

	// -----------------------------------------------------
	
	public List<ResourceNode> load() {
		final Query query = conversation.createQuery();
		query.addField(RDF.TYPE, SNOPS.uri(type));
		if (sortColumns != null && sortColumns.length > 0) {
			query.setSortCriteria(new SortCriteria(sortColumns));
		}
		return query.getResult().toList(500);
	}
	
}
