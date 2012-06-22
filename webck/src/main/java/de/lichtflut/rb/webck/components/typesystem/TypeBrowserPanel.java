/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.typesystem;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.ResourceModel;
import org.arastreju.sge.model.nodes.views.SNClass;

import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import de.lichtflut.rb.webck.models.basic.LoadableModel;

/**
 * <p>
 * Browser panel for rb:Types.
 * </p>
 * 
 * <p>
 * Created Sep 22, 2011
 * </p>
 * 
 * @author Oliver Tigges
 */
public abstract class TypeBrowserPanel extends Panel {

	private final LoadableModel<List<SNClass>> model;

	// ---------------- Constructor -------------------------

	/**
	 * Constructor.
	 */
	@SuppressWarnings("rawtypes")
	public TypeBrowserPanel(final String id, final LoadableModel<List<SNClass>> model) {
		super(id);
		this.model = model;

		setOutputMarkupId(true);

		add(new ListView<SNClass>("listview", model) {
			@Override
			protected void populateItem(final ListItem<SNClass> item) {
				final SNClass type = item.getModelObject();
				final Link link = new AjaxFallbackLink("link") {
					@Override
					public void onClick(AjaxRequestTarget target) {
						onTypeSelected(type, target);
					}
				};
				item.add(link);
				link.add(new Label("type", type.getQualifiedName().getSimpleName()));
				link.add(new AttributeAppender("title", type.getQualifiedName().toURI()));
			}
		});

		addLink();
	}

	// -- CALLBACKS ---------------------------------------

	public abstract void onTypeSelected(SNClass type, AjaxRequestTarget target);

	// ----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onEvent(final IEvent<?> event) {
		final ModelChangeEvent<?> mce = ModelChangeEvent.from(event);
		if (mce.isAbout(ModelChangeEvent.TYPE)) {
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

	protected void onCreate(AjaxRequestTarget target) {

	}
	
	/**
	 * Sets whether the create {@link Link} is visible or not.
	 * @return
	 */
	protected boolean isCreateLinkVisible(){
		return true;
	}

	// ------------------------------------------------------

	private void addLink() {
		@SuppressWarnings("rawtypes")
		AjaxLink link = new AjaxLink("create") {

			@Override
			public void onClick(AjaxRequestTarget target) {
				onCreate(target);
			}
		};
		link.add(new Label("label", new ResourceModel("link-create")));
		link.setVisible(isCreateLinkVisible());
		add(link);
	}

}
