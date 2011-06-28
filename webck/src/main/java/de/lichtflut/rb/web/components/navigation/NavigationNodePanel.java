/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web.components.navigation;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;

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
	@SuppressWarnings("unused")
	private IModel<String> label;

	/**
	 * Constructor.
	 *
	 * @param id - The component ID.
	 * @param link - The link (must have component ID 'link')
	 * @param label - The label.
	 */
	public NavigationNodePanel(final String id, final CKLink link,
			final IModel<String> label) {
		super(id);
		this.link = link;
//		link.add(new Label("label", label));
		this.label = label;
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
		// throw new NotYetImplementedException();
		children.add(node);
		buildComponent();
		return node;
	}

	@Override
	protected void initComponent(final CKValueWrapperModel model) {
		add(link);

		if (children != null && children.size() > 0) {
			final NavigationBar subLevelMenu = new NavigationBar("child");
			for(NavigationNode node : children){
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
}
