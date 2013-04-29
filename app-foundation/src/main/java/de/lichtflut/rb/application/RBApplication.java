/*
 * Copyright (C) 2013 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.lichtflut.rb.application;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.Application;
import org.apache.wicket.ConverterLocator;
import org.apache.wicket.IConverterLocator;
import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.views.SNText;
import org.arastreju.sge.model.nodes.views.SNTimeSpec;
import org.arastreju.sge.naming.QualifiedName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import de.lichtflut.rb.application.base.LoginPage;
import de.lichtflut.rb.application.base.LogoutPage;
import de.lichtflut.rb.application.custom.BrowseAndSearchPage;
import de.lichtflut.rb.application.custom.PerspectivePage;
import de.lichtflut.rb.application.custom.UserProfilePage;
import de.lichtflut.rb.application.custom.WelcomePage;
import de.lichtflut.rb.application.graphvis.ContainmentMapInfoVisPage;
import de.lichtflut.rb.application.graphvis.FlowChartInfoVisPage;
import de.lichtflut.rb.application.graphvis.HierarchyInfoVisPage;
import de.lichtflut.rb.application.graphvis.PeripheryViewPage;
import de.lichtflut.rb.application.layout.Layout;
import de.lichtflut.rb.application.layout.frugal.FrugalLayout;
import de.lichtflut.rb.application.resourceview.EntityDetailPage;
import de.lichtflut.rb.application.styles.Style;
import de.lichtflut.rb.application.styles.frugal.FrugalStyle;
import de.lichtflut.rb.core.common.EntityLabelBuilder;
import de.lichtflut.rb.core.config.RBConfig;
import de.lichtflut.rb.core.viewspec.MenuItem;
import de.lichtflut.rb.webck.common.RBWebSession;
import de.lichtflut.rb.webck.components.listview.ReferenceLink;
import de.lichtflut.rb.webck.components.navigation.NavigationNode;
import de.lichtflut.rb.webck.components.navigation.NavigationNodePanel;
import de.lichtflut.rb.webck.conversion.LabelBuilderConverter;
import de.lichtflut.rb.webck.conversion.QualifiedNameConverter;
import de.lichtflut.rb.webck.conversion.RBDateConverter;
import de.lichtflut.rb.webck.conversion.ResourceIDConverter;
import de.lichtflut.rb.webck.conversion.SNTextConverter;
import de.lichtflut.rb.webck.conversion.SNTimeSpecConverter;

/**
 * <p>
 * 	Base Application class for all Resource Browser applications.
 * </p>
 * 
 * <p>
 * 	Created May 12, 2011
 * </p>
 * 
 * @author Oliver Tigges
 */
public abstract class RBApplication extends WebApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(RBApplication.class);

	private RBConfig config;

	// ------------------------------------------------------

	public static RBApplication get() {
		return (RBApplication) Application.get();
	}

	// -- Configuration -----------------------------------

	public RBConfig getConfig() {
		return config;
	}

	public void setConfig(final RBConfig config) {
		this.config = config;
	}

	// ----------------------------------------------------

	@Override
	public Class<? extends Page> getHomePage(){
		return WelcomePage.class;
	}

	public Class<? extends Page> getLoginPage(){
		return LoginPage.class;
	}

	public Class<? extends Page> getLogoutPage(){
		return LogoutPage.class;
	}

	public Class<? extends Page> getEntityDetailPage(){
		return EntityDetailPage.class;
	}

	public Class<? extends Page> getEntityDetailPage(final ResourceID type){
		return getEntityDetailPage();
	}

	public Class<? extends Page> getPerspectivePage(){
		return PerspectivePage.class;
	}

	public Class<? extends Page> getPeripheryVizPage(){
		return PeripheryViewPage.class;
	}

	public Class<? extends Page> getHierarchyVizPage(){
		return HierarchyInfoVisPage.class;
	}

	public Class<? extends Page> getContainmentMapVizPage(){
		return ContainmentMapInfoVisPage.class;
	}

	public Class<? extends Page> getFlowChartVizPage(){
		return FlowChartInfoVisPage.class;
	}

	public Class<? extends Page> getBrowseAndSearchPage(){
		return BrowseAndSearchPage.class;
	}

	public Class<?extends Page> getUserProfilePage(){
		return UserProfilePage.class;
	}

	// ----------------------------------------------------

	/**
	 * The layout for this application.
	 * @return The layout.
	 */
	public Layout getLayout() {
		return new FrugalLayout();
	}

	/**
	 * The style for this application.
	 * @return The style.
	 */
	public Style getStyle() {
		return new FrugalStyle();
	}

	// ----------------------------------------------------

	/**
	 * Configure if this application can be used without authentication.
	 * @return true if unauthenticated access is supported.
	 */
	public boolean supportsUnauthenticatedAccess() {
		return false;
	}

	public String getDefaultDomain() {
		return "public";
	}

	public List<NavigationNode> getFirstLevelNavigation(final List<MenuItem> menuItems) {
		final List<NavigationNode> result = new ArrayList<NavigationNode>();
		for (MenuItem item : menuItems) {
			result.add(createPageNode(item));
		}
		result.add(createPageNode(getBrowseAndSearchPage(), "navigation.browse-and-search"));
		if (RBWebSession.exists() && RBWebSession.get().isAuthenticated()) {
			result.add(createPageNode(getUserProfilePage(), "navigation.user-profile-view"));
		}
		return result;
	}

	protected NavigationNode createPageNode(final Class<? extends Page> pageClass, final String key) {
		return new NavigationNodePanel(new ReferenceLink("link", pageClass, new ResourceModel(key)));
	}

	protected NavigationNode createPageNode(final MenuItem item) {
		final PageParameters params = new PageParameters();
		if (item.getPerspective() != null) {
			params.add(PerspectivePage.VIEW_ID, item.getPerspective().getQualifiedName().toURI());
		}

		return new NavigationNodePanel(new ReferenceLink("link", RBApplication.get().getPerspectivePage(), params, Model.of(item.getName())));
	}

	// ----------------------------------------------------

	@Override
	public Session newSession(final Request request, final Response response) {
		return new RBWebSession(request);
	}

	@Override
	protected void init() {
		super.init();

		getComponentInstantiationListeners().add(new SpringComponentInjector(this));

		getMarkupSettings().setStripWicketTags(true);
		getMarkupSettings().setDefaultMarkupEncoding("UTF-8");

		getRequestCycleListeners().add(new RBRequestCycleListener());
		getRequestCycleSettings().setResponseRequestEncoding("UTF-8");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected IConverterLocator newConverterLocator() {
		final ConverterLocator locator = new ConverterLocator();
		locator.set(SNText.class, new SNTextConverter());
		locator.set(SNTimeSpec.class, new SNTimeSpecConverter());
		locator.set(ResourceID.class, new ResourceIDConverter());
		locator.set(QualifiedName.class, new QualifiedNameConverter());
		locator.set(EntityLabelBuilder.class, new LabelBuilderConverter());
		locator.set(Date.class, new RBDateConverter());
		return locator;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		LOGGER.info("Application is beeing destroyed. Free all resources.");

		final WebApplicationContext wac =
				WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());

		final RBConfig rbc = (RBConfig) wac.getBean("rbConfig");
		rbc.getArastrejuProfile().close();
	}


}
