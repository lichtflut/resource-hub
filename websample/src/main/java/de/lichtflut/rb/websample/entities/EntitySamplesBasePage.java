/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.websample.entities;

import java.util.Collection;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.nodes.views.SNClass;

import de.lichtflut.rb.core.services.ServiceProvider;
import de.lichtflut.rb.webck.components.listview.ReferenceLink;
import de.lichtflut.rb.webck.components.navigation.NavigationBar;
import de.lichtflut.rb.webck.components.navigation.NavigationNode;
import de.lichtflut.rb.webck.components.navigation.NavigationNodePanel;
import de.lichtflut.rb.webck.models.resources.ResourceLabelModel;
import de.lichtflut.rb.websample.base.RBBasePage;

/**
 * <p>
 *  Base page for pages with entities.
 * </p>
 *
 * <p>
 * 	Created Sep 21, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class EntitySamplesBasePage extends RBBasePage {
	
	@SpringBean
	private ServiceProvider provider;
	
	/**
	 * Singleton pattern: There will be only one instance per runtime.
	 * @return {@link ServiceProvider}
	 */
	public ServiceProvider getServiceProvider(){
		return provider;
	}
	
	// -----------------------------------------------------

	/**
	 * @param title
	 */
	public EntitySamplesBasePage(String title) {
		super(title);
	}

	/**
	 * @param title
	 * @param params
	 */
	public EntitySamplesBasePage(String title, PageParameters params) {
		super(title, params);
	}

	// -----------------------------------------------------
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Component createSideBar(String id) {
		final NavigationBar menuLeft = new NavigationBar(id);

		final NavigationNode parent = new NavigationNodePanel(new Label("link", "Entities by Type"));
		final Collection<SNClass> types = getServiceProvider().getTypeManager().findAllTypes();
		for (SNClass type : types) {
			PageParameters param = new PageParameters();
			param.add("type", type);
			ReferenceLink link = new ReferenceLink("link", EntityOverviewPage.class, param, new ResourceLabelModel(type));
			parent.addChild(new NavigationNodePanel(link));
		}
		menuLeft.addChild(parent);
		return menuLeft;
	}
	
}
