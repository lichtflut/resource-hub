/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.lichtflut.rb.core.api.RBEntityManagement;
import de.lichtflut.rb.core.api.ResourceSchemaManagement;
import de.lichtflut.rb.core.schema.model.RBEntity;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.spi.RBServiceProvider;
import de.lichtflut.rb.web.ck.behavior.CKBehavior;
import de.lichtflut.rb.web.ck.components.CKLink;
import de.lichtflut.rb.web.ck.components.CKLinkType;
import de.lichtflut.rb.web.ck.components.GenericResourceFormPanel;
import de.lichtflut.rb.web.ck.components.ResourceRegisterPanel;
import de.lichtflut.rb.web.ck.components.SchemaSubmitPanel;
import de.lichtflut.rb.web.ck.components.navigation.NavigationBar;
import de.lichtflut.rb.web.ck.components.navigation.NavigationNodePanel;
import de.lichtflut.rb.web.genericresource.GenericResourceFormPage;

/**
 * <p>
 * TODO [DESCRIPTION].
 * </p>
 *
 * <p>
 * Created Jan 5, 2011
 * </p>
 *
 * @author Nils Bleisch
 */
@SuppressWarnings("serial")
public class RSPage extends RBSuperPage {

	private final RepeatingView resourceList = new RepeatingView("resourcelist");
	private WebMarkupContainer component;

	/**
	 * @param parameters
	 *            /
	 */
	public RSPage(final PageParameters parameters) {
		super("Resource Schema", parameters);
		init(parameters);
	}

	// -------------------------------------------

	/**
	 * @param parameters
	 *            /
	 */
	@SuppressWarnings("static-access")
	private void init(final PageParameters parameters) {

		component = new WebMarkupContainer("ckcomponent");

		component.add(new SchemaSubmitPanel("content-area") {

			public RBServiceProvider getServiceProvider() {
				return getRBServiceProvider();
			}

		});
		// ------------ Navigation - Sidebar ------------

		NavigationBar sidebar = new NavigationBar("sidebar-right");
		fillMenu(sidebar);
		add(sidebar);
		// -----------------------------------------------
//		add(new SchemaSubmitPanel("schemaSubmitPanel") {
//
//			public RBServiceProvider getServiceProvider() {
//				return getRBServiceProvider();
//			}
//
//		});
		updateResourceList();
		this.add(resourceList);
		ResourceRegisterPanel panel = new ResourceRegisterPanel(
				"resourceRegister", getRBServiceProvider()
						.getResourceSchemaManagement().getAllResourceSchemas(),
				"", null, false) {
			public RBServiceProvider getServiceProvider() {
				return getRBServiceProvider();
			}
		};

		panel.addBehavior(panel.SHOW_DETAILS, new CKBehavior() {

			@SuppressWarnings("rawtypes")
			@Override
			public Object execute(final Object... objects) {
				String identifier = (String) objects[0];
				final int a = 2;
				String value = (String) objects[a];
				@SuppressWarnings("unused")
				final RBEntity instance = (RBEntity) objects[1];
				if (value.contains("a")) {
					return new CKLink(identifier, "bla", RSPage.class, CKLinkType.WEB_PAGE_CLASS);
				} else {
					return new Label(identifier, value);
				}
			}
		});
		panel.refreshComponent();
		this.add(panel);
		this.add(new ResourceRegisterPanel("resourceRegisterTest",
				getRBServiceProvider().getResourceSchemaManagement()
						.getAllResourceSchemas(), "Tigges", Arrays
						.asList(new String[] { "hasNachname", "hasEmail",
								"blablubbla" }), true) {
			public RBServiceProvider getServiceProvider() {
				return getRBServiceProvider();
			}
		});
		this.add(component);
	}

	/**
	 *
	 */
	protected void onBeforeRender() {
		super.onBeforeRender();
		updateResourceList();
	}

	// -----------------------------------------------------

