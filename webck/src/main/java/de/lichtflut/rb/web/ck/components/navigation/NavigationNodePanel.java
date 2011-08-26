/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.ck.components.navigation;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.Model;

import de.lichtflut.rb.core.spi.RBServiceProvider;
import de.lichtflut.rb.web.ck.components.CKComponent;
import de.lichtflut.rb.web.ck.components.CKLink;

/**
 * <p>
 * Simple NavigationNode implementations which is the 'Component' itself.
 * </p>
 *
 * <p>
 * Created May 19, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
@SuppressWarnings("serial")
public class NavigationNodePanel extends CKComponent implements NavigationNode {

	private CKLink link;
	private final List<NavigationNode> children = new ArrayList<NavigationNode>();
	private static final Behavior CSS_CLASS_EVEN =  new AttributeAppender("class", Model.of("even"), " ");
	private static final Behavior CSS_CLASS_ODD =  new AttributeAppender("class", Model.of("odd"), " ");
	// -----------------------------------------------------

	/**
	 * Constructor.
	 *
	 * @param link - The link (must have component ID 'link')
	 */
	public NavigationNodePanel(final CKLink link) {
		this("node",link);
	}

	/**
	 * This constructor is private since the wicket:id is always "node".
	 * @param id - wicket:id
	 * @param link - {@link CKLink}
	 */
	protected NavigationNodePanel(final String id, final CKLink link) {
		super(id);
		this.link = link;
		this.buildComponent();
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
	 *
	 * @return false /
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
		if (children != null && children.size() > 0){
			return true;
		}
		return false;
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
		children.add(node);
		this.buildComponent();
		return node;
	}

	/**
	 * Checks if {@link NavigationNodePanel} is even or odd numbered.
	 * @param node - {@link NavigationNodePanel}
	 * @return boolean - true if node is even, false if not
	 */
	public boolean isEven(final NavigationNode node){
		int index = children.indexOf(node);
		// Adding +1 coz indexOf starts with zero
		if(((index + 1) % 2) == 1){
			return false;
		}
			return true;

	}

	@Override
	protected void initComponent(final CKValueWrapperModel model) {
		add(link);
		if (hasChildren()) {
			final NavigationBar subLevelMenu = new NavigationBar("child");
			for(NavigationNode node : children){
				addOddOrEvenAttribute(node);
				subLevelMenu.addChild(node);
			}
			subLevelMenu.setRenderBodyOnly(true);
			this.add(subLevelMenu);
		} else {
			final WebMarkupContainer childContainer = new WebMarkupContainer(
					"child");
			childContainer.setRenderBodyOnly(true);
			this.add(childContainer);
		}
		add(new WebMarkupContainer("ck-header"));
		add(new WebMarkupContainer("ck-footer"));
	}

	@Override
	public RBServiceProvider getServiceProvider() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CKComponent setViewMode(final ViewMode mode) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Checks whether node is odd or even and sets the "class" attribute accordingly.
	 * @param node - {@link NavigationNode}
	 * @return {@link NavigationNode}
	 */
	private NavigationNode addOddOrEvenAttribute(final NavigationNode node){
		List<? extends Behavior> behaviors = node.getComponent().getBehaviors();
		// Check if behavior already extists. If yes, throw it..
		for (Behavior behavior : behaviors) {
			if(behavior.equals(CSS_CLASS_EVEN)){
				node.getComponent().remove(CSS_CLASS_EVEN);
			}else if(behavior.equals(CSS_CLASS_ODD)){
				node.getComponent().remove(CSS_CLASS_ODD);
			}
		}
		// Add behavior
		if(isEven(node)){
			node.getComponent().add(CSS_CLASS_EVEN);
		}else if(!isEven(node)){
			node.getComponent().add(CSS_CLASS_ODD);
		}
		return node;
	}
}
