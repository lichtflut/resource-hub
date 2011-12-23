/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.typesystem;

import static org.arastreju.sge.SNOPS.assure;
import static org.arastreju.sge.SNOPS.singleObject;
import static org.arastreju.sge.SNOPS.string;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
import org.arastreju.sge.ModelingConversation;
import org.arastreju.sge.SNOPS;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.views.SNProperty;
import org.arastreju.sge.model.nodes.views.SNText;

import de.lichtflut.rb.core.RB;
import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.webck.behaviors.DefaultButtonBehavior;
import de.lichtflut.rb.webck.components.form.RBCancelButton;
import de.lichtflut.rb.webck.components.form.RBStandardButton;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import de.lichtflut.rb.webck.models.basic.AbstractDerivedListModel;
import de.lichtflut.rb.webck.models.basic.AbstractLoadableDetachableModel;
import de.lichtflut.rb.webck.models.resources.ResourceLabelModel;
import de.lichtflut.rb.webck.models.resources.ResourceUriModel;

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
public abstract class SNPropertyEditorPanel extends Panel {
	
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
				return string(singleObject(model.getObject(), RB.HAS_FIELD_LABEL));
			}
		};
		
		final TextField<String> name = new TextField<String>("name", nameModel);
		form.add(name);
		
		form.add(new RBStandardButton("save") {
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
				assureLabel(model.getObject(), nameModel.getObject());
				target.add(form);
			}
		}.add(new DefaultButtonBehavior()));
		
		form.add(new RBCancelButton("cancel") {
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
				nameModel.setObject(null);
			}
		});
		
		form.add(new RBStandardButton("delete") {
			@Override
			protected void applyActions(AjaxRequestTarget target, Form<?> form) {
				final SNProperty original = model.getObject();
				getServiceProvider().getTypeManager().removeProperty(original);
				info("Property deleted.");
				SNPropertyEditorPanel.this.setVisible(false);
				target.add(SNPropertyEditorPanel.this);
				send(getPage(), Broadcast.BUBBLE, new ModelChangeEvent<Void>(ModelChangeEvent.PROPERTY));
			}
		});
		
		final ListView<SNProperty> superProps = new ListView<SNProperty>("superProps", superPropertyModel(model)) {
			@Override
			protected void populateItem(ListItem<SNProperty> item) {
				item.add(new Label("property", item.getModelObject().getQualifiedName().getSimpleName()));
			}
		};
		add(superProps);
		
		
		final ListView<SNProperty> inverseProps = new ListView<SNProperty>("inverseProps", inversePropertyModel(model)) {
			@Override
			protected void populateItem(ListItem<SNProperty> item) {
				item.add(new Label("property", item.getModelObject().getQualifiedName().getSimpleName()));
			}
		};
		add(inverseProps);
		
		add(form);
	}
	
	public abstract ServiceProvider getServiceProvider();
	
	// ----------------------------------------------------
	
	void assureLabel(final ResourceID properyID, final String label) {
		final ModelingConversation mc = newMC();
		final SNProperty property = mc.resolve(properyID).asProperty();
		if (StringUtils.isBlank(label)) {
			SNOPS.remove(property, RB.HAS_FIELD_LABEL);
			info("Label has been removed");
		} else {
			assure(property, RB.HAS_FIELD_LABEL, new SNText(label), RB.TYPE_SYSTEM_CONTEXT);
			info("Label has been saved");
		}
	}
	
	ModelingConversation newMC() {
		return getServiceProvider().getArastejuGate().startConversation();
	}
	
	// ----------------------------------------------------
	
	private IModel<List<SNProperty>> superPropertyModel(IModel<SNProperty> src) {
		return new AbstractDerivedListModel<SNProperty, SNProperty>(src) {
			@Override
			public List<SNProperty> derive(IModel<SNProperty> source) {
				final Set<SNProperty> all = source.getObject().getSuperProperties();
				all.remove(source.getObject());
				return new ArrayList<SNProperty>(all);
			}
		};
	}
	
	private IModel<List<SNProperty>> inversePropertyModel(IModel<SNProperty> src) {
		return new AbstractDerivedListModel<SNProperty, SNProperty>(src) {
			@Override
			public List<SNProperty> derive(IModel<SNProperty> source) {
				return new ArrayList<SNProperty>(source.getObject().getInverseProperties());
			}
		};
	}
	
	
}
