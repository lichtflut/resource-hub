/*
 * Copyright (C) 2012 lichtflut Forschungs- und Entwicklungsgesellschaft mbH
 */
package de.lichtflut.rb.webck.components.fields;

import de.lichtflut.rb.core.services.ServiceContext;
import de.lichtflut.rb.webck.config.QueryServicePathBuilder;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.arastreju.sge.naming.QualifiedName;
import org.odlabs.wiquery.ui.autocomplete.AutocompleteSource;

/**
 * <p>
 * 	Picker field for users from auth module.
 * </p>
 *
 * <p>
 * 	Created Jun 15, 2012
 * </p>
 *
 * @author Oliver Tigges
 */
public class UserPickerField extends DataPickerField<QualifiedName> {

    @SpringBean
    private QueryServicePathBuilder pathBuilder;

    @SpringBean
    private ServiceContext serviceContext;

    // ----------------------------------------------------

    /**
	 * Query users.
	 * @param id The component ID.
	 * @param model The model.
	 */
	public UserPickerField(final String id, final IModel<QualifiedName> model) {
		super(id, model);
		setType(QualifiedName.class);
        setSource(findUser());
	}
	
	// -----------------------------------------------------
	
	public AutocompleteSource findUser() {
        return new AutocompleteSource(pathBuilder.queryUsers(serviceContext.getDomain()));
	}
	
}
