/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.entity.quickinfo;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;

import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.services.SemanticNetworkService;
import de.lichtflut.rb.webck.components.entity.EntityRowDisplayPanel;
import de.lichtflut.rb.webck.models.ConditionalModel;
import de.lichtflut.rb.webck.models.fields.RBFieldValueModel;

/**
 * <p>
 * This panel provides a {@link RBEntity}s quick-info.
 * </p>
 * Created: Feb 27, 2013
 *
 * @author Ravi Knox
 */
public class QuickInfoPanel extends Panel{

	@SpringBean
	private SemanticNetworkService networkService;

	// ---------------- Constructor -------------------------

	/**
	 * Constructor.
	 * 
	 * @param id The component id
	 * @param model Contains the entity's resourceId
	 */
	public QuickInfoPanel(final String id, final IModel<List<RBField>> fields) {
		super(id, fields);
		WebMarkupContainer container = new WebMarkupContainer("container");

		Label label = new Label("noInfo", new ResourceModel("label.no-info"));
		label.add(visibleIf(ConditionalModel.isEmpty(fields)));
		container.add(label);

		ListView<RBField> view = createListView(fields);
		view.add(visibleIf(ConditionalModel.isNotEmpty(fields)));
		container.add(view);

		add(container);
	}

	// ------------------------------------------------------

	protected ListView<RBField> createListView(final IModel<List<RBField>> fields) {
		ListView<RBField> view = new ListView<RBField>("info", fields) {
			@Override
			protected void populateItem(final ListItem<RBField> item) {
				EntityRowDisplayPanel panel = createEntityRowDisplayPanel(item);
				item.add(panel);
			}

			protected EntityRowDisplayPanel createEntityRowDisplayPanel(final ListItem<RBField> item) {
				EntityRowDisplayPanel panel = new EntityRowDisplayPanel("row", item.getModel()){
					@Override
					protected Component addValueField(final ListItem<RBFieldValueModel> value, final Datatype dataType) {
						if(Datatype.RESOURCE.name().equals(dataType.name())){
							ResourceNode resolved = networkService.resolve((ResourceID) value.getModelObject().getFieldValue().getValue());
							value.getModelObject().setObject(resolved);
						}
						return super.addValueField(value, dataType);
					}
				};
				return panel;
			}
		};
		return view;
	}

}

