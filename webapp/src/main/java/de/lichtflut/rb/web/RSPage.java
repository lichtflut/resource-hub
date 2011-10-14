/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.lichtflut.rb.webck.components.navigation.NavigationBar;

/**
 * <p>
 * Sample page for Web-CK.
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

//	private final RepeatingView resourceList = new RepeatingView("resourcelist");
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
	private void init(final PageParameters parameters) {

		component = new WebMarkupContainer("ck-component");

		component.add(new WebMarkupContainer("content-area").setOutputMarkupPlaceholderTag(true));
//		component.add(new SchemaSubmitPanel("content-area") {
//
//			public NewRBEntityManagement getServiceProvider() {
//				return getRBServiceProvider();
//			}

//		});
		this.add(component);
		// ------------ Navigation - Sidebar ------------

		NavigationBar sidebar = new NavigationBar("sidebar-left");
//		fillMenu(sidebar);
		add(sidebar);

	}

	/**
	 *
	 */
	protected void onBeforeRender() {
		super.onBeforeRender();
//		updateResourceList();
	}

	// -----------------------------------------------------

	/**
	 *
	 */
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	private void updateResourceList() {
//		resourceList.removeAll();
//		ResourceSchemaManagement rManagement = getRBServiceProvider()
//				.getResourceSchemaManagement();
//		RBEntityManagement rTypeManagement = getRBServiceProvider()
//				.getRBEntityManagement();
//		Collection<ResourceSchema> resourceSchemas = rManagement
//				.getAllResourceSchemas();
//		if (resourceSchemas == null) {
//			resourceSchemas = new ArrayList<ResourceSchema>();
//		}
//		for (final ResourceSchema resourceSchema : resourceSchemas) {
//
//			Collection<RBEntity> instances = rTypeManagement
//					.loadAllEntitiesForSchema(resourceSchema);
//
//			final ArrayList<RBEntity> schemaInstances = new ArrayList<RBEntity>(
//					(instances != null) ? instances : new HashSet<RBEntity>());
//
//			PageParameters params = new PageParameters();
//			params.add("resourceid", resourceSchema.getDescribedResourceID()
//					.getQualifiedName().toURI());
//			Fragment fragment = new Fragment(resourceList.newChildId(),
//					"listPanel", this);
//			fragment.add(new BookmarkablePageLink<GenericResourceFormPage>(
//					"link", GenericResourceFormPage.class, params)
//					.add(new Label("linkLabel", resourceSchema
//							.getDescribedResourceID().getQualifiedName()
//							.getSimpleName())));
//
//			fragment.add(new ListView("instancelist", schemaInstances) {
//				@Override
//				protected void populateItem(final ListItem item) {
//					RBEntity instance = (RBEntity) item.getModelObject();
//					PageParameters params = new PageParameters();
//					params.add("resourceid", resourceSchema
//							.getDescribedResourceID().getQualifiedName()
//							.toURI());
//					params.add("instanceid", instance.getQualifiedName()
//							.toURI());
//					Fragment fragment = new Fragment(resourceList.newChildId(),
//							"listPanel", this);
//					fragment.add(new BookmarkablePageLink<GenericResourceFormPage>(
//							"link", GenericResourceFormPage.class, params)
//							.add(new Label("linkLabel", resourceSchema
//									.getDescribedResourceID()
//									.getQualifiedName().getSimpleName())));
//
//					fragment.add(new ListView("instancelist", schemaInstances) {
//						@Override
//						protected void populateItem(final ListItem item) {
//							RBEntity instance = (RBEntity) item
//									.getModelObject();
//							PageParameters params = new PageParameters();
//							params.add("resourceid", resourceSchema
//									.getDescribedResourceID()
//									.getQualifiedName().toURI());
//							params.add("instanceid", instance
//									.getQualifiedName().toURI());
//							item.add(new BookmarkablePageLink<GenericResourceFormPage>(
//									"innerlink", GenericResourceFormPage.class,
//									params).add(new Label("innerlinkLabel",
//									instance.toString())));
//						}
//					});
//					item.add(new BookmarkablePageLink<GenericResourceFormPage>(
//							"innerlink", GenericResourceFormPage.class, params)
//							.add(new Label("innerlinkLabel", instance
//									.toString())));
//				}
//			});
//
//			resourceList.add(fragment);
//		}
//		resourceList.modelChanged();
//	}


	/**
	 * @param sidebar
	 *            -
	 * @return -
	 */
//	@SuppressWarnings("rawtypes")
//	private NavigationBar fillMenu(final NavigationBar sidebar) {
//		NavigationNodePanel submitSchemaLink = new NavigationNodePanel(new CKLink("link",
//				"Submit Schema", RSPage.class, CKLinkType.WEB_PAGE_CLASS));
//		sidebar.addChild(submitSchemaLink);
//		// ---------------- Manage Entities Link -------------------
//		NavigationNodePanel linkManageEntities = new NavigationNodePanel(
//				new CKLink("link", "Manage Entities", CKLinkType.CUSTOM_BEHAVIOR));
//		NavigationNodePanel linkCreate = new NavigationNodePanel(
//				new CKLink("link", "Create", CKLinkType.CUSTOM_BEHAVIOR));
//		NavigationNodePanel linkShow = new NavigationNodePanel(
//				new CKLink("link", "Show", CKLinkType.CUSTOM_BEHAVIOR));
//		NavigationNodePanel linkUpdate = new NavigationNodePanel(
//				new CKLink("link", "Update", CKLinkType.CUSTOM_BEHAVIOR));
//		// Load all Schemas
//		ResourceSchemaManagement rManagement = getRBServiceProvider()
//				.getResourceSchemaManagement();
//		final RBEntityManagement rTypeManagement = getRBServiceProvider()
//				.getRBEntityManagement();
//		Collection<ResourceSchema> resourceSchemas = rManagement
//				.getAllResourceSchemas();
//		if (resourceSchemas == null) {
//			resourceSchemas = new ArrayList<ResourceSchema>();
//		}
//		// CREATE LINK
//		for (final ResourceSchema resourceSchema : resourceSchemas) {
//			PageParameters params = new PageParameters();
//			params.add("resourceid", resourceSchema.getDescribedResourceID()
//					.getQualifiedName().toURI());
//			CKLink link = new CKLink("link", resourceSchema.getDescribedResourceID()
//					.getQualifiedName().getSimpleName(), CKLinkType.CUSTOM_BEHAVIOR);
//			link.addBehavior(CKLink.ON_LINK_CLICK_BEHAVIOR, new CKBehavior() {
//				@Override
//				public Object execute(final Object... objects) {
//					component.removeAll();
//					component.add(new GenericResourceFormPanel("content-area", resourceSchema, null) {
//						public RBServiceProvider getServiceProvider() {
//							return getRBServiceProvider();
//						}
//					});
//					return null;
//				}
//			});
//			NavigationNodePanel node = new NavigationNodePanel(link);
//			linkCreate.addChild(node);
//		}
//
//		// SHOW LINK
//		// Iterate through Schemas an load all Entities of the Schema type
//		for (final ResourceSchema resourceSchema : resourceSchemas) {
//
//			PageParameters params = new PageParameters();
//			params.add("resourceid", resourceSchema.getDescribedResourceID()
//					.getQualifiedName().toURI());
//			CKLink link = new CKLink("link",
//					resourceSchema.getDescribedResourceID().getQualifiedName().getSimpleName(),
//					CKLinkType.CUSTOM_BEHAVIOR);
//			link.addBehavior(CKLink.ON_LINK_CLICK_BEHAVIOR, new CKBehavior() {
//				@Override
//				public Object execute(final Object... objects) {
//					component.removeAll();
//
//					ArrayList<ResourceSchema> schemaList = new ArrayList<ResourceSchema>();
//					schemaList.add(getRBServiceProvider().getResourceSchemaManagement()
//								.getResourceSchemaForResourceType(resourceSchema.getDescribedResourceID()));
//					ResourceRegisterPanel	panel = new ResourceRegisterPanel("content-area", schemaList,
//									"", null, false){
//						public RBServiceProvider getServiceProvider() {
//							return getRBServiceProvider();
//						}
//					};
//					panel.addBehavior(ResourceRegisterPanel.SHOW_DETAILS, new CKBehavior() {
//						@Override
//						public Object execute(final Object... objects) {
//							component.removeAll();
//							GenericResourceFormPanel resourceFormPanel = new GenericResourceFormPanel(
//									"content-area",	resourceSchema, (RBEntity) objects[0]) {
//								@Override
//								public RBServiceProvider getServiceProvider() {
//									// TODO Auto-generated method stub
//									return null;
//								}
//							};
//							component.add(resourceFormPanel);
//							return null;
//						}
//					});
//					panel.addBehavior(ResourceRegisterPanel.DELETE_ROW_ITEM, new CKBehavior() {
//						@Override
//						public Object execute(final Object... objects) {
//							CKLink deleteLink = (CKLink) objects[0];
//							deleteLink.setVisible(false);
//							return null;
//						}
//					});
//					panel.addBehavior(ResourceRegisterPanel.ADD_CUSTOM_ROW_ITEM, new CKBehavior() {
//						@Override
//						public Object execute(final Object... objects) {
//							return new CKLink("propertyField", "Google", "http://google.com/search?q="
//										+ resourceSchema.getDescribedResourceID()
//											.getQualifiedName().getSimpleName(),
//											CKLinkType.EXTERNAL_LINK);
//						}
//					});
//					panel.refreshComponent();
//					component.add(panel);
//					return null;
//				}
//			});
//			linkShow.addChild(new NavigationNodePanel(link));
//		}
//		// UPDATE LINK
//		// Iterate through Schemas an load all Entities of the Schema type
//		for (final ResourceSchema resourceSchema : resourceSchemas) {
//			Collection<RBEntity> instances = rTypeManagement.loadAllEntitiesForSchema(resourceSchema);
//			ArrayList<RBEntity> schemaInstances = new ArrayList<RBEntity>(
//					(instances != null) ? instances : new HashSet<RBEntity>());
//			PageParameters params = new PageParameters();
//			params.add("resourceid", resourceSchema.getDescribedResourceID()
//					.getQualifiedName().toURI());
//
//			NavigationNodePanel node = new NavigationNodePanel(
//					new CKLink("link", resourceSchema.getDescribedResourceID()
//							.getQualifiedName().getSimpleName(),
//							GenericResourceFormPage.class, params,
//							CKLinkType.BOOKMARKABLE_WEB_PAGE_CLASS));
//			linkUpdate.addChild(node);
//
//			for (RBEntity instance : schemaInstances) {
//				PageParameters instanceParams = new PageParameters();
//				instanceParams.add("resourceid", resourceSchema
//						.getDescribedResourceID().getQualifiedName().toURI());
//				instanceParams.add("instanceid", instance.getQualifiedName()
//						.toURI());
//				NavigationNodePanel navinode = new NavigationNodePanel(
//						new CKLink("link", instance.toString(), GenericResourceFormPage.class, instanceParams,
//								CKLinkType.BOOKMARKABLE_WEB_PAGE_CLASS));
//				node.addChild(navinode);
//			}
//		}
//
//		// Add links
//		linkManageEntities.addChild(linkCreate);
//		linkManageEntities.addChild(linkShow);
//		linkManageEntities.addChild(linkUpdate);
//		sidebar.addChild(linkManageEntities);
//		return sidebar;
//	}
}
