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
 * 	Picker field for general resource IDs.
 * </p>
 *
 * <p>
 * 	Created Oct 26, 2011
 * </p>
 *
 * @author Oliver Tigges
 */
public class ResourcePickerField extends DataPickerField<ResourceID> {

    private final IModel<ResourceID> type;

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
	public ResourcePickerField(final String id, final IModel<ResourceID> model) {
		this(id, model, new Model<ResourceID>());
	}

    /**
     * Constructor.
     * @param id The component ID.
     * @param model The model.
     * @param type The rdf:type of the resource.
     */
    public ResourcePickerField(final String id, final IModel<ResourceID> model, ResourceID type) {
        this(id, model, Model.of(type));
    }

    /**
     * Constructor.
     * @param id The component ID.
     * @param model The model.
     * @param type The rdf:type of the resource.
     */
    public ResourcePickerField(final String id, final IModel<ResourceID> model, final IModel<ResourceID> type) {
        super(id, model, new ResourceDisplayModel(model));
        this.type = type;
        setType(ResourceID.class);
        setSource(findAll());
    }
	
	// -----------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onConfigure() {
        super.onConfigure();
        if (type != null && type.getObject() != null) {
            setSource(findByType(type.getObject()));
        } else {
            setSource(findAll());
        }
    }


	
	public AutocompleteSource findAll() {
        return new AutocompleteSource(
                pathBuilder.queryResources(serviceContext.getDomain(), null));
	}

    public AutocompleteSource findByType(final ResourceID type) {
        return new AutocompleteSource(
                pathBuilder.queryResources(serviceContext.getDomain(), type.toURI()));
    }

}
