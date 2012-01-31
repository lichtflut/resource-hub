/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.widgets;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.isTrue;
import static de.lichtflut.rb.webck.models.ConditionalModel.not;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import de.lichtflut.rb.core.viewspec.Perspective;
import de.lichtflut.rb.core.viewspec.ViewPort;
import de.lichtflut.rb.webck.models.ConditionalModel;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;

/**
 * <p>
 *  Panel displaying widgets based on a perspective specification.
 * </p>
 *
 * <p>
 * 	Created Jan 20, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class PerspectivePanel extends Panel {

	private IModel<Boolean> isConfigMode = new Model<Boolean>(false);

	// ----------------------------------------------------
	
	/**
	 * @param id
	 */
	@SuppressWarnings("rawtypes")
	public PerspectivePanel(String id, IModel<Perspective> spec) {
		super(id);
		
		setOutputMarkupId(true);
		
		final ConditionalModel<Boolean> isConfigConditional = isTrue(isConfigMode);
		
		add(new AjaxLink("configureLink") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				isConfigMode.setObject(true);
				target.add(PerspectivePanel.this);
			}
		}.add(visibleIf(not(isConfigConditional))));
		
		add(new AjaxLink("configDoneLink") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				isConfigMode.setObject(false);
				target.add(PerspectivePanel.this);
			}
		}.add(visibleIf(isConfigConditional)));
		

		add(new ListView<ViewPort>("portList", new PortListModel(spec)) {
			@Override
			protected void populateItem(ListItem<ViewPort> item) {
				item.add(new ViewPortPanel("port", item.getModel(), isConfigConditional));
			}
		});
		
	}
	
	// ----------------------------------------------------
	
	class PortListModel extends DerivedDetachableModel<List<ViewPort>, Perspective> {

		/**
		 * @param original The original model.
		 */
		public PortListModel(IModel<Perspective> original) {
			super(original);
		}

		/** 
		 * {@inheritDoc}
		 */
		@Override
		protected List<ViewPort> derive(Perspective original) {
			return original.getViewPorts();
		}
		
	}
	
}