	/**
	 *
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void updateResourceList() {
		resourceList.removeAll();
		ResourceSchemaManagement rManagement = getRBServiceProvider()
				.getResourceSchemaManagement();
		RBEntityManagement rTypeManagement = getRBServiceProvider()
				.getRBEntityManagement();
		Collection<ResourceSchema> resourceSchemas = rManagement
				.getAllResourceSchemas();
		if (resourceSchemas == null) {
			resourceSchemas = new ArrayList<ResourceSchema>();
		}
		for (final ResourceSchema resourceSchema : resourceSchemas) {

			Collection<RBEntity> instances = rTypeManagement
					.loadAllRBEntitiesForSchema(resourceSchema);

			final ArrayList<RBEntity> schemaInstances = new ArrayList<RBEntity>(
					(instances != null) ? instances : new HashSet<RBEntity>());

			PageParameters params = new PageParameters();
			params.add("resourceid", resourceSchema.getDescribedResourceID()
					.getQualifiedName().toURI());
			Fragment fragment = new Fragment(resourceList.newChildId(),
					"listPanel", this);
			fragment.add(new BookmarkablePageLink<GenericResourceFormPage>(
					"link", GenericResourceFormPage.class, params)
					.add(new Label("linkLabel", resourceSchema
							.getDescribedResourceID().getQualifiedName()
							.getSimpleName())));

			fragment.add(new ListView("instancelist", schemaInstances) {
				@Override
				protected void populateItem(final ListItem item) {
					RBEntity instance = (RBEntity) item.getModelObject();
					PageParameters params = new PageParameters();
					params.add("resourceid", resourceSchema
							.getDescribedResourceID().getQualifiedName()
							.toURI());
					params.add("instanceid", instance.getQualifiedName()
							.toURI());
					Fragment fragment = new Fragment(resourceList.newChildId(),
							"listPanel", this);
					fragment.add(new BookmarkablePageLink<GenericResourceFormPage>(
							"link", GenericResourceFormPage.class, params)
							.add(new Label("linkLabel", resourceSchema
									.getDescribedResourceID()
									.getQualifiedName().getSimpleName())));

					fragment.add(new ListView("instancelist", schemaInstances) {
						@Override
						protected void populateItem(final ListItem item) {
							RBEntity instance = (RBEntity) item
									.getModelObject();
							PageParameters params = new PageParameters();
							params.add("resourceid", resourceSchema
									.getDescribedResourceID()
									.getQualifiedName().toURI());
							params.add("instanceid", instance
									.getQualifiedName().toURI());
							item.add(new BookmarkablePageLink<GenericResourceFormPage>(
									"innerlink", GenericResourceFormPage.class,
									params).add(new Label("innerlinkLabel",
									instance.toString())));
						}
					});
					item.add(new BookmarkablePageLink<GenericResourceFormPage>(
							"innerlink", GenericResourceFormPage.class, params)
							.add(new Label("innerlinkLabel", instance
									.toString())));
				}
			});

			resourceList.add(fragment);
		}
		resourceList.modelChanged();
	}


	/**
	 * @param sidebar
	 *            -
	 * @return -
	 */
	@SuppressWarnings("rawtypes")
	private NavigationBar fillMenu(final NavigationBar sidebar) {
		NavigationNodePanel submitLink = new NavigationNodePanel(new CKLink("link",
				"Submit Schema", RSPage.class, CKLinkType.WEB_PAGE_CLASS));
		sidebar.addChild(submitLink);
		// ---------------- Manage Entities Link -------------------
		NavigationNodePanel linkManageEntities = new NavigationNodePanel(
				new CKLink("link", "Manage Entities", RSPage.class,
						CKLinkType.WEB_PAGE_CLASS));
		NavigationNodePanel linkCreate = new NavigationNodePanel(
				new CKLink("link", "Create", RSPage.class,
						CKLinkType.WEB_PAGE_CLASS));
		NavigationNodePanel linkShow = new NavigationNodePanel(
				new CKLink("link", "Show", RSPage.class,
						CKLinkType.WEB_PAGE_CLASS));
		NavigationNodePanel linkUpdate = new NavigationNodePanel(
				new CKLink("link", "Update", RSPage.class,
						CKLinkType.WEB_PAGE_CLASS));
		// Load all Schemas
		ResourceSchemaManagement rManagement = getRBServiceProvider()
				.getResourceSchemaManagement();
		RBEntityManagement rTypeManagement = getRBServiceProvider()
				.getRBEntityManagement();
		Collection<ResourceSchema> resourceSchemas = rManagement
				.getAllResourceSchemas();
		if (resourceSchemas == null) {
			resourceSchemas = new ArrayList<ResourceSchema>();
		}
		// CREATE LINK
		for (final ResourceSchema resourceSchema : resourceSchemas) {
			PageParameters params = new PageParameters();
			params.add("resourceid", resourceSchema.getDescribedResourceID()
					.getQualifiedName().toURI());

			CKLink link = new CKLink("link", resourceSchema.getDescribedResourceID()
					.getQualifiedName().getSimpleName(), CKLinkType.CUSTOM_BEHAVIOR);

			link.addBehavior(CKLink.ON_LINK_CLICK_BEHAVIOR, new CKBehavior() {
				@Override
				public Object execute(final Object... objects) {
					component.removeAll();
					component.add(new GenericResourceFormPanel("content-area", resourceSchema, null) {
						public RBServiceProvider getServiceProvider() {
							return getRBServiceProvider();
						}
					});
					return null;
				}
			});
			NavigationNodePanel node = new NavigationNodePanel(link);
			linkCreate.addChild(node);
		}

		// SHOW LINK
		// Iterate through Schemas an load all Entities of the Schema type
		for (final ResourceSchema resourceSchema : resourceSchemas) {

			Collection<RBEntity> instances = rTypeManagement
					.loadAllRBEntitiesForSchema(resourceSchema);
//			ArrayList<RBEntity> schemaInstances = new ArrayList<RBEntity>(
//					(instances != null) ? instances : new HashSet<RBEntity>());
			PageParameters params = new PageParameters();
			params.add("resourceid", resourceSchema.getDescribedResourceID()
					.getQualifiedName().toURI());

//			NavigationNodePanel node = new NavigationNodePanel(
//					new CKLink("link", resourceSchema.getDescribedResourceID()
//							.getQualifiedName().getSimpleName(),
//							GenericResourceFormPage.class, params,
//							CKLinkType.BOOKMARKABLE_WEB_PAGE_CLASS));

			NavigationNodePanel node = new NavigationNodePanel(new CKLink("link",
					resourceSchema.getDescribedResourceID().getQualifiedName().getSimpleName(),
						CKLinkType.CUSTOM_BEHAVIOR));
			node.addBehavior(CKLink.ON_LINK_CLICK_BEHAVIOR, new CKBehavior() {
				@Override
				public Object execute(final Object... objects) {
					component.removeAll();
					ArrayList<ResourceSchema> schemaList = new ArrayList<ResourceSchema>();
					schemaList.add(getRBServiceProvider()
							.getResourceSchemaManagement()
								.getResourceSchemaForResourceType(resourceSchema.getResourceID()));
					ResourceRegisterPanel panel = new ResourceRegisterPanel("content-area", schemaList,
									"", null, false){
						public RBServiceProvider getServiceProvider() {
							return getRBServiceProvider();
						}
					};
					component.add(panel);
					return null;
				}
			});
			node.refreshComponent();
			linkShow.addChild(node);

//			for (RBEntity instance : schemaInstances) {
//				PageParameters instanceParams = new PageParameters();
//				instanceParams.add("resourceid", resourceSchema
//						.getDescribedResourceID().getQualifiedName().toURI());
//				instanceParams.add("instanceid", instance.getQualifiedName()
//						.toURI());
//				NavigationNodePanel navinode = new NavigationNodePanel(
//						new CKLink("link", instance.toString(), GenericResourceFormPage.class,
//								CKLinkType.WEB_PAGE_CLASS));
//				node.addChild(navinode);
//			}
		}
		// UPDATE LINK
		// Iterate through Schemas an load all Entities of the Schema type
		for (final ResourceSchema resourceSchema : resourceSchemas) {

			Collection<RBEntity> instances = rTypeManagement
					.loadAllRBEntitiesForSchema(resourceSchema);

			ArrayList<RBEntity> schemaInstances = new ArrayList<RBEntity>(
					(instances != null) ? instances : new HashSet<RBEntity>());

			PageParameters params = new PageParameters();
			params.add("resourceid", resourceSchema.getDescribedResourceID()
					.getQualifiedName().toURI());

			NavigationNodePanel node = new NavigationNodePanel(
					new CKLink("link", resourceSchema.getDescribedResourceID()
							.getQualifiedName().getSimpleName(),
							GenericResourceFormPage.class, params,
							CKLinkType.BOOKMARKABLE_WEB_PAGE_CLASS));
			linkUpdate.addChild(node);

			for (RBEntity instance : schemaInstances) {
				PageParameters instanceParams = new PageParameters();
				instanceParams.add("resourceid", resourceSchema
						.getDescribedResourceID().getQualifiedName().toURI());
				instanceParams.add("instanceid", instance.getQualifiedName()
						.toURI());
				NavigationNodePanel navinode = new NavigationNodePanel(
						new CKLink("link", instance.toString(), GenericResourceFormPage.class,
								CKLinkType.WEB_PAGE_CLASS));
				node.addChild(navinode);
			}
		}

		// Add links
		linkManageEntities.addChild(linkCreate);
		linkManageEntities.addChild(linkShow);
		linkManageEntities.addChild(linkUpdate);
		sidebar.addChild(linkManageEntities);

		return sidebar;
	}


}
