/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxFallbackButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;

import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.RBField;

/**
 * <p>
 *  [DESCRIPTION]
 * </p>
 *
 * <p>
 * 	Created Nov 15, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
@SuppressWarnings("rawtypes")
public abstract class EntityPanel extends Panel {

	/**
	 * @param id
	 * @param model
	 */
	public EntityPanel(final String id, final IModel<RBEntity> model) {
		super(id, model);
		
		final Form form = new Form("form");
		form.setOutputMarkupId(true);
		
		form.add(new ResourceInfoPanel("infoPanel", model));
		form.add(new FeedbackPanel("feedbackPanel"));
		
		final IModel<List<RBField>> listModel = new AbstractReadOnlyModel<List<RBField>>() {
			@Override
			public List<RBField> getObject() {
				return model.getObject().getAllFields();
			}
		};
		
		final ListView<RBField> view = new ListView<RBField>("rows", listModel) {
			@Override
			protected void populateItem(ListItem<RBField> item) {
				item.add(new EntityFieldRowPanel("row", item.getModel()));
			}
		};
		form.add(view);
		
		form.add(new AjaxFallbackButton("save", form) {
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				onSave();
				target.add(form);
			}
			
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(form);
			}
		});
		
		add(form);
	}
	
	public abstract void onSave();

}
