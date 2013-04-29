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
package de.lichtflut.rb.webck.components.catalog;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.SNClass;
import org.arastreju.sge.structure.OrderBySerialNumber;

import de.lichtflut.rb.core.services.TypeManager;
import de.lichtflut.rb.webck.behaviors.ConditionalBehavior;
import de.lichtflut.rb.webck.components.common.PanelTitle;
import de.lichtflut.rb.webck.models.ConditionalModel;
import de.lichtflut.rb.webck.models.resources.ResourceLabelModel;

/**
 * <p>
 * Display all direct subclasses of a given class.
 * </p>
 * Created: Jan 31, 2013
 *
 * @author Ravi Knox
 */
public class CategoriesPanel extends Panel {

	@SpringBean
	private TypeManager typeManager;

	// ---------------- Constructor -------------------------

	/**
	 * Constructor.
	 * 
	 * @param id Componet id
	 * @param superclass The superclass for this List
	 */
	public CategoriesPanel(final String id, final IModel<ResourceID> superclass) {
		super(id);

		addCategoriesTitle("categoriesTitle", new ResourceModel("title.category"));
		createCategoriesList("categoriesList", superclass);

		addNoSubclassesInfo("noSubclasses", superclass);
	}

	// ------------------------------------------------------

	/**
	 * @param superclass Base class
	 * @return a IModel containing all subclasses for a given type
	 */
	protected IModel<? extends List<ResourceNode>> getAllSubClassesFor(final IModel<ResourceID> superclass) {
		return new LoadableDetachableModel<List<ResourceNode>>() {
			@Override
			protected List<ResourceNode> load() {
				List<ResourceNode> list = new LinkedList<ResourceNode>();
				Set<SNClass> categories = typeManager.getSubClasses(superclass.getObject());
				for (SemanticNode temp : categories) {
					list.add(SNClass.from(temp));
				}
				Collections.sort(list, getNodeComparator());
				return list;
			}
		};
	}

	/**
	 * @return a comparator for all SoftwareItems
	 */
	Comparator<ResourceNode> getNodeComparator(){
		return new OrderBySerialNumber();
	}

	protected void applyActions(final ListItem<ResourceNode> item, final AjaxRequestTarget target) {
	}

	// ------------------------------------------------------

	private void addCategoriesTitle(final String id, final IModel<String> resourceModel) {
		add(new PanelTitle(id, resourceModel));
	}

	private void createCategoriesList(final String id, final IModel<ResourceID> superclass) {
		ListView<ResourceNode> list = new ListView<ResourceNode>(id, getAllSubClassesFor(superclass)) {
			@Override
			protected void populateItem(final ListItem<ResourceNode> item) {
				ResourceNode category = item.getModelObject();
				AjaxLink<?> link = new AjaxLink<Void>("link") {
					@Override
					public void onClick(final AjaxRequestTarget target) {
						applyActions(item, target);
					}

				};
				link.add(new Label("label", new ResourceLabelModel(category)));
				item.add(link);
			}
		};
		add(list);
	}


	private void addNoSubclassesInfo(final String id, final IModel<ResourceID> superclass) {
		Label label = new Label(id, new ResourceModel("label.no-listing"));
		label.add(ConditionalBehavior.visibleIf((ConditionalModel.isEmpty(getAllSubClassesFor(superclass)))));
		add(label);
	}
}
