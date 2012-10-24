/*
 * Copyright (C) 2012 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.navigation;

import de.lichtflut.rb.webck.components.common.TypedPanel;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  A common navigation bar. The bar is itself a node too and can be added as sub menu to another menu.
 * </p>
 *
 * <p>
 * 	Created May 19, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
@SuppressWarnings("serial")
public class NavigationBar extends TypedPanel<List<NavigationNode>> implements NavigationNode {

	private static final Behavior CSS_CLASS_EVEN =  new AttributeAppender("class", Model.of("even"), " ");
	private static final Behavior CSS_CLASS_ODD =  new AttributeAppender("class", Model.of("odd"), " ");
	
	// -----------------------------------------------------

	/**
	 * @param id The component ID.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public NavigationBar(final String id) {
		this(id, new Model(new ArrayList<NavigationNode>()));
	}

	/**
	 * @param id The component ID.
	 * @param model The model of the nodes.
	 */
	public NavigationBar(final String id, final IModel<List<NavigationNode>> model) {
		super(id, model);
		
		final ListView<NavigationNode> itemView = new ListView<NavigationNode>("nodeList", model) {
			private boolean isEven = false;
			@Override
			protected void populateItem(final ListItem<NavigationNode> item) {
				final Component comp = item.getModelObject().getComponent();
				if(isEven){
					comp.add(CSS_CLASS_EVEN);
					isEven = false;
				}else{
					comp.add(CSS_CLASS_ODD);
					isEven = true;
				}
				item.add(comp);
			}
		};
		
		add(itemView);
	}
	
	// -----------------------------------------------------

	@Override
	public boolean isExpandable() {
		return false;
	}

	@Override
	public boolean isExpanded() {
		return false;
	}

	@Override
	public boolean isActive() {
		return false;
	}

	@Override
	public boolean hasChildren() {
		return !getChildren().isEmpty();
	}

	@Override
	public List<NavigationNode> getChildren() {
		return getModelObject();
	}

	@Override
	public NavigationNode addChild(final NavigationNode node) {
		this.getChildren().add(node);
		return this;
	}
	
	// ----------------------------------------------------
	
	@Override
	public Component getComponent() {
		return this;
	}

}
