/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.application.base;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.lichtflut.rb.application.RBApplication;
import de.lichtflut.rb.application.admin.AdminBasePage;
import de.lichtflut.rb.application.common.RBPermission;
import de.lichtflut.rb.application.custom.BrowseAndSearchPage;
import de.lichtflut.rb.application.pages.AbstractBasePage;
import de.lichtflut.rb.core.viewspec.MenuItem;
import de.lichtflut.rb.webck.behaviors.SubmitFormOnEnterBehavior;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.organizer.domains.DomainSwitcherPanel;
import de.lichtflut.rb.webck.components.fields.SearchField;
import de.lichtflut.rb.webck.components.identities.SessionInfoPanel;
import de.lichtflut.rb.webck.components.listview.ReferenceLink;
import de.lichtflut.rb.webck.components.navigation.NavigationBar;
import de.lichtflut.rb.webck.components.navigation.NavigationNode;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import de.lichtflut.rb.webck.models.CurrentUserModel;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;
import de.lichtflut.rb.webck.models.domains.CurrentDomainModel;
import de.lichtflut.rb.webck.models.viewspecs.MenuItemListModel;

/**
 * <p>
 *  This page is a common  base for RB applications and may be useful for most simple applications.
 * </p>
 * 
 * <p>
 *  Created May 30, 2011
 * </p>
 * 
 * @author Oliver Tigges
 */
@SuppressWarnings("serial")
public class RBBasePage extends AbstractBasePage {

	/**
	 * Default Constructor.
	 */
	public RBBasePage() {
		this(null);
	}

	/**
	 * Constructor with parameters.
	 * @param parameters The parameters.
	 */
	public RBBasePage(final PageParameters parameters) {
		super(parameters);
	}

	// -----------------------------------------------------

	@Override
	protected void onInitialize() {
		super.onInitialize();

		final IModel<List<NavigationNode>> navModel = new DerivedDetachableModel<List<NavigationNode>, List<MenuItem>>(
				new MenuItemListModel()) {
			@Override
			protected List<NavigationNode> derive(final List<MenuItem> menuItems) {
				return RBApplication.get().getFirstLevelNavigation(menuItems);
			}
		};

		add(new NavigationBar("mainNavigation", navModel) {
			@Override
			public void onEvent(final IEvent<?> event) {
				if (ModelChangeEvent.from(event).isAbout(ModelChangeEvent.MENU)) {
					RBAjaxTarget.add(this);
				}
			}
		}.setOutputMarkupId(true));

		add(createSearchForm("inlineSearchForm"));

		add(createSecondLevelNav("secondLevelNav"));

		add(new ReferenceLink("adminAreaLink", AdminBasePage.class, new ResourceModel("global.link.admin-area"))
		.add(visibleIf(CurrentUserModel.hasPermission(RBPermission.ENTER_ADMIN_AREA.name()))));

		add(new Label("username", CurrentUserModel.displayNameModel())
		    .add(visibleIf(CurrentUserModel.isLoggedIn())));

		add(new DomainSwitcherPanel("domain", CurrentDomainModel.displayNameModel()));

		add(new SessionInfoPanel("sessionInfo"));

	}

	protected Component createSecondLevelNav(final String componentID) {
		return new WebMarkupContainer(componentID);
	}

	@SuppressWarnings("rawtypes")
	protected Form<?> createSearchForm(final String componentID) {
		final IModel<String> searchModel = new Model<String>();
		final Form<?> form = new Form(componentID) {
			@Override
			protected void onSubmit() {
				final PageParameters params = new PageParameters();
				if (searchModel.getObject() != null) {
					params.add(BrowseAndSearchPage.PARAM_TERM, searchModel.getObject());
				}
				setResponsePage(BrowseAndSearchPage.class, params);
			}
		};
		form.setOutputMarkupId(true);

		final SearchField searchField = new SearchField("search", searchModel);
		final SubmitLink submitLink = new SubmitLink("submit");

		form.add(searchField);
		form.add(submitLink);
		form.add(new SubmitFormOnEnterBehavior(submitLink));
		return form;
	}

}
