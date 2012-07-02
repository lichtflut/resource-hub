/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.fields;

import de.lichtflut.rb.core.RBSystem;
import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.webck.config.QueryServicePathBuilder;
import de.lichtflut.rb.webck.models.resources.ResourceDisplayModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.ResourceID;
import org.odlabs.wiquery.ui.autocomplete.AutocompleteSource;

/**
 * <p>
 * 	Picker field for general resource IDs.
 * </p>
 *
 * <p>
 * 	Created Oct 26, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class EntityPickerField extends DataPickerField<ResourceID> {
	
	@SpringBean
	private QueryServicePathBuilder pathBuilder;
	
	@SpringBean
	private ServiceContext serviceContext;
	
	// ----------------------------------------------------

	/**
	 * Query any resource of type system:entity
	 * @param id The component ID.
	 * @param model The model.
	 */
	public EntityPickerField(final String id, final IModel<ResourceID> model) {
		this(id, model, RBSystem.ENTITY);
	}
	
	/**
	 * Query any resource of given type.
	 * @param id The component ID.
	 * @param model The model.
	 * @param type The type (should be a sub class of system:entity).
	 */
	public EntityPickerField(final String id, final IModel<ResourceID> model, final ResourceID type) {
		super(id, model, new ResourceDisplayModel(model));
		setType(ResourceID.class);
		setSource(findEntity(type));
	}

	// -----------------------------------------------------
	
	public AutocompleteSource findEntity(final ResourceID type) {
		return new AutocompleteSource(pathBuilder.queryEntities(serviceContext.getDomain(), type.toURI()));
	}
	
}
