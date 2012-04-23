/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.typesystem.TypeDefinitions;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;

import de.lichtflut.rb.core.schema.model.TypeDefinition;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import de.lichtflut.rb.webck.models.basic.LoadableModel;

/**
 * <p>
 *  Panel for Browsing of {@link TypeDefinition}s.
 * </p>
 *
 * <p>
 * 	Created Oct 20, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public abstract class TypeDefBrowserPanel extends Panel {

	private final LoadableModel<List<TypeDefinition>> model;
	
	// ----------------------------------------------------

	/**
	 * Constructor.
	 * @param id
	 */
	@SuppressWarnings("rawtypes")
	public TypeDefBrowserPanel(final String id, final LoadableModel<List<TypeDefinition>> model) {
		super(id);
		this.model = model;
		
		setOutputMarkupId(true);
		
		add(new ListView<TypeDefinition>("listview", model) {
			@Override
			protected void populateItem(final ListItem<TypeDefinition> item) {
				final TypeDefinition typeDef = item.getModelObject();
				final Link link = new AjaxFallbackLink("link") {
						@Override
						public void onClick(AjaxRequestTarget target) {
							onTypeDefSelected(typeDef, target);
						}	
				};
				item.add(link);
				link.add(new Label("typeDef", typeDef.getName()));
				link.add(new AttributeAppender("title", typeDef.getID().getQualifiedName()));
			}
		});
		
		add(new AjaxFallbackLink("createTypeDef") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				target.add(TypeDefBrowserPanel.this);
				onCreateTypeDef(target);
			}
		});
	}
	
	// ----------------------------------------------------
	
	/** 
	* {@inheritDoc}
	*/
	@Override
	public void onEvent(final IEvent<?> event) {
		final ModelChangeEvent<?> mce = ModelChangeEvent.from(event);
		if (mce.isAbout(ModelChangeEvent.PUBLIC_TYPE_DEFINITION)) {
			model.reset();
			RBAjaxTarget.add(this);
		}
	}
	
	/** 
	* {@inheritDoc}
	*/
	@Override
	protected void onDetach() {
		super.onDetach();
		model.detach();
	}
	
	// -- CALLBACKS ---------------------------------------
	
	public abstract void onCreateTypeDef(AjaxRequestTarget target);
	
	public abstract void onTypeDefSelected(TypeDefinition typeDef, AjaxRequestTarget target);
	
}
