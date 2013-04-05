/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.entity.quickinfo;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;

import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.RBField;
import de.lichtflut.rb.core.schema.model.Datatype;
import de.lichtflut.rb.core.services.SemanticNetworkService;
import de.lichtflut.rb.webck.components.entity.EntityRowDisplayPanel;
import de.lichtflut.rb.webck.models.fields.RBFieldValueModel;

/**
 * <p>
 * This Panel displays a {@link RBEntity}s' quick-info.
 * </p>
 * Created: Sep 24, 2012
 *
 * @author Ravi Knox
 */
public class QuickInfoPopUpPanel extends Panel {

	@SpringBean
	private SemanticNetworkService networkService;

	// ---------------- Constructor -------------------------

	/**
	 * @param id - wicket:id
	 * @param model - {@link IModel} for {@link RBEntity}
	 */
	public QuickInfoPopUpPanel(final String id, final IModel<List<RBField>> fields, final IModel<String> title) {
		super(id, fields);
		createTitle(title);
		createListView(fields);
	}

	// ------------------------------------------------------

	/**
	 * Add the entities' title to the panel.
	 * @param title
	 */
	private void createTitle(final IModel<String> title) {
		add(new Label("label", title));
	}

	protected void createListView(final IModel<List<RBField>> fields) {
		ListView<RBField> view = new ListView<RBField>("quickInfo", fields) {
			@Override
			protected void populateItem(final ListItem<RBField> item) {
				EntityRowDisplayPanel panel = createEntityRowDisplayPanel(item);
				item.add(panel);
			}

			protected EntityRowDisplayPanel createEntityRowDisplayPanel(final ListItem<RBField> item) {
				EntityRowDisplayPanel panel = new EntityRowDisplayPanel("rbField", item.getModel()){
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
		add(view);
	}

}
