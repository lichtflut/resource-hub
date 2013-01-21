/*
 * Copyright 2012 by lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.models.resources;

import de.lichtflut.rb.core.services.SemanticNetworkService;
import de.lichtflut.rb.webck.models.basic.AbstractLoadableDetachableModel;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.ResourceID;
import org.arastreju.sge.model.nodes.ResourceNode;

/**
 * <p>
 *  Model loading a resource.
 * </p>
 *
 * <p>
 * 	Created Mar 8, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class ResourceLoadModel extends AbstractLoadableDetachableModel<ResourceNode> {

	@SpringBean
	private SemanticNetworkService service;
	
	private final ResourceID id;
	
	// ----------------------------------------------------
	
	/**
	 * Constructor.
	 */
	public ResourceLoadModel(ResourceID id) {
		this.id = id;
		Injector.get().inject(this);
	}

	@Override
	public ResourceNode load() {
		return service.find(id.getQualifiedName());
	}
	
}
