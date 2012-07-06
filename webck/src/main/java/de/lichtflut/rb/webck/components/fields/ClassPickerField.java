/*
 * Copyright (C) 2011 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.fields;

import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.webck.config.QueryServicePathBuilder;
import de.lichtflut.rb.webck.models.resources.ResourceDisplayModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.model.ResourceID;
import org.odlabs.wiquery.ui.autocomplete.AutocompleteSource;

/**
 * <p>
 * 	Picker field for rdfs:Class'es.
 * </p>
 *
 * <p>
 * 	Created Dec 14, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class ClassPickerField extends DataPickerField<ResourceID> {
	
	private final IModel<ResourceID> superClass;

    @SpringBean
    private QueryServicePathBuilder pathBuilder;

    @SpringBean
    private ServiceContext serviceContext;
	
	// ----------------------------------------------------

	/**
	 * Constructor.
	 * @param id The component ID.
	 * @param model The model.
	 */
	public ClassPickerField(final String id, final IModel<ResourceID> model) {
		this(id, model, new Model<ResourceID>());
	}
	
	/**
	 * Constructor.
	 * @param id The component ID.
	 * @param model The model.
	 */
	public ClassPickerField(final String id, final IModel<ResourceID> model, final IModel<ResourceID> superClass) {
		super(id, model, new ResourceDisplayModel(model));
		this.superClass = superClass;
		setType(ResourceID.class);
	}
	
	// -----------------------------------------------------
	
	/** 
	* {@inheritDoc}
	*/
	@Override
	protected void onConfigure() {
		super.onConfigure();
		setSource(findSubClasses(superClass.getObject()));
	}
	
	// ----------------------------------------------------
	
	public AutocompleteSource findSubClasses(ResourceID superClass) {
        String uri = superClass != null ? superClass.toURI() : null;
        return new AutocompleteSource(
                pathBuilder.queryClasses(serviceContext.getDomain(), uri));
	}
	
}
