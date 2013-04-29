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

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.and;
import static de.lichtflut.rb.webck.models.ConditionalModel.isEmpty;
import static de.lichtflut.rb.webck.models.ConditionalModel.not;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.views.SNClass;

import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.schema.model.PropertyDeclaration;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.services.SchemaManager;
import de.lichtflut.rb.core.services.SemanticNetworkService;
import de.lichtflut.rb.core.services.TypeManager;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.common.PanelTitle;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import de.lichtflut.rb.webck.models.BrowsingContextModel;
import de.lichtflut.rb.webck.models.basic.DerivedModel;
import de.lichtflut.rb.webck.models.resources.ResourceLabelModel;

/**
 * <p>
 * This Panel displays various links that match the SChema for a given type.
 * </p>
 * Created: Feb 5, 2013
 * 
 * @author Ravi Knox
 */
public class CatalogProposalPanel extends Panel {

	@SpringBean
	private SchemaManager schemaManager;

	@SpringBean
	private TypeManager typeManager;

	@SpringBean
	private SemanticNetworkService networkService;

	private final IModel<ResourceID> type;

	// ---------------- Constructor -------------------------

	/**
	 * Constructor.
	 * 
	 * @param id Component id
	 * @param model {@link IModel} containing the type
	 */
	public CatalogProposalPanel(final String id, final IModel<ResourceID> model) {
		super(id);
		type = model;
		add(new PanelTitle("title", new ResourceModel("title")));

		addListView("proposals");

		setOutputMarkupId(true);
		setVisibility(model);

	}


	// ------------------------------------------------------

	protected void setVisibility(final IModel<ResourceID> model) {
		add(visibleIf(and(not(BrowsingContextModel.isInViewMode()), not(isEmpty(getReferencedTypes())))));
	}

	/**
	 * @return a List containing all (super)types, that a {@link PropertyDeclaration} can  reference to, in order to get a proposal entry.
	 */
	protected List<SNClass> getAcceptableSuperclasses(){
		return Collections.emptyList();
	}

	protected void applyActions(final AjaxRequestTarget target, final IModel<PropertyDeclaration> decl, final IModel<ResourceID> typeConstraint) {
	}

	/**
	 * Returns a form, that will be submitted when the user wants to create a catalog item.
	 * @return a {@link Form}
	 */
	protected Form<?> getExternalForm() {
		return null;
	}

	// ------------------------------------------------------

	private void addListView(final String id) {
		ListView<PropertyDeclaration> view = new ListView<PropertyDeclaration>(id, getReferencedTypes()) {
			@Override
			protected void populateItem(final ListItem<PropertyDeclaration> item) {
				AjaxSubmitLink link = new AjaxSubmitLink("link", getExternalForm()) {
					@Override
					protected void onSubmit(final AjaxRequestTarget target, final Form<?> form) {
						IModel<PropertyDeclaration> field = item.getModel();
						ResourceID constraint = field.getObject().getConstraint().getTypeConstraint();
						IModel<ResourceID> typeConstraint = Model.of(constraint);
						CatalogProposalPanel.this.applyActions(target, field, typeConstraint);
					}

				};
				ResourceLabelModel labelModel = new ResourceLabelModel(item.getModel().getObject().getPropertyDescriptor());
				link.add(new Label("linkLabel", new StringResourceModel("link.label", new Model<String>(), labelModel)));
				item.add(link);
			}

		};

		add(view);
	}

	private IModel<List<PropertyDeclaration>> getReferencedTypes() {
		return new DerivedModel<List<PropertyDeclaration>, ResourceSchema>(getSchemaFor(type)) {
			@Override
			protected List<PropertyDeclaration> derive(final ResourceSchema original) {
				List<PropertyDeclaration> referencedTypes = new ArrayList<PropertyDeclaration>();
				for (PropertyDeclaration decl: original.getPropertyDeclarations()) {
					if(checkForAcceptedTypeReference(decl)){
						referencedTypes.add(decl);
					}
				}
				return referencedTypes;
			}
		};
	}

	private boolean checkForAcceptedTypeReference(final PropertyDeclaration decl) {
		if(!Datatype.RESOURCE.name().equals(decl.getDatatype().name())){
			return false;
		}
		if(!decl.hasConstraint()){
			return false;
		}
		if(null == decl.getConstraint().getTypeConstraint()){
			return false;
		}
		ResourceID constraint = decl.getConstraint().getTypeConstraint();
		if(getAcceptableSuperclasses().contains(constraint)){
			return true;
		}
		Set<SNClass> superClasses = typeManager.getSuperClasses(constraint);
		superClasses.retainAll(getAcceptableSuperclasses());
		if(superClasses.size() > 0) {
			return true;
		}
		return false;
	}

	private IModel<ResourceSchema> getSchemaFor(final IModel<ResourceID> model) {
		return new DerivedModel<ResourceSchema, ResourceID>(model) {
			@Override
			protected ResourceSchema derive(final ResourceID original) {
				return schemaManager.findSchemaForType(model.getObject());
			}
		};
	}

	// ------------------------------------------------------

	@Override
	public void onEvent(final IEvent<?> event) {
		ModelChangeEvent<Object> mce = ModelChangeEvent.from(event);
		de.lichtflut.rb.webck.events.ModelChangeEvent<Object> mceRB = de.lichtflut.rb.webck.events.ModelChangeEvent.from(event);

		if(mce.isAbout(ModelChangeEvent.PROPOSAL_UPDATE)){
			type.setObject((ResourceID)mce.getPayload());
			RBAjaxTarget.add(CatalogProposalPanel.this);
		}
		else if(mceRB.isAbout(de.lichtflut.rb.webck.events.ModelChangeEvent.ENTITY)){
			RBAjaxTarget.add(CatalogProposalPanel.this);
		}
		else if(mceRB.isAbout(de.lichtflut.rb.webck.events.ModelChangeEvent.RELATIONSHIP)){
			ResourceID payload = (ResourceID)mceRB.getPayload();
			type.setObject(payload);
			RBAjaxTarget.add(CatalogProposalPanel.this);
		}
	}
}
