/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.navigation;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;

import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.webck.components.CKComponent;

/**
 * <p>
 *  A common navigation bar.
 * </p>
 *
 * <p>
 * 	Created May 19, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
@SuppressWarnings("serial")
public class NavigationBar extends CKComponent implements NavigationNode {

	private static final Behavior CSS_CLASS_EVEN =  new AttributeAppender("class", Model.of("even"), " ");
	private static final Behavior CSS_CLASS_ODD =  new AttributeAppender("class", Model.of("odd"), " ");
	private final List<NavigationNode> children = new ArrayList<NavigationNode>();

	// -----------------------------------------------------

	/**
	 * @param id The component ID.
	 */
	public NavigationBar(final String id) {
		super(id);
		buildComponent();

	}

	// -----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isExpandable() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isExpanded() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isActive() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasChildren() {
		return !children.isEmpty();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<NavigationNode> getChildren() {
		return children;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Component getComponent() {
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public NavigationNode addChild(final NavigationNode node) {
		this.children.add(node);
		return this;
	}

	@Override
	protected void initComponent(final CKValueWrapperModel model) {

		final ListView<NavigationNode> itemView = new ListView<NavigationNode>("nodeList", children) {
			private boolean isEven = false;
			@Override
			protected void populateItem(final ListItem<NavigationNode> item) {
				Component comp = item.getModelObject().getComponent();
				((CKComponent) comp).buildComponent();
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

	@Override
	public ServiceProvider getServiceProvider() {
		// TODO Auto-generated method stub
		return null;
	}

}
