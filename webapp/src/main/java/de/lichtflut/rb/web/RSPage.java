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
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import de.lichtflut.rb.core.api.ResourceSchemaManagement;
import de.lichtflut.rb.core.api.ResourceTypeManagement;
import de.lichtflut.rb.core.schema.model.ResourceSchema;
import de.lichtflut.rb.core.schema.model.ResourceTypeInstance;
import de.lichtflut.rb.core.spi.RBServiceProvider;
import de.lichtflut.rb.web.components.genericresource.panels.ResourceRegisterPanel;
import de.lichtflut.rb.web.components.genericresource.panels.SchemaSubmitPanel;
import de.lichtflut.rb.web.genericresource.GenericResourceFormPage;

/**
 * <p>
 *  TODO [DESCRIPTION]
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
	 * @param parameters
	 */
	public RSPage(final PageParameters parameters) {
		super("Resource Schema", parameters);
		init(parameters);
	}

	//-------------------------------------------

    @SuppressWarnings({ })
	private void init(PageParameters parameters) { 
    
    	add(new SchemaSubmitPanel("schemaSubmitPanel"){

			public RBServiceProvider getServiceProvider() {
				return getRBServiceProvider();
			}
    		
    	});
    	updateResourceList();
		this.add(resourceList);
		this.add(new ResourceRegisterPanel("resourceRegister", getRBServiceProvider().getResourceSchemaManagement().getAllResourceSchemas(),"" , null) {
			public RBServiceProvider getServiceProvider() {
				return getRBServiceProvider();
			}
		});
		
		this.add(new ResourceRegisterPanel("resourceRegisterTest",
				getRBServiceProvider().getResourceSchemaManagement().getAllResourceSchemas(),"lichtflut.de" ,Arrays.asList(new String[]{"hasNachname", "hasEmail", "blablubbla"})) {
			public RBServiceProvider getServiceProvider() {
				return getRBServiceProvider();
			}
		});
		
		
	}

    
    protected void onBeforeRender() {
    	super.onBeforeRender();
    	updateResourceList();
    }
    
	// -----------------------------------------------------
    
	@SuppressWarnings({ "unchecked" })
	private void updateResourceList(){
    	resourceList.removeAll();	
    	ResourceSchemaManagement rManagement = getRBServiceProvider().getResourceSchemaManagement();
    	ResourceTypeManagement rTypeManagement = getRBServiceProvider().getResourceTypeManagement();
    	Collection<ResourceSchema> resourceSchemas = rManagement.getAllResourceSchemas();
		if(resourceSchemas==null) resourceSchemas = new ArrayList<ResourceSchema>();
		for (final ResourceSchema resourceSchema : resourceSchemas) {

			Collection<ResourceTypeInstance> instances = rTypeManagement.loadAllResourceTypeInstancesForSchema(resourceSchema);
			
			ArrayList<ResourceTypeInstance> schemaInstances = 
				new ArrayList<ResourceTypeInstance>((instances != null) ? instances : new HashSet<ResourceTypeInstance>());
			
			PageParameters params = new PageParameters();
			params.add("resourceid", resourceSchema.getDescribedResourceID().getQualifiedName().toURI());
			Fragment fragment = new Fragment(resourceList.newChildId(),"listPanel",this);
			fragment.add(new BookmarkablePageLink<GenericResourceFormPage>("link",GenericResourceFormPage.class, params).
					add(new Label("linkLabel",resourceSchema.getDescribedResourceID().getQualifiedName().getSimpleName())));
			
			fragment.add(new ListView("instancelist",schemaInstances){
				@Override
				protected void populateItem(ListItem item) {
					ResourceTypeInstance instance = (ResourceTypeInstance) item.getModelObject();
					PageParameters params = new PageParameters();
					params.add("resourceid", resourceSchema.getDescribedResourceID().getQualifiedName().toURI());
					params.add("instanceid", instance.getQualifiedName().toURI());
					item.add(new BookmarkablePageLink<GenericResourceFormPage>("innerlink",GenericResourceFormPage.class, params).
					add(new Label("innerlinkLabel",instance.toString())));
				}
				
			});
			
			resourceList.add(fragment);
		}
		resourceList.modelChanged();
    }
    

	
}
