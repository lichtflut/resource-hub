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
package de.lichtflut.rb.webck.components.typesystem.properties;

import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.services.SemanticNetworkService;
import de.lichtflut.rb.core.services.TypeManager;
import de.lichtflut.rb.webck.behaviors.DefaultButtonBehavior;
import de.lichtflut.rb.webck.components.form.RBCancelButton;
import de.lichtflut.rb.webck.components.form.RBStandardButton;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import de.lichtflut.rb.webck.models.basic.AbstractLoadableDetachableModel;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;
import de.lichtflut.rb.webck.models.resources.ResourceLabelModel;
import de.lichtflut.rb.webck.models.resources.ResourceUriModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.views.SNProperty;
import org.arastreju.sge.model.nodes.views.SNText;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.arastreju.sge.SNOPS.assure;
import static org.arastreju.sge.SNOPS.singleObject;
import static org.arastreju.sge.SNOPS.string;

/**
 * <p>
 *  Panel for Editing of rdf:Properties.
 * </p>
 *
 * <p>
 * 	Created Dec 13, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class SNPropertyEditorPanel extends Panel {

	@SpringBean
	private TypeManager typeManager;

	@SpringBean
	private SemanticNetworkService service;

	// ----------------------------------------------------

	/**
	 *  Constructor.
	 */
	@SuppressWarnings("rawtypes")
	public SNPropertyEditorPanel(final String id, final IModel<SNProperty> model) {
		super(id);

		setOutputMarkupPlaceholderTag(true);
		setOutputMarkupId(true);

		add(new Label("propertyUri", new ResourceUriModel(model)));
		add(new Label("propertyLabel", new ResourceLabelModel(model)));

		final Form<?> form = new Form("form");
		form.setOutputMarkupId(true);
		form.add(new FeedbackPanel("feedback"));

		final IModel<String> nameModel = new AbstractLoadableDetachableModel<String>() {
			@Override
			public String load() {
				return string(singleObject(model.getObject(), RBSystem.HAS_FIELD_LABEL));
			}
		};

		final TextField<String> name = new TextField<String>("name", nameModel);
		form.add(name);

		form.add(new RBStandardButton("save") {
			@Override
			protected void applyActions(final AjaxRequestTarget target, final Form<?> form) {
				assureLabel(model.getObject(), nameModel.getObject());
				target.add(form);
			}
		}.add(new DefaultButtonBehavior()));

		form.add(new RBCancelButton("cancel") {
			@Override
			protected void applyActions(final AjaxRequestTarget target, final Form<?> form) {
				nameModel.setObject(null);
			}
		});

		form.add(new RBStandardButton("delete") {
			@Override
			protected void applyActions(final AjaxRequestTarget target, final Form<?> form) {
				final SNProperty original = model.getObject();
				typeManager.removeProperty(original);
				info("Property deleted.");
				SNPropertyEditorPanel.this.setVisible(false);
				target.add(SNPropertyEditorPanel.this);
				send(getPage(), Broadcast.BUBBLE, new ModelChangeEvent<Void>(ModelChangeEvent.PROPERTY));
			}
		});

		final ListView<SNProperty> superProps = new ListView<SNProperty>("superProps", superPropertyModel(model)) {
			@Override
			protected void populateItem(final ListItem<SNProperty> item) {
				item.add(new Label("property", item.getModelObject().getQualifiedName().getSimpleName()));
			}
		};
		add(superProps);


		final ListView<SNProperty> inverseProps = new ListView<SNProperty>("inverseProps", inversePropertyModel(model)) {
			@Override
			protected void populateItem(final ListItem<SNProperty> item) {
				item.add(new Label("property", item.getModelObject().getQualifiedName().getSimpleName()));
			}
		};
		add(inverseProps);

		add(form);
	}

	void assureLabel(final ResourceID properyID, final String label) {
		final SNProperty property = SNProperty.from(service.resolve(properyID));
		if (StringUtils.isBlank(label)) {
			SNOPS.remove(property, RBSystem.HAS_FIELD_LABEL);
			info("Label has been removed");
		} else {
			assure(property, RBSystem.HAS_FIELD_LABEL, new SNText(label));
			info("Label has been saved");
		}
	}

	// ----------------------------------------------------

	private IModel<List<SNProperty>> superPropertyModel(final IModel<SNProperty> src) {
		return new DerivedDetachableModel<List<SNProperty>, SNProperty>(src) {
			@Override
			public List<SNProperty> derive(final SNProperty source) {
				final Set<SNProperty> all = source.getSuperProperties();
				all.remove(source);
				return new ArrayList<SNProperty>(all);
			}
		};
	}

	private IModel<List<SNProperty>> inversePropertyModel(final IModel<SNProperty> src) {
		return new DerivedDetachableModel<List<SNProperty>, SNProperty>(src) {
			@Override
			public List<SNProperty> derive(final SNProperty source) {
				return new ArrayList<SNProperty>(source.getInverseProperties());
			}
		};
	}


}
