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

import de.lichtflut.rb.core.services.SchemaManager;
import de.lichtflut.rb.core.services.SemanticNetworkService;
import de.lichtflut.rb.core.services.TypeManager;
import de.lichtflut.rb.webck.behaviors.ConditionalBehavior;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.common.PanelTitle;
import de.lichtflut.rb.webck.models.ConditionalModel;
import de.lichtflut.rb.webck.models.resources.ResourceLabelModel;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;
import org.arastreju.sge.model.nodes.SemanticNode;
import org.arastreju.sge.model.nodes.views.SNClass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * This Panel offers some predefined SoftwareItems to choose from.
 * </p>
 * Created: Jan 28, 2013
 * 
 * @author Ravi Knox
 */
public class CatalogPanel extends Panel {

	@SpringBean
	private TypeManager typeManager;

	@SpringBean
	private SemanticNetworkService networkService;

	@SpringBean
	private SchemaManager schemaManager;

	private final IModel<List<ResourceNode>> root = new ListModel<ResourceNode>(new LinkedList<ResourceNode>());

	// ---------------- Constructor -------------------------

	/**
	 * Constructor.
	 * 
	 * @param id Component id
	 * @param type Superclass of all catalog items
	 */
	public CatalogPanel(final String id, final IModel<ResourceID> type) {
		super(id);

		addCategoriesPanel("categories",type);
		addSpecifyingList("specifyingList", type);
		addSearchbox("searchbox",type);

		setOutputMarkupId(true);
	}

	// ------------------------------------------------------

	/**
	 * @param base Base class
	 * @return a IModel containing all subclasses for a given type
	 */
	protected IModel<? extends List<ResourceNode>> getAllSubClassesFor(final IModel<ResourceID> base) {
		return new LoadableDetachableModel<List<ResourceNode>>() {
			@Override
			protected List<ResourceNode> load() {
				List<ResourceNode> list = new LinkedList<ResourceNode>();
				Set<SNClass> categories = typeManager.getSubClasses(base.getObject());
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
		return new Comparator<ResourceNode>() {
			@Override
			public int compare(final ResourceNode o1, final ResourceNode o2) {
				return o1.getQualifiedName().toURI().compareTo(o2.getQualifiedName().toURI());
			}
		};
	}

	/**
	 * Triggered when user cancels the creation of an entity.
	 */
	protected void onCancel(final AjaxRequestTarget target, final Form<?> form) {
	}

	/**
	 * Specify behavior when 'create'-link is clicked.
	 * @param model IModel containing the selected class
	 */
	protected void applyAction(final IModel<ResourceID> model) {
	}

	// ------------------------------------------------------

	private void addCategoriesPanel(final String id, final IModel<ResourceID> type) {
		Component panel = new CategoriesPanel(id, type){
			@Override
			protected IModel<? extends List<ResourceNode>> getAllSubClassesFor(final IModel<ResourceID> base) {
				return CatalogPanel.this.getAllSubClassesFor(base);
			}

			@Override
			Comparator<ResourceNode> getNodeComparator() {
				return CatalogPanel.this.getNodeComparator();
			}

			@Override
			protected void applyActions(final ListItem<ResourceNode> item, final AjaxRequestTarget target) {
				root.getObject().clear();
				root.getObject().add(item.getModelObject());
				RBAjaxTarget.add(CatalogPanel.this);
			}
		};
		add(panel);
	}

	private void addSpecifyingList(final String id, final IModel<ResourceID> type) {
		ListView<ResourceID> list = new ListView<ResourceID>(id, root) {
			@Override
			protected void populateItem(final ListItem<ResourceID> item) {
				item.add(new Label("itemListTitle", new ResourceLabelModel(item.getModelObject())));
				item.add(new AjaxLink<Void>("createLink"){
					@Override
					public void onClick(final AjaxRequestTarget target) {
						applyAction(item.getModel());
					}
				});
				item.add(new CatalogItemInfoPanel("info", new Model<ResourceNode>(item.getModelObject().asResource())));
				item.add(createSubListForType("subList", item.getModel()));
			}
		};
		list.add(ConditionalBehavior.visibleIf(ConditionalModel.isNotEmpty(root)));
		add(list);
	}

	private Component createSubListForType(final String id, final IModel<ResourceID> model) {
		// get all subclasses type model and list 'em up
		IModel<? extends List<ResourceNode>> subClasses = getAllSubClassesFor(model);
		ListView<ResourceNode> subList = new ListView<ResourceNode>(id,subClasses) {
			@Override
			protected void populateItem(final ListItem<ResourceNode> item) {
				AjaxLink<?> link = new AjaxLink<Void>("subLink") {
					@Override
					public void onClick(final AjaxRequestTarget target) {
						if(addToList(item, model)){
							RBAjaxTarget.add(CatalogPanel.this);
						}
					}
				};
				link.add(new Label("subLabel", new ResourceLabelModel(item.getModel())));
				item.add(link);
			}
		};
		return subList;
	}

	private void addSearchbox(final String string, final IModel<ResourceID> type) {
		add(new PanelTitle("searchbox-title", new ResourceModel("title.searchbox")));

		Form<?> form = new Form<Void>("form");
		final Model<ResourceID> model = new Model<ResourceID>();
		// TODO switch to SNClassPicker when inferencing of RDF:Subclass is activated in arastreju
		form.add(getPicker(type, model));
		form.add(new AjaxButton("create") {
			@Override
			protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
				if(null != model.getObject()) {
					CatalogPanel.this.applyAction(model);
				}
			}
		});
		add(form);
	}

	private Component getPicker(final IModel<ResourceID> type, final Model<ResourceID> model) {
		IChoiceRenderer<ResourceID> renderer = new IChoiceRenderer<ResourceID>(){
			@Override
			public Object getDisplayValue(final ResourceID object) {
				return new ResourceLabelModel(object).getObject();
			}

			@Override
			public String getIdValue(final ResourceID object, final int index) {
				return String.valueOf(index);
			}
		};
        // findSubclassesFor(type), renderer
		TextField<ResourceID> picker = new TextField<ResourceID>("searchbox", model);
		return picker;
	}

	private IModel<List<ResourceID>> findSubclassesFor(final IModel<ResourceID> type) {
		return new LoadableDetachableModel<List<ResourceID>>() {
			@Override
			protected List<ResourceID> load() {
				List<ResourceID> list = new ArrayList<ResourceID>();
				findSubclasses(type.getObject(), list);
				return list;
			}

			private void findSubclasses(final ResourceID type, final List<ResourceID> list){
				for (SNClass snClass : typeManager.getSubClasses(type)) {
					list.add(snClass);
					if (0 < typeManager.getSubClasses(snClass).size()) {
						findSubclasses(snClass, list);
					}
				}
			}
		};
	}

	private boolean addToList(final ListItem<ResourceNode> item, final IModel<ResourceID> model) {
		boolean success = false;
		int index = root.getObject().indexOf(model.getObject());
		while(root.getObject().size() > index+1){
			root.getObject().remove(index+1);
		}
		if(!root.getObject().contains(item.getModelObject())){
			root.getObject().add(item.getModelObject());
			success = true;
		}
		return success;
	}

}
