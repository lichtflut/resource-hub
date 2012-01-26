/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.websample.base;

import org.apache.commons.lang3.Validate;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.odlabs.wiquery.core.resources.CoreJavaScriptResourceReference;
import org.odlabs.wiquery.ui.dialog.Dialog;

import de.lichtflut.rb.webck.components.common.DialogHoster;
import de.lichtflut.rb.webck.components.listview.ReferenceLink;
import de.lichtflut.rb.webck.components.navigation.NavigationBar;
import de.lichtflut.rb.webck.components.navigation.NavigationNode;
import de.lichtflut.rb.webck.components.navigation.NavigationNodePanel;
import de.lichtflut.rb.websample.DashboardPage;
import de.lichtflut.rb.websample.components.ComponentsCatalogPage;
import de.lichtflut.rb.websample.entities.EntityOverviewPage;
import de.lichtflut.rb.websample.infomanagement.InformationManagementPage;
import de.lichtflut.rb.websample.types.TypeSystemPage;

/**
 * <p>
 * 	Base page for WebSample pages.
 * </p>
 *
 * <p>
 * 	Created May 09, 2011
 * </p>
 *
 * @author Nils Bleisch
 */
@SuppressWarnings("serial")
public abstract class RBBasePage extends WebPage implements DialogHoster {
	
	private static final String MODALDIALOG = "modaldialog";

	private String title;

	// -----------------------------------------------------

	/**
	 * Default constructor.
	 * @param title /
	 */
	public RBBasePage(final String title){
		super();
		this.title = title;
		init();
	}
	
	/**
	 * Takes PageParamertes as argument.
	 * @param title /
	 * @param params /
	 */
	public RBBasePage(final String title, final PageParameters params){
		super(params);
		this.title = title;
		init();
	}

	// -----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void renderHead(final IHeaderResponse response) {
		super.renderHead(response);
		response.renderJavaScriptReference(CoreJavaScriptResourceReference.get());
	}

	// -----------------------------------------------------


	/**
	 * Initializer.
	 */
	public void init(){
		Label titleLabel = new Label("title", title);
		titleLabel.setRenderBodyOnly(true);
		add(titleLabel);

		final NavigationBar mainNavigation = new NavigationBar("mainNavigation");
		
		mainNavigation.addChild(createNavigationNode(Model.of("Home"), DashboardPage.class));
		// Entity Pages
		mainNavigation.addChild(createNavigationNode(Model.of("Entities"), EntityOverviewPage.class));
		
		// Type System
		mainNavigation.addChild(createNavigationNode(Model.of("Type-System"), TypeSystemPage.class));
		
		// Information Management 
		mainNavigation.addChild(createNavigationNode(Model.of("Info-Mgmt"), InformationManagementPage.class));

		// Components Catalog
		mainNavigation.addChild(createNavigationNode(Model.of("Catalog"), ComponentsCatalogPage.class));
		
		add(mainNavigation);

		add(createSideBar("sidebarLeft"));
		
		add(emptyDialog());
		
	}
	
	protected Component createSideBar(final String id) {
		return new WebMarkupContainer(id);
	}
	
	private NavigationNode createNavigationNode(IModel<String> label, Class<? extends Page> pageClass) {
		return new NavigationNodePanel(new ReferenceLink("link", pageClass,label));
	}
	
	// -- DIALOG ------------------------------------------

	/**
	 * @return the ID to assign to dialog.
	 */
	@Override
	public String getDialogID() {
		return MODALDIALOG;
	}
	
	@Override
	public void openDialog(Dialog dialog) {
		setDialog(dialog);
		final AjaxRequestTarget target = AjaxRequestTarget.get();
		if (target != null) {
			dialog.open(target);
			target.add(dialog);
		} else {
			dialog.open();
		}
	}
	
	@Override
	public void closeDialog(Dialog dialog) {
		setDialog(emptyDialog());
		final AjaxRequestTarget target = AjaxRequestTarget.get();
		if (target != null) {
			dialog.close(target);
			target.add(dialog);
		} else {
			dialog.close();
		}
	}
	
	protected void setDialog(Component dialog) {
		Validate.isTrue(MODALDIALOG.equals(dialog.getId()));
		replace(dialog);
	}
	
	private Component emptyDialog() {
		return new WebMarkupContainer(MODALDIALOG).setOutputMarkupPlaceholderTag(true).setVisible(false);
	}
	

}
