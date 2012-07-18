/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.application.admin;

import static de.lichtflut.rb.webck.behaviors.ConditionalBehavior.visibleIf;
import static de.lichtflut.rb.webck.models.ConditionalModel.and;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.lichtflut.rb.application.RBApplication;
import de.lichtflut.rb.application.admin.domains.DomainsManagementPage;
import de.lichtflut.rb.application.admin.identitymanagment.IdentityManagementPage;
import de.lichtflut.rb.application.admin.infomgmt.InformationManagementPage;
import de.lichtflut.rb.application.admin.organizer.OrganizerPage;
import de.lichtflut.rb.application.admin.typesystem.TypeSystemPage;
import de.lichtflut.rb.application.common.RBPermission;
import de.lichtflut.rb.application.pages.AbstractBasePage;
import de.lichtflut.rb.webck.components.listview.ReferenceLink;
import de.lichtflut.rb.webck.components.navigation.NavigationBar;
import de.lichtflut.rb.webck.components.navigation.NavigationNodePanel;
import de.lichtflut.rb.webck.models.CurrentUserModel;
import de.lichtflut.rb.webck.models.domains.CurrentDomainModel;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

/**
 * <p>
 * Base page for all <strong>admin</strong> pages.
 * </p>
 * 
 * <p>
 * Created May 30, 2011
 * </p>
 * 
 * @author Oliver Tigges
 */
@SuppressWarnings("serial")
public class AdminBasePage extends AbstractBasePage {

    public static ResourceReference ADMIN_CSS =
            new JavaScriptResourceReference(AdminBasePage.class, "admin-0.1.css");

    // ----------------------------------------------------

	/**
	 * Default Constructor.
	 */
	public AdminBasePage() {
		this(null);
	}

	/**
	 * @param parameters The page parameters.
	 */
	public AdminBasePage(final PageParameters parameters) {
		super(parameters);
	}

	// -----------------------------------------------------

	protected Class<? extends Page> getWelcomePage(){
		return RBApplication.get().getHomePage();
	}

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.renderCSSReference(ADMIN_CSS);
    }

    /**
	 * {@inheritDoc}
	 */
	@Override
	protected void onInitialize() {
		super.onInitialize();

		final NavigationBar mainNavigation = new NavigationBar("mainNavigation");

		mainNavigation.addChild(new NavigationNodePanel(new ReferenceLink("link", TypeSystemPage.class, new ResourceModel(
				"navigation.admin.type-system"))));

		mainNavigation.addChild(new NavigationNodePanel(new ReferenceLink("link", InformationManagementPage.class, new ResourceModel(
				"navigation.admin.info-managemnt"))));

		mainNavigation.addChild(new NavigationNodePanel(new ReferenceLink("link", OrganizerPage.class, new ResourceModel(
				"navigation.admin.organizer"))));

		final NavigationNodePanel identityManagement = new NavigationNodePanel(new ReferenceLink("link", IdentityManagementPage.class,
				new ResourceModel("navigation.admin.identity-management")));
		identityManagement.add(visibleIf(CurrentUserModel.hasPermission(RBPermission.SEE_USERS.name())));
		mainNavigation.addChild(identityManagement);

		final NavigationNodePanel domainsManagement = new NavigationNodePanel(new ReferenceLink("link", DomainsManagementPage.class,
				new ResourceModel("navigation.admin.domains-management")));
		domainsManagement.add(visibleIf(and(CurrentUserModel.hasPermission(RBPermission.MANAGE_DOMAINS.name()),
				CurrentDomainModel.isMasterDomain())));

		mainNavigation.addChild(domainsManagement);

		if(null != getWelcomePage()){
			mainNavigation.addChild(new NavigationNodePanel(new ReferenceLink("link", getWelcomePage(), new ResourceModel(
				"navigation.admin.back-to-frontend"))));
		}

		add(mainNavigation);

		add(new Label("username", CurrentUserModel.displayNameModel()));

		add(new Label("domain", CurrentDomainModel.displayNameModel()));

	}

}
