/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import de.lichtflut.rb.core.api.ResourceSchemaManagement;
import de.lichtflut.rb.core.api.RBEntityManagement;
import de.lichtflut.rb.core.schema.model.RBEntity;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.spi.RBServiceProvider;
import de.lichtflut.rb.web.ck.behavior.CKBehavior;
import de.lichtflut.rb.web.ck.components.ResourceRegisterPanel;
import de.lichtflut.rb.web.ck.components.SchemaSubmitPanel;
import de.lichtflut.rb.web.genericresource.GenericResourceFormPage;

/**
 * <p>
 *  TODO [DESCRIPTION].
 * </p>
 *
 * <p>
 * 	Created Jan 5, 2011
 * </p>
 *
 * @author Nils Bleisch
 */
@SuppressWarnings("serial")
public class RSPage extends RBSuperPage {

	private final RepeatingView resourceList =  new RepeatingView("resourcelist");


	/**
	 * @param parameters /
	 */
	public RSPage(final PageParameters parameters) {
		super("Resource Schema", parameters);
		init(parameters);
	}

	//-------------------------------------------

    /**
     * @param parameters /
     */
	private void init(final PageParameters parameters) {

    	add(new SchemaSubmitPanel("schemaSubmitPanel"){

			public RBServiceProvider getServiceProvider() {
				return getRBServiceProvider();
			}

    	});
    	updateResourceList();
		this.add(resourceList);
		ResourceRegisterPanel panel = new ResourceRegisterPanel("resourceRegister",
				getRBServiceProvider().getResourceSchemaManagement().getAllResourceSchemas(),"" , null, false) {
			public RBServiceProvider getServiceProvider() {
				return getRBServiceProvider();
			}
		};

		panel.addBehavior(panel.SHOW_DETAILS, new CKBehavior() {

			@SuppressWarnings("rawtypes")
			@Override
			public Object execute(final Object... objects) {
				String identifier = (String) objects[0];
				final int a = 3;
				String value = (String) objects[a];
				final RBEntity instance = (RBEntity) objects[1];
				if(value.contains("a")){
					return new Link(identifier) {
						public void onClick() {
							PageParameters params = new PageParameters();
							params.add("resourceid", instance.getResourceSchema().
									getDescribedResourceID().getQualifiedName().toURI());
							params.add("instanceid", instance.getQualifiedName().toURI());
							setResponsePage(GenericResourceFormPage.class, params);
						}
					};
				}else{
					return new Label(identifier, value);
				}
			}
		});

		this.add(panel);
		this.add(new ResourceRegisterPanel("resourceRegisterTest",
				getRBServiceProvider().getResourceSchemaManagement().getAllResourceSchemas(),
				"M�ller" ,Arrays.asList(new String[]{"hasNachname", "hasEmail", "blablubbla"}), true) {
			public RBServiceProvider getServiceProvider() {
				return getRBServiceProvider();
			}
		});

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
	private void updateResourceList(){
    	resourceList.removeAll();
    	ResourceSchemaManagement rManagement = getRBServiceProvider().getResourceSchemaManagement();
    	RBEntityManagement rTypeManagement = getRBServiceProvider().getRBEntityManagement();
    	Collection<ResourceSchema> resourceSchemas = rManagement.getAllResourceSchemas();
		if(resourceSchemas==null) {
			resourceSchemas = new ArrayList<ResourceSchema>();
		}
		for (final ResourceSchema resourceSchema : resourceSchemas) {

			Collection<RBEntity> instances = rTypeManagement.loadAllResourceTypeInstancesForSchema(resourceSchema);

			ArrayList<RBEntity> schemaInstances =
				new ArrayList<RBEntity>((instances != null) ? instances : new HashSet<RBEntity>());

			PageParameters params = new PageParameters();
			params.add("resourceid", resourceSchema.getDescribedResourceID().getQualifiedName().toURI());
			Fragment fragment = new Fragment(resourceList.newChildId(),"listPanel",this);
			fragment.add(new BookmarkablePageLink<GenericResourceFormPage>("link",GenericResourceFormPage.class, params).
					add(new Label("linkLabel",resourceSchema.getDescribedResourceID().
							getQualifiedName().getSimpleName())));

			fragment.add(new ListView("instancelist",schemaInstances){
				@Override
				protected void populateItem(final ListItem item) {
					RBEntity instance = (RBEntity) item.getModelObject();
					PageParameters params = new PageParameters();
					params.add("resourceid", resourceSchema.getDescribedResourceID().getQualifiedName().toURI());
					params.add("instanceid", instance.getQualifiedName().toURI());
					item.add(new BookmarkablePageLink<GenericResourceFormPage>("innerlink",
							GenericResourceFormPage.class, params).
					add(new Label("innerlinkLabel",instance.toString())));
				}
			});

			resourceList.add(fragment);
		}
		resourceList.modelChanged();
    }


}
