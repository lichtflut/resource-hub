/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.editor;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxFallbackButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import de.lichtflut.rb.core.api.EntityManager;
import de.lichtflut.rb.core.entity.RBEntity;
import de.lichtflut.rb.core.entity.RBField;
import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.*;
import de.lichtflut.rb.webck.application.LinkProvider;
import de.lichtflut.rb.webck.components.ResourceInfoPanel;
import static de.lichtflut.rb.webck.models.ConditionalModel.*;
import de.lichtflut.rb.webck.models.RBFieldsListModel;

/**
 * <p>
 *  Panel for displaying and edition of an {@link RBEntity}.
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
	
	private IModel<Boolean> readonly;
	
	// ----------------------------------------------------

	/**
	 * Constructor.
	 * @param id The ID.
	 * @param model The model.
	 * @param readonly Specify if panel is readonly.
	 */
	public EntityPanel(final String id, final IModel<RBEntity> model, final IModel<Boolean> readonly) {
		super(id, model);
		this.readonly = readonly;
		
		final Form form = new Form("form");
		form.setOutputMarkupId(true);
		
		form.add(new ResourceInfoPanel("infoPanel", model));
		form.add(new FeedbackPanel("feedbackPanel"));
		
		form.add(createRows(new RBFieldsListModel(model)));
		
		form.add(createSaveButton(model, form));
		form.add(createCancelButton(model, form));
		form.add(createEditButton(model, form));
		
		add(form);
	}
	
	// ----------------------------------------------------
	
	public EntityPanel setReadonly(final boolean readonly) {
		this.readonly.setObject(readonly);
		return this;
	}
	
	public boolean isReadonly() {
		return readonly.getObject();
	}
	
	// ----------------------------------------------------
	
	public abstract EntityManager getEntityManager();
	
	public abstract LinkProvider getLinkProvider();

	// ----------------------------------------------------
	
	protected ListView<RBField> createRows(final IModel<List<RBField>> listModel) {
		final ListView<RBField> view = new ListView<RBField>("rows", listModel) {
			@Override
			protected void populateItem(ListItem<RBField> item) {
				if (isReadonly()) {
					item.add(new EntityRowDisplayPanel("row", item.getModel()) {
						@Override
						public LinkProvider getLinkProvider() {
							return EntityPanel.this.getLinkProvider();
						}
					});
				} else {
					item.add(new EntityRowEditPanel("row", item.getModel()) {
						@Override
						public LinkProvider getLinkProvider() {
							return EntityPanel.this.getLinkProvider();
						}
					});
				}
			}
		};
		return view;
	}
	
	protected AjaxFallbackButton createSaveButton(final IModel<RBEntity> model, final Form form) {
		final AjaxFallbackButton save = new AjaxFallbackButton("save", form) {
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				getEntityManager().store(model.getObject());
				setReadonly(true);
				target.add(form);
			}
			
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(form);
			}
		};
		save.add(visibleIf(isFalse(readonly)));
		return save;
	}
	
	protected AjaxFallbackButton createCancelButton(final IModel<RBEntity> model, final Form form) {
		final AjaxFallbackButton cancel = new AjaxFallbackButton("cancel", form) {
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				setReadonly(true);
				target.add(form);
			}
			
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(form);
			}
		};
		cancel.setDefaultFormProcessing(false);
		cancel.add(visibleIf(isFalse(readonly)));
		return cancel;
	}
	
	protected AjaxFallbackButton createEditButton(final IModel<RBEntity> model, final Form form) {
		final AjaxFallbackButton edit = new AjaxFallbackButton("edit", form) {
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				setReadonly(false);
				target.add(form);
			}
			
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(form);
			}
		};
		edit.add(visibleIf(isTrue(readonly)));
		return edit;
	}
	
}
