/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.application.base;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
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
import de.lichtflut.rb.application.custom.PerspectivePage;
import de.lichtflut.rb.application.pages.AbstractBasePage;
import de.lichtflut.rb.core.viewspec.MenuItem;
import de.lichtflut.rb.webck.behaviors.SubmitFormOnEnterBehavior;
import de.lichtflut.rb.webck.common.RBAjaxTarget;
import de.lichtflut.rb.webck.components.domains.DomainSwitcherPanel;
import de.lichtflut.rb.webck.components.fields.SearchField;
import de.lichtflut.rb.webck.components.listview.ReferenceLink;
import de.lichtflut.rb.webck.components.navigation.NavigationBar;
import de.lichtflut.rb.webck.components.navigation.NavigationNode;
import de.lichtflut.rb.webck.components.navigation.NavigationNodePanel;
import de.lichtflut.rb.webck.events.ModelChangeEvent;
import de.lichtflut.rb.webck.models.CurrentUserModel;
import de.lichtflut.rb.webck.models.basic.DerivedDetachableModel;
import de.lichtflut.rb.webck.models.domains.CurrentDomainModel;
import de.lichtflut.rb.webck.models.viewspecs.MenuItemListModel;

/**
 * <p>
 * Base page for all pages in Glasnost Information Server.
 * </p>
 * 
 * <p>
 * Created May 30, 2011
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
	 * @param parameters
	 */
	public RBBasePage(final PageParameters parameters) {
		super(parameters);
	}

	// -----------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onInitialize() {
		super.onInitialize();

		final IModel<List<NavigationNode>> navModel = new DerivedDetachableModel<List<NavigationNode>, List<MenuItem>>(
				new MenuItemListModel()) {
			@Override
			protected List<NavigationNode> derive(List<MenuItem> menuItems) {
				final List<NavigationNode> result = new ArrayList<NavigationNode>();
				for (MenuItem item : menuItems) {
					result.add(createPageNode(item));
				}
				result.add(createPageNode(RBApplication.get().getBrowseAndSearchPage(), "navigation.browse-and-search"));
				result.add(createPageNode(RBApplication.get().getUserProfilePage(), "navigation.user-profile-view"));
				return result;
			}
		};

		add(new NavigationBar("mainNavigation", navModel) {
			@Override
			public void onEvent(IEvent<?> event) {
				if (ModelChangeEvent.from(event).isAbout(ModelChangeEvent.MENU)) {
					RBAjaxTarget.add(this);
				}
			}
		}.setOutputMarkupId(true));

		add(createSeachForm("inlineSearchForm"));

		add(createSecondLevelNav("secondLevelNav"));

		add(new ReferenceLink("adminAreaLink", AdminBasePage.class, new ResourceModel("global.link.admin-area"))
				.add(visibleIf(CurrentUserModel.hasPermission(RBPermission.ENTER_ADMIN_AREA.name()))));

		add(new Label("username", CurrentUserModel.displayNameModel()));

		add(new DomainSwitcherPanel("domain", CurrentDomainModel.displayNameModel()));

	}

	protected Component createSecondLevelNav(final String componentID) {
		return new WebMarkupContainer(componentID);
	}

	@SuppressWarnings("rawtypes")
	protected Form<?> createSeachForm(final String componentID) {
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

	// -----------------------------------------------------

	protected NavigationNode createPageNode(final Class<? extends Page> pageClass, final String key) {
		return new NavigationNodePanel(new ReferenceLink("link", pageClass, new ResourceModel(key)));
	}

	protected NavigationNode createPageNode(final MenuItem item) {
		final PageParameters params = new PageParameters();
		if (item.getPerspective() != null) {
			params.add(PerspectivePage.VIEW_ID, item.getPerspective().getID().toURI());
		}

		return new NavigationNodePanel(new ReferenceLink("link", RBApplication.get().getPerspectivePage(), params, Model.of(item.getName())));
	}

}